FROM java:8 
VOLUME /tmp 
ADD ./target/asset-1.0.jar /asset.jar
ENTRYPOINT ["java","-jar","/asset.jar","--spring.profiles.active=dev"]