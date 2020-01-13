#!/bin/sh
jkPId=$(ps -ef | grep midi-jk-processor | grep -v "grep" |awk '{print $2}')
echo "${jkPId}"
if [ "$jkPId" ==  "" ]
then
cd /opt/seadun/production/jk 
nohup java -jar midi-jk-processor-1.0.1-jar-with-dependencies.jar &
#java -version
else
nohup kill -9 $jkPId
cd /opt/seadun/production/jk 
nohup java -jar midi-jk-processor-1.0.1-jar-with-dependencies.jar &
echo "runing......"
fi
