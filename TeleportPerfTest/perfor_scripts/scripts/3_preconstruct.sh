# pre-construct tx
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12000/PerfTestController/pre_construct_data?peerId=0&accountId=0&txsNumber=50000' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12001/PerfTestController/pre_construct_data?peerId=1&accountId=1&txsNumber=50000' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12002/PerfTestController/pre_construct_data?peerId=2&accountId=2&txsNumber=50000' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12003/PerfTestController/pre_construct_data?peerId=3&accountId=3&txsNumber=50000' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12004/PerfTestController/pre_construct_data?peerId=4&accountId=4&txsNumber=50000' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12005/PerfTestController/pre_construct_data?peerId=5&accountId=5&txsNumber=50000' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12006/PerfTestController/pre_construct_data?peerId=6&accountId=6&txsNumber=50000' &


