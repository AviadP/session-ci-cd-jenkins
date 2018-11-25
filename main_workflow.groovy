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

                stage('Teardown'){
                    sh 'docker-compose stop && docker-compose rm -f || true'
                    sh 'sudo docker rm -f opsschool_dummy_app || true'
                    sh 'sudo docker rm -f buildclassdummyproject_nginx_1 || true'

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
                    sh 'curl localhost/health'
                }

            }

        }
)