#!/bin/sh

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

if [ $# -eq 2 ] && [ $2 = "-arm" ]; then PLATFORM="arm64"; else PLATFORM="latest"; fi

BASE_REPO=ivangudak096

if [ $1 = "-agents" ]; then
  BASE_IMAGE=dt-java-agents
else
  BASE_IMAGE=dt-java-noagent
fi

./gradlew clean build
docker image build --platform linux/$PLATFORM -t ivangudak096/dt-clients-service:$PLATFORM --build-arg BASE_REPO=$BASE_REPO --build-arg BASE_IMAGE=$BASE_IMAGE --build-arg BASE_IMG_TAG=$PLATFORM.
docker push ivangudak096/dt-clients-service:$PLATFORM

#docker image build --platform linux/arm64 -t ivangudak096/dt-clients-service:arm64 .
#docker push ivangudak096/dt-clients-service:arm64
