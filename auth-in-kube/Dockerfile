FROM openjdk:11.0.10-jdk-slim

COPY target/auth-in-kube-0.0.1.jar auth-in-kube-0.0.1.jar

ENTRYPOINT ["java", "-jar", "/auth-in-kube-0.0.1.jar"]