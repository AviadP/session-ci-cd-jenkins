FROM python:3.6.2-alpine

RUN mkdir /myapp
WORKDIR /myapp
COPY ./python_app/ /myapp
RUN pip install -r requirements.txt

CMD ["python", "server.py"]
