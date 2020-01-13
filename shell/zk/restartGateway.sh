#!/bin/sh
gatewayPId=$(ps -ef | grep midi-gateway | grep -v "grep" |awk '{print $2}')
echo "${gatewayPId}"
if [ "$gatewayPId" ==  "" ]
then
cd /opt/seadun/production/midi-gateway
nohup sh bin/start.sh &
#java -version
else
nohup kill -9 $gatewayPId
cd /opt/seadun/production/midi-gateway 
nohup sh bin/start.sh &
echo "runing......"
fi
