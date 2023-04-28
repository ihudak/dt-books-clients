@ECHO OFF
CLS
ECHO.

IF "%~1"=="" GOTO UNKNOWN

IF "%1"=="-noagent" (
    SET BASE_IMAGE=dt-java-noagent
) ELSE IF "%1"=="-agents"  (
    SET BASE_IMAGE=dt-java-agents
) ELSE GOTO UNKNOWN

IF "%~2"=="-arm" (SET PLATFORM=arm64) ELSE (SET PLATFORM=latest)

SET BASE_REPO=ivangudak096

./gradlew.bat clean build
docker image build --platform linux/%PLATFORM% -t %BASE_REPO%/dt-clients-service:%PLATFORM% --build-arg BASE_REPO=%BASE_REPO% --build-arg BASE_IMAGE=%BASE_IMAGE% --build-arg BASE_IMG_TAG=%PLATFORM% .
docker push ivangudak096/dt-clients-service:%PLATFORM%
EXIT 0


:UNKNOWN
ECHO "Bad/No Parameters"
ECHO "Usage:"
ECHO "   ./push_docker.sh -agents  -arm|-x86  # makes docker image with OA and OTel agents"
ECHO "   ./push_docker.sh -noagent -arm|-x86  # makes docker image with no agents embedded"
ECHO
ECHO "Please supply at least either -agents or -noagent"
ECHO "      optionally specify platform as -arm or -x86"
EXIT 1
