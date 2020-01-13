#!/bin/sh
heliosPId=$(ps -ef | grep midi-helios-processor | grep -v "grep" |awk '{print $2}')
echo "${heliosPId}"
if [ "$heliosPId" ==  "" ]
then
nohup java -jar /opt/seadun/production/helios/midi-helios-processor-1.0.1-jar-with-dependencies.jar &
#java -version
else
echo "runing......"
fi
