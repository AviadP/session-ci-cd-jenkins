parallel(
        "AwsStream":{
            node ('ops-school-dynamic-slave'){
                currentBuild.result = "SUCCESS"

                stage('Checkout'){

                    checkout scm
                }

                stage('Create local docker repo'){
                    sh 'sudo docker run -d -p 5000:5000 --restart=always --name registry registry:2 || true'
                }

                stage('Unit Tests'){
                    sh 'pip install -r python_app/requirements.txt'
                    sh 'python -m virtualenv --system-site-packages NEW_ENV'
                    sh '. ./NEW_ENV/bin/activate'
                    sh 'pip install -r python_app/requirements.txt'
                    sh 'python -m pytest --junitxml results.xml python_app/tests/test_server.py'
                    sh 'source NEW_ENV/bin/deactivate'

                }

                stage('Teardown'){
                    sh 'docker-compose stop && docker-compose rm -f || true'
                    sh 'sudo docker rm -f opsschool_dummy_app || true'

                }

                stage('Build'){
                    sh 'sudo docker build --no-cache -t localhost:5000/opsschool_dummy_app:latest .'
                    sh 'sudo docker push localhost:5000/opsschool_dummy_app:latest'
                }

                stage('Deploy'){
                    sh 'sudo docker pull localhost:5000/opsschool_dummy_app:latest || true'
                    sh 'sudo docker-compose up -d || true'
                }

                stage('Final Test'){
                    sh 'curl localhost:8000/health'
                }

                junit 'test/python_app/*.xml'

            }

        }
)