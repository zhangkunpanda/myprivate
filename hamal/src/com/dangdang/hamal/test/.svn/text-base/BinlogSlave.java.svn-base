package com.dangdang.hamal.test;

import com.dangdang.hamal.conf.ListenerConf;
import com.dangdang.hamal.conf.TopologyConf.ListenerParam;
import com.dangdang.hamal.mysql.core.BinaryLogClient;
import com.dangdang.hamal.mysql.core.Listeners;
import com.dangdang.hamal.mysql.core.event.*;

/**
 * binlog slave
 *
 * @author linux_china
 */
public class BinlogSlave {
	
    public static BinlogRecorder binlogRecorder = new BinlogRecorder("mysql-bin.000001", 4L);

    public static void main(String[] args) throws Exception {
        BinaryLogClient client = new BinaryLogClient("localhost", 3306, "root", "root");
        client.setBinlogFilename(binlogRecorder.getFilename());
        client.setBinlogPosition(binlogRecorder.getPosition());       
        
        client.registerEventListener(new Listeners.EventListener() {
            private DmlSentence dmlSentence;
            private DdlSentence ddlSentence;

            public void onEvent(Event event) {
                EventHeaderV4 header = event.getHeader();
                EventData data = event.getData();
                if (data instanceof RotateEventData) {
                    RotateEventData rotateEventData = (RotateEventData) data;
                    binlogRecorder.setFilename(rotateEventData.getBinlogFilename());
                    binlogRecorder.setPosition(rotateEventData.getBinlogPosition());
                } else if (data instanceof QueryEventData) {  //start
                    QueryEventData queryEventData = (QueryEventData) data;
                    String database = queryEventData.getDatabase();
                    String sql = queryEventData.getSql();
                    if ("BEGIN".equalsIgnoreCase(sql)) {
                        dmlSentence = new DmlSentence();
                        dmlSentence.setStartedAt(header.getTimestamp());
                        dmlSentence.setDatabase(queryEventData.getDatabase());
                        dmlSentence.setExecuteTime(queryEventData.getExecutionTime());
                    } else if ("COMMIT".equalsIgnoreCase(sql)) {

                    } else {
                        ddlSentence = new DdlSentence(database, sql);
                        fire(ddlSentence);
                        binlogRecorder.setPosition(header.getNextPosition());
                        ddlSentence = null;
                    }
                } else if (data instanceof TableMapEventData) { //table
                    TableMapEventData tableMapEventData = (TableMapEventData) data;
                    dmlSentence.setDatabase(tableMapEventData.getDatabase());
                    dmlSentence.setTable(tableMapEventData.getTable());
                } else if (data instanceof UpdateRowsEventData) { //update
                    dmlSentence.setType("update");
                    dmlSentence.setData(data);
                } else if (data instanceof WriteRowsEventData) { //insert
                    dmlSentence.setType("insert");
                    dmlSentence.setData(data);
                } else if (data instanceof DeleteRowsEventData) {  //delete
                    dmlSentence.setType("delete");
                    dmlSentence.setData(data);
                } else if (data instanceof XidEventData) {
                    dmlSentence.setEndedAt(header.getTimestamp());
                    fire(dmlSentence);
                    binlogRecorder.setPosition(header.getNextPosition());
                    dmlSentence = null;
                }
            }

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "test";
			}

			@Override
			public void onStart(ListenerParam param) {
				// TODO Auto-generated method stub
				
			}
        });
        client.connect();
    }
	public static void fire(DmlSentence sentence) {
		System.out.println("x:DML -> " + sentence.getDatabase() + ":" + sentence.getTable() + ":" + sentence.getType());
	}

	public static void fire(DdlSentence sentence) {
		System.out.println("x:DDL -> " + sentence.getDatabase() + ":" + sentence.getSql());
	}
 
}
