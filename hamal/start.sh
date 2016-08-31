#########################################################################
# File Name: start.sh
# Author: ma6174
# mail: ma6174@163.com
# Created Time: Thu 05 Feb 2015 09:41:13 AM CST
#########################################################################
#!/bin/bash
#!/bin/sh   

RUN_HOME=/home/hadoop/zhangkun/hamal
LIBS=$RUN_HOME/lib/*

for file in $LIBS
do
	export CLASSPATH=$CLASSPATH:$file;
done
echo $CLASSPATH;

java com.dangdang.hamal.topology.Topology
#java com.dangdang.mysql.binlog.BinaryLogFileReader /home/hadoop/zhangkun/mysql-bin.000001
#java com.dangdang.mysql.binlog.test.BinlogSlave
#java com.dangdang.mysql.binlog.test.SocketTest2

