FROM python:3.8.12

COPY ./requirements.dep /app/requirements.dep
WORKDIR /app
RUN pip install -r requirements.dep
COPY . /app
WORKDIR /app
ENTRYPOINT [ "python", "-u" ]
CMD [ "Niblette.py" ]