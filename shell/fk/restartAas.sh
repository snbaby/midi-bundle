#!/bin/sh
aasPId=$(ps -ef | grep midi-aas-processor | grep -v "grep" |awk '{print $2}')
echo "${aasPId}"
if [ "$aasPId" ==  "" ]
then
cd /opt/seadun/production/aas 
nohup java -jar midi-aas-processor-1.0.1-jar-with-dependencies.jar &
#java -version
else
nohup kill -9 $aasPId
cd /opt/seadun/production/aas 
nohup java -jar midi-aas-processor-1.0.1-jar-with-dependencies.jar &
echo "runing......"
fi
