FROM python:3.6.2-alpine

RUN mkdir /myapp
RUN mkdir /myapp/python_app

COPY ./python_app/ /myapp/python_app
WORKDIR /myapp/python_app
RUN pip install -r requirements.txt

CMD ["python", "server.py"]
