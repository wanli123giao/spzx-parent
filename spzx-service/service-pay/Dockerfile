FROM ubuntu:22.04
MAINTAINER wanli
EXPOSE 8515
ADD target/service-pay-1.0-SNAPSHOT.jar /service-pay-1.0-SNAPSHOT.jar

WORKDIR /
ENTRYPOINT ["java" , "-jar" , "service-pay-1.0-SNAPSHOT.jar"]