# query tps from peer1
curl -X GET --header 'Accept: text/plain' 'http://172.17.0.1:12001/PerfTestController/statisic_result'

# query tps from peer2
curl -X GET --header 'Accept: text/plain' 'http://172.17.0.1:12002/PerfTestController/statisic_result'

# query tps from peer3
curl -X GET --header 'Accept: text/plain' 'http://172.17.0.1:12003/PerfTestController/statisic_result'

# query tps from peer4
curl -X GET --header 'Accept: text/plain' 'http://172.17.0.1:12004/PerfTestController/statisic_result'

# query tps from peer5
curl -X GET --header 'Accept: text/plain' 'http://172.17.0.1:12005/PerfTestController/statisic_result'

# query tps from peer6
curl -X GET --header 'Accept: text/plain' 'http://172.17.0.1:12006/PerfTestController/statisic_result'