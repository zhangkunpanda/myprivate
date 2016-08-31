package com.dangdang.hamal.mysql.core.event;

import java.util.HashMap;
import java.util.Map;

public class TableMapEventDataCache {

	private static Map<Long, TableMapEventData> tableidTableMapEventDataMap;
	
	static
	{
		tableidTableMapEventDataMap=new HashMap<Long, TableMapEventData>();
	}
	
	public static void put(TableMapEventData tableMapEventData)
	{
		tableidTableMapEventDataMap.put(tableMapEventData.getTableId(), tableMapEventData);
	}
	
	public static TableMapEventData get(long tableid)
	{
		if(tableidTableMapEventDataMap.containsKey(tableid))
		{
			return tableidTableMapEventDataMap.get(tableid);
		}
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
