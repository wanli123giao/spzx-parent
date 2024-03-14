FROM ubuntu:22.04
MAINTAINER wanli
EXPOSE 8500
#ADD target/server-gateway-1.0-SNAPSHOT.jar /server-gateway-1.0-SNAPSHOT.jar
ADD target/spzx-server-gateway-1.0-SNAPSHOT.jar /spzx-server-gateway-1.0-SNAPSHOT.jar
WORKDIR /
ENTRYPOINT ["java" , "-jar" , "spzx-server-gateway-1.0-SNAPSHOT.jar"]