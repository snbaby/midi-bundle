#!/bin/sh

sh /opt/seadun/shell/install-crontab-aas.sh
sh /opt/seadun/shell/install-crontab-helios.sh
sh /opt/seadun/shell/install-crontab-gateway.sh
sh /opt/seadun/shell/crontab-aas.sh
sh /opt/seadun/shell/crontab-helios.sh
cd /opt/seadun/production/midi-gateway && nohup sh bin/start.sh
