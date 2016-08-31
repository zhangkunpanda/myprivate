package com.dangdang.hamal.event.listener;

import java.util.logging.Logger;

import com.dangdang.hamal.conf.ListenerConf;
import com.dangdang.hamal.conf.TopologyConf.ListenerParam;
import com.dangdang.hamal.mysql.core.BinaryLogClient;
import com.dangdang.hamal.mysql.core.Listeners;
import com.dangdang.hamal.mysql.core.event.DeleteRowsEventData;
import com.dangdang.hamal.mysql.core.event.Event;
import com.dangdang.hamal.mysql.core.event.EventData;
import com.dangdang.hamal.mysql.core.event.EventHeaderV4;
import com.dangdang.hamal.mysql.core.event.QueryEventData;
import com.dangdang.hamal.mysql.core.event.RotateEventData;
import com.dangdang.hamal.mysql.core.event.TableMapEventData;
import com.dangdang.hamal.mysql.core.event.UpdateRowsEventData;
import com.dangdang.hamal.mysql.core.event.WriteRowsEventData;
import com.dangdang.hamal.mysql.core.event.XidEventData;
import com.dangdang.hamal.test.DdlSentence;
import com.dangdang.hamal.test.DmlSentence;

/**
 * Ä¬ÈÏÊä³ö¼àÌýÆ÷
 * @author zhangkunjs
 *
 */
public class DefaultListener implements Listeners.EventListener{

	private final Logger _LOGGER_ = Logger.getLogger(getClass().getName());

	private DmlSentence dmlSentence;
	private DdlSentence ddlSentence;
	public void onEvent(Event event) {
		System.out.println("default listener:");
		System.out.println("----"+event.getHeader().toString());
		System.out.println("----"+event.getData().toString());

		EventHeaderV4 header = event.getHeader();
		EventData data = event.getData();
		if (data instanceof RotateEventData) {
			RotateEventData rotateEventData = (RotateEventData) data;
			//			binlogRecorder.setFilename(rotateEventData.getBinlogFilename());
			//			binlogRecorder.setPosition(rotateEventData.getBinlogPosition());
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
			dmlSentence = null;
		}
	}

	public  void fire(DmlSentence sentence) {
		_LOGGER_.info(getName()+":" + sentence.getDatabase() + ":" + sentence.getTable() + ":" + sentence.getType());
	}

	public  void fire(DdlSentence sentence) {
		_LOGGER_.info(getName()+":" + sentence.getDatabase() + ":" + sentence.getSql());
	}


	@Override
	public void onStart(ListenerParam param) {
		_LOGGER_.info("start Default params:");
		if(param.getParams()!=null&&param.getParams().size()>0)
			for(String key:param.getParams().keySet())
			{
				_LOGGER_.info("----key:"+key+",val:"+param.getParams().get(key));
			}	
	}

	@Override
	public String getName() {
		return "default";
	}
}
