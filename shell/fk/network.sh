#!/bin/bash

function usage() {
    echo "-a config address"
    echo "-i specify nic name"
    echo "-n config netmask"
    echo "-g config gateway"
    echo "-h print this usage"
}

while getopts ":a:i:f:n:g:h" opt
do
	case $opt in
		a ) address=$OPTARG;;
		i ) nic=$OPTARG;;
		n ) netmask=$OPTARG;;
		g ) gateway=$OPTARG;;
		h ) usage
			exit 1;;
	esac
done

if [ -z "${address}" ]
then
	echo "-a config address not exist"
	exit 1
else
	echo "-a ${address}"
fi

if [ -z "${nic}" ]
then
	echo "-i specify nic name not exist"
	exit 1
else
	echo "-i ${nic}"
fi

if [ -z "${netmask}" ]
then
	echo "-n config netmask not exist"
	exit 1
else
	echo "-n ${netmask}"
fi

if [ -z "${gateway}" ]
then
	echo "-g config gateway not exist"
	exit 1
else
	echo "-g ${gateway}"
fi

if [ "`cat /etc/os-release|grep -c 'Red Hat'`" != 0 ]
then
	bash /opt/seadun/shell/network-red-hat.sh -a ${address} -n ${netmask} -g ${gateway} -f /etc/sysconfig/network-scripts/ifcfg-${nic}
else
	echo "have not Red Hat"
fi 

if [ "`cat /etc/os-release|grep -c Alpine`" != 0 ]
then
	bash /opt/seadun/shell/network-alpine.sh -a ${address} -n ${netmask} -g ${gateway} -i ${nic} -f /etc/network/interfaces
else
	echo "have not Alpine"
fi
