FROM ubuntu:22.04
MAINTAINER wanli
EXPOSE 8511
ADD target/service-product-1.0-SNAPSHOT.jar /service-product-1.0-SNAPSHOT.jar

WORKDIR /
ENTRYPOINT ["java" , "-jar" , "service-product-1.0-SNAPSHOT.jar"]