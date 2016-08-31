package com.dangdang.hamal.test;

import com.dangdang.hamal.event.listener.KafkaEventConsumerListener;
import com.dangdang.hamal.mysql.core.BinaryLogClient;

/**
 * binlog slave
 *
 * @author linux_china
 */
public class BinlogSlave2 {
	public static BinlogRecorder binlogRecorder = new BinlogRecorder("mysql-bin.000002", 4L);

	public static void main(String[] args) throws Exception {
		BinaryLogClient client = new BinaryLogClient("localhost", 3306, "root", "root");
//		client.setBinlogFilename(binlogRecorder.getFilename());
//		client.setBinlogPosition(binlogRecorder.getPosition());
		KafkaEventConsumerListener dissListener=new KafkaEventConsumerListener();  
	
		client.connect();
	}


}
