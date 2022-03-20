# start container
docker run  -p 12001:12000 --name=test1 -itd webbshi/bitos_perftest:latest /bin/bash
docker run  -p 12002:12000 --name=test2 -itd webbshi/bitos_perftest:latest /bin/bash
docker run  -p 12003:12000 --name=test3 -itd webbshi/bitos_perftest:latest /bin/bash
docker run  -p 12004:12000 --name=test4 -itd webbshi/bitos_perftest:latest /bin/bash
docker run  -p 12005:12000 --name=test5 -itd webbshi/bitos_perftest:latest /bin/bash
docker run  -p 12006:12000 --name=test6 -itd webbshi/bitos_perftest:latest /bin/bash

docker logs -f test1 --tail=1000
