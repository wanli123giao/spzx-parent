FROM ubuntu:22.04
MAINTAINER wanli
EXPOSE 8513
ADD target/service-cart-1.0-SNAPSHOT.jar /service-cart-1.0-SNAPSHOT.jar

WORKDIR /
ENTRYPOINT ["java" , "-jar" , "service-cart-1.0-SNAPSHOT.jar"]