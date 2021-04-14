FROM gradle:6.8.3-jdk8

RUN mkdir /app
WORKDIR /app


COPY . /app

#RUN ./gradlew build
