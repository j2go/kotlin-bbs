FROM java:8-alpine

WORKDIR  /app
RUN   apk update \
  &&   apk add ca-certificates wget \
  &&   wget https://github.com/sstg/kotlin-bbs/releases/download/0.0.1/kotlin-bbs-0.0.1.jar

CMD ["java", "-jar", "kotlin-bbs-0.0.1.jar"]

