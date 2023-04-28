#!/bin/sh
######## Project Configuration ##########
PROJECT=dt-clients
BASE_REPO=ivangudak096
TAG=latest
######## Project Configuration ##########

display_usage() {
  echo "Bad/no argument(s) supplied";
      echo "Usage:";
      echo "   ./push_docker.sh -agents  -arm|-x86  # makes docker image with OA and OTel agents";
      echo "   ./push_docker.sh -noagent -arm|-x86  # makes docker image with no agents embedded";
      echo;
      echo "Please supply at least either -agents or -noagent";
      echo "      optionally specify platform as -arm or -x86";
	  echo;
      exit 1;
}

if [ $# -eq 0 ]; then
  display_usage;
elif [ $1 != "-agents" ] && [ $1 != "-noagent" ]; then
  display_usage;
fi

if [ $# -eq 2 ] && [ $2 = "-arm" ]; then
  PLATFORM="arm64";
  PLATFORM_FULL="arm64/v8";
else
  PLATFORM="x64";
  PLATFORM_FULL="amd64";
fi

if [ $1 = "-agents" ]; then
  AGENT=agents
else
  AGENT=noagent
fi

IMG_NAME=$BASE_REPO/$PROJECT-$AGENT-$PLATFORM:$TAG

./gradlew clean build
docker image build \
  --platform linux/$PLATFORM_FULL \
  -t $IMG_NAME \
  --build-arg BASE_REPO=$BASE_REPO \
  --build-arg AGENT=$AGENT \
  --build-arg PLATFORM=$PLATFORM \
  --build-arg BASE_IMG_TAG=$TAG \
  .
docker push $IMG_NAME
