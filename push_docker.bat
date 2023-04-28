@ECHO OFF
CLS
ECHO.

REM ######## Project Configuration ##########
SET PROJECT=dt-clients
SET BASE_REPO=ivangudak096
SET TAG=latest
REM ######## Project Configuration ##########

IF "%~1"=="" GOTO UNKNOWN

IF "%1"=="-noagent" (
    SET AGENT=noagent
) ELSE IF "%1"=="-agents"  (
    SET AGENT=agents
) ELSE GOTO UNKNOWN

IF "%~2"=="-arm" (
    SET PLATFORM=arm64
    SET PLATFORM_FULL=arm64/v8
) ELSE (
    SET PLATFORM=x64
    SET PLATFORM_FULL=amd64
)

SET IMG_NAME=%BASE_REPO%/%PROJECT%-%AGENT%-%PLATFORM%:%TAG%

call gradlew.bat clean build
docker image build ^
    --platform linux/%PLATFORM_FULL% ^
    -t %IMG_NAME% ^
    --build-arg BASE_REPO=%BASE_REPO% ^
    --build-arg AGENT=%AGENT% ^
    --build-arg PLATFORM=%PLATFORM% ^
    --build-arg BASE_IMG_TAG=%TAG% ^
    .
docker push %IMG_NAME%
EXIT 0


:UNKNOWN
ECHO Bad/No Parameters
ECHO Usage:
ECHO    push_docker.bat -agents  -arm/-x86  # makes docker image with OA and OTel agents
ECHO    push_docker.bat -noagent -arm/-x86  # makes docker image with no agents embedded
ECHO.
ECHO Please supply at least either -agents or -noagent
ECHO       optionally specify platform as -arm or -x86
ECHO.
EXIT 1
