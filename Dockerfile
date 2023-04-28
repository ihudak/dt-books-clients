ARG BASE_REPO=ivangudak096
ARG AGENT=noagent
ARG PLATFORM=x64
ARG BASE_IMG_TAG=latest
FROM ${BASE_REPO}/dt-java-${AGENT}-${PLATFORM}:${BASE_IMG_TAG}
MAINTAINER dynatrace.com

ARG JAR_FILE=build/libs/*0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /opt/app/app.jar
