#!/bin/sh
heliosPId=$(ps -ef | grep midi-helios-processor | grep -v "grep" |awk '{print $2}')
echo "${heliosPId}"
if [ "$heliosPId" ==  "" ]
then
cd /opt/seadun/production/helios
nohup java -jar midi-helios-processor-1.0.1-jar-with-dependencies.jar &
#java -version
else
nohup kill -9 $heliosPId
cd /opt/seadun/production/helios 
nohup java -jar midi-helios-processor-1.0.1-jar-with-dependencies.jar &
echo "runing......"
fi
