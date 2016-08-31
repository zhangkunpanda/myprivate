package com.dangdang.hamal.event.listener;

import java.io.IOException;

import com.dangdang.hamal.conf.TopologyConf.ListenerParam;
import com.dangdang.hamal.data.parser.SqlParser;
import com.dangdang.hamal.mysql.connector.MysqlConnector;
import com.dangdang.hamal.mysql.core.BinaryLogClient;
import com.dangdang.hamal.mysql.core.Listeners;
import com.dangdang.hamal.mysql.core.event.Event;
import com.dangdang.hamal.mysql.core.event.EventData;
import com.dangdang.hamal.mysql.core.event.EventType;
import com.dangdang.hamal.mysql.core.event.QueryEventData;
import com.dangdang.hamal.mysql.core.event.TableMapEventData;
import com.dangdang.hamal.mysql.core.event.TableMapEventDataCache;
import com.dangdang.hamal.mysql.core.network.protocol.ResultSetRowPacket;
import com.dangdang.hamal.mysql.meta.TableMeta;
import com.dangdang.hamal.mysql.meta.TableMetaCache;

public class RefreshTableMetaMapListener implements Listeners.EventListener{

	@Override
	public String getName() {
		return "RefreshTableMetaMap";
	}

	@Override
	public void onStart(ListenerParam param) {
	}
	
	@Override
	public void onEvent(Event event) {
		EventType et=event.getHeader().getEventType();
		EventData eventData=event.getData();
		refreshTableMetaCache(et,eventData);
	}

	private void refreshTableMetaCache(EventType et,EventData eventData)
	{
		if(et==EventType.TABLE_MAP)
		{
			TableMapEventData tableMapEvent = (TableMapEventData) eventData;
			TableMapEventDataCache.put(tableMapEvent);
			String db=tableMapEvent.getDatabase();
			String table=tableMapEvent.getTable();
			String full=db+"."+table;
			try {
				if(!TableMetaCache.exist(full)){
					ResultSetRowPacket[] resultSet=	MysqlConnector.getSingleton().getTableMeta(db, table);			
					TableMeta tableMeta= TableMeta.fromQuery(db, table, resultSet);
					TableMetaCache.put(full, tableMeta);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(et==EventType.QUERY)
		{
			QueryEventData queryEventData = (QueryEventData) eventData;
			String db=queryEventData.getDatabase();
			String sql=queryEventData.getSql();
			String newsql=SqlParser.getNewSql(sql);
			//ALTER TABLE `test`.`bin_log` 
			//ADD COLUMN `test123` VARCHAR(45) NULL AFTER `bin_logcol`;			
			if(newsql.startsWith(SqlParser.SQL_PREFIX_ALTER_TABLE))
			{			
				String table=SqlParser.getAlterTable(newsql);
				System.out.println("+++ALTER TABLE:"+table);
				String full=db+"."+table;
				try {
					ResultSetRowPacket[] resultSet=	MysqlConnector.getSingleton().getTableMeta(db, table);			
					TableMeta tableMeta= TableMeta.fromQuery(db, table, resultSet);
					TableMetaCache.put(full, tableMeta);					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
