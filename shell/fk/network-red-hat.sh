#!/bin/bash

function usage() {
    echo "-a config address"
    echo "-f specify file path"
    echo "-n config netmask"
    echo "-g config gateway"
    echo "-h print this usage"
}
while getopts ":a:i:f:n:g:h" opt
do
	case $opt in
		a ) IPADDR=$OPTARG;;
		f ) NETWORK_FILE=$OPTARG;;
		n ) NETMASK=$OPTARG;;
		g ) GATEWAY=$OPTARG;;
		h ) usage
			exit 1;;
	esac
done
echo "NETWORK_FILE=${NETWORK_FILE}"

if [ -a "${NETWORK_FILE}" ]
then
	if [ -z "${IPADDR}" ]
	then
		echo "IPADDR not exist"
	else
		echo "modify IPADDR=${IPADDR}"
		sed -i '/IPADDR=/d' ${NETWORK_FILE}
		echo "IPADDR=${IPADDR}" >> ${NETWORK_FILE}
	fi

	if [ -z "${NETMASK}" ]
	then
		echo "NETMASK not exist"
	else
		echo "modify NETMASK=${NETMASK}"
		sed -i '/NETMASK=/d' ${NETWORK_FILE}
                echo "NETMASK=${NETMASK}" >> ${NETWORK_FILE}
	fi

	if [ -z "${GATEWAY}" ]
	then
		echo "GATEWAY not exist"
	else
		echo "modify GATEWAY=${GATEWAY}"
		sed -i '/GATEWAY=/d' ${NETWORK_FILE}
		echo "GATEWAY=${GATEWAY}" >> ${NETWORK_FILE}
	fi
		
	#systemctl restart network

else
	echo "NETWORK_FILE not exist"
fi

