ARG BASE_REPO=ivangudak096
ARG BASE_IMAGE=dt-java-noagent
ARG BASE_IMG_TAG=latest
FROM --platform=linux/x86-64 ${BASE_REPO}/${BASE_IMAGE}:${BASE_IMG_TAG}
MAINTAINER dynatrace.com

ARG JAR_FILE=build/libs/*0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /opt/app/app.jar
