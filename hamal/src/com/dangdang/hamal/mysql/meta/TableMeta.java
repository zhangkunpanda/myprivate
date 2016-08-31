/**
 * copyright @ dangdang.com 2015
 */
package com.dangdang.hamal.mysql.meta;

import com.dangdang.hamal.mysql.core.network.protocol.ResultSetRowPacket;


public  class TableMeta implements Comparable<TableMeta>
{
	private String db;
	private String table;
	private String fullname;	
	private FieldMeta[] fields;	


	public String getFullname() {
		return db+"."+table;
	}
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

	public FieldMeta[] getFields() {
		return fields;
	}
	public void setFields(FieldMeta[] fields) {
		this.fields = fields;
	}

	public static TableMeta fromQuery(String db,String table, ResultSetRowPacket[] resultSet)
	{
		TableMeta tableMeta=new TableMeta();
		tableMeta.setDb(db);
		tableMeta.setTable(table);

		FieldMeta[] fields=new FieldMeta[resultSet.length];
		int i=0;
		for(ResultSetRowPacket row:resultSet){
			String[] vals=row.getValues();
			fields[i]=FieldMeta.fromQuery(vals);
			i++;
		}
		tableMeta.setFields(fields);
		return tableMeta;
	}


	@Override
	public int compareTo(TableMeta other) {
		int cmp=this.db.compareTo(other.db);
		if(cmp==0)
		{
			return this.table.compareTo(other.table);
		}
		return cmp;
	}		
}