mvn clean
mvn install
docker build -t webbshi/bitos_perftest:latest .
docker push webbshi/bitos_perftest:latest