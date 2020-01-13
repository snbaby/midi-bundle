#!/bin/sh
aasPId=$(ps -ef | grep midi-aas-processor | grep -v "grep" |awk '{print $2}')
echo "${aasPId}"
if [ "$aasPId" ==  "" ]
then
nohup java -jar /opt/seadun/production/aas/midi-aas-processor-1.0.1-jar-with-dependencies.jar &
#java -version
else
echo "runing......"
fi
