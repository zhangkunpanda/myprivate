/**
 * copyright @ dangdang.com 2015
 */
package com.dangdang.hamal.data.parser;

import java.io.IOException;

import com.dangdang.hamal.data.DdlData;
import com.dangdang.hamal.mysql.connector.MysqlConnector;
import com.dangdang.hamal.mysql.core.event.DeleteRowsEventData;
import com.dangdang.hamal.mysql.core.event.Event;
import com.dangdang.hamal.mysql.core.event.EventType;
import com.dangdang.hamal.mysql.core.event.QueryEventData;
import com.dangdang.hamal.mysql.core.network.protocol.ResultSetRowPacket;
import com.dangdang.hamal.mysql.meta.TableMeta;
import com.dangdang.hamal.mysql.meta.TableMetaCache;

/**
 * DDL½âÎö
 * @author zhangkunjs
 *
 */
public class DdlDataParser {

	public static DdlData  parse(Event event)
	{
		EventType et=event.getHeader().getEventType();

		if(et==EventType.QUERY)
		{
			QueryEventData queryEventData = (QueryEventData) event.getData();
			String db=queryEventData.getDatabase();
			String sql=queryEventData.getSql();
			String newsql=SqlParser.getNewSql(sql);
			DdlData data=new DdlData();
			data.setSql(newsql);
			data.setSql(db);
			if(newsql.startsWith(SqlParser.SQL_PREFIX_ALTER_TABLE))
			{
				String table=SqlParser.getAlterTable(newsql);
				data.setTable(table);			
			}	
			return data;
		}

		return null;		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
