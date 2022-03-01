FROM openjdk:8-jdk-alipine
COPY target/rpc-dubbo-consumer-1.0-SNAPSHOT.jar  app.jar
ENV NACOS_ADDRESS ${nacos.address}
CMD ["sh", "-c", "java -jar app.jar"]