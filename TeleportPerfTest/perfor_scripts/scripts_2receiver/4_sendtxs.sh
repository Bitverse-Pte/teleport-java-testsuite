# send txs
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12001/PerfTestController/send_txs?peerId=1&accountIndex=1&txsNumber=50000&interval=200' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12002/PerfTestController/send_txs?peerId=2&accountIndex=2&txsNumber=50000&interval=200' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12003/PerfTestController/send_txs?peerId=3&accountIndex=3&txsNumber=50000&interval=200' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12004/PerfTestController/send_txs?peerId=4&accountIndex=4&txsNumber=50000&interval=200' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12005/PerfTestController/send_txs?peerId=5&accountIndex=5&txsNumber=50000&interval=200' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12006/PerfTestController/send_txs?peerId=6&accountIndex=6&txsNumber=50000&interval=200' &