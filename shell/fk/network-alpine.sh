#!/bin/bash
start_nic_config=0
linenum=0
config_address=0
config_netmask=0
config_gateway=0
is_dhcp=0 #0: do not change; 1: do dhcp; 2:do static


function usage() {
    echo "-a config address"
    echo "-i specify nic name"
    echo "-f specify file path"
    echo "-d specify this nic use dhcp mode"
    echo "-s specify this nic use static mode"
    echo "-n config netmask"
    echo "-g config gateway"
    echo "-h print this usage"
}
while getopts ":a:i:f:n:g:dsh" opt
do
	case $opt in
		a )
            config_address=1
            address=$OPTARG;;
		i ) nic=$OPTARG;;
		f ) filename=$OPTARG;;
        d ) is_dhcp=1;;
        s ) is_dhcp=2;;
		n ) 
            config_netmask=1
            netmask=$OPTARG;;
		g ) 
            config_gateway=1
            gateway=$OPTARG;;
		h ) usage
			exit 1;;
	esac
done
echo "filename=${filename}"

function insert_config() {
    line=$1
    action=$2
     # insert remaining configs
    if [[ $config_netmask -eq 1 ]];then
        sed -i "${line}${action}        netmask $netmask" $filename
        sed -i -e "${line}s/^/     /" $filename
        config_network=0
    fi
    if [[ $config_address -eq 1 ]];then
        sed -i "${line}${action}    address $address" $filename
        sed -i -e "${line}s/^/     /" $filename
        config_address=0
    fi
    if [[ $config_gateway -eq 1 ]];then
        sed -i "${line}${action}    gateway $gateway" $filename
        sed -i -e "${line}s/^/     /" $filename
        config_gateway=0
    fi
}
function replace_config() {
	if [[ $start_nic_config -eq 1 && $config_netmask -eq 1 && $myline =~ "netmask" && "x$netmask" != "x" ]];then
		sed -i  "${linenum}s/^.*$/     netmask ${netmask}/" $filename
        config_netmask=0
	fi
	if [[ $start_nic_config -eq 1 && $config_address -eq 1 && $myline =~ "address" && "x$address" != "x" ]];then
		sed -i  "${linenum}s/^.*$/     address ${address}/" $filename
        config_address=0
	fi
	if [[ $start_nic_config -eq 1 && $config_gateway -eq 1 && $myline =~ "gateway" && "x$gateway" != "x" ]];then
		sed -i  "${linenum}s/^.*$/     gateway ${gateway}/" $filename
        config_gateway=0
	fi
}
while read myline
do
    let linenum=linenum+1
    if [[ $myline =~ "auto" ]];then
	    if [[ $myline =~ $nic ]];then
		    start_nic_config=1
            let dhcp_line=linenum+1
            if [[ $is_dhcp -eq 1 ]];then
                sed -i "${dhcp_line}s/static/dhcp/" $filename
            elif [[ $is_dhcp -eq 2 ]];then
                sed -i "${dhcp_line}s/dhcp/static/" $filename
            fi
	    elif [[ $start_nic_config -eq 1 ]];then
            # insert config
            insert_config $linenum i
            start_nic_config=0
        fi
    fi
    replace_config $linenum
done < $filename

if [[ $start_nic_config -eq 1 ]];then
    # append configs
    insert_config $linenum a
    start_nic_config=0
fi

#/etc/init.d/sshd restart
