#!/bin/sh
if [ ! -f "/var/spool/cron/root" ]
then
	#定时任务文件不存在
	#添加定时任务
	echo "* * * * *   source /etc/profile && sh /opt/seadun/shell/crontab-helios.sh" >> /var/spool/cron/root
else
	#定时任务文件存在
	#删除原定时任务
	sed -i '/crontab-helios.sh/d' /var/spool/cron/root
	#添加新定时任务
        echo "* * * * *   source /etc/profile && sh /opt/seadun/shell/crontab-helios.sh" >> /var/spool/cron/root 
	crontab -l
	echo "/var/spool/cron/root文件存在"
	
fi
