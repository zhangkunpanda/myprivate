/**
 * copyright @ dangdang.com 2015
 */
package com.dangdang.hamal.mysql.meta;

import java.util.HashMap;
import java.util.Map;

import com.dangdang.hamal.mysql.core.network.protocol.ResultSetRowPacket;

public class TableMetaCache {

	private static Map<String,TableMeta> fullNameTableMap;

	static 
	{
		fullNameTableMap=new HashMap<String,TableMeta>();
	}

	public static void put(String fullname,TableMeta table)
	{
		fullNameTableMap.put(fullname, table);
	}

	public static boolean exist(String fullname)
	{
		return fullNameTableMap.containsKey(fullname);
	}

	public static TableMeta get(String fullname)
	{
		if(fullNameTableMap.containsKey(fullname))
		{
			return fullNameTableMap.get(fullname);
		}
		return null;
	}
	
}
