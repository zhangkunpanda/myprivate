/**
 * copyright @ dangdang.com 2015
 */

package com.dangdang.hamal.data;

/**
 * DDL Êý¾Ý
 * @author zhangkunjs
 *
 */
public class DdlData {

	public static enum DdlAction
	{
		ALTER_TABLE,
		CREAET_TABLE,
		RENAME_TABLE
	}
	
	private String db;
	private String table;
	private String sql;
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}	
	
}
