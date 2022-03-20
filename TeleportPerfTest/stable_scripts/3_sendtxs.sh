nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12001/StableTestController/send_txs?peerId=1&accountIndex=1&txsNumber=20000&interval=10' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12002/StableTestController/send_txs?peerId=2&accountIndex=2&txsNumber=20000&interval=10' &
nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12003/StableTestController/send_txs?peerId=3&accountIndex=3&txsNumber=20000&interval=10' &
# nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12004/StableTestController/send_txs?peerId=4&accountIndex=4&txsNumber=20000&interval=10' &
# nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12005/StableTestController/send_txs?peerId=5&accountIndex=5&txsNumber=1000&interval=10' &
# nohup curl -X GET --header 'Accept: */*' 'http://127.0.0.1:12006/StableTestController/send_txs?peerId=6&accountIndex=6&txsNumber=1000&interval=10' &