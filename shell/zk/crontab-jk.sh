#!/bin/sh
jkPId=$(ps -ef | grep midi-jk-processor | grep -v "grep" |awk '{print $2}')
echo "${jkPId}"
if [ "$jkPId" ==  "" ]
then
nohup java -jar /opt/seadun/production/jk/midi-jk-processor-1.0.1-jar-with-dependencies.jar &
#java -version
else
echo "runing......"
fi
