FROM python:3.6.2-alpine

RUN mkdir /myapp

COPY ./python_app/ /myapp

WORKDIR /myapp

RUN pip install -r requirements.txt

CMD ["python", "server.py"]
