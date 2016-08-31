
package com.dangdang.hamal.data;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;


/**
 * 每次变更 产生的数据 
 * IncC是 Incremental change 缩写
 * @author zhangkunjs
 *
 */
public class IncCRowData {

	public static enum IncCAction
	{
		UPDATE,INSERT,DELETE;
	}
	private long tblid;
	private String db;
	private String tbl;
	private long ts;
	private List<ArrayList<Cell>> rows;
	private IncCAction action;

	public IncCRowData()
	{
		this.rows=new ArrayList<ArrayList<Cell>>();
	}

	public long getTblid() {
		return tblid;
	}

	public void setTblid(long tblid) {
		this.tblid = tblid;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getTbl() {
		return tbl;
	}

	public void setTbl(String tbl) {
		this.tbl = tbl;
	}



	public long getTs() {
		return ts;
	}



	public void setTs(long ts) {
		this.ts = ts;
	}



	public List<ArrayList<Cell>> getRows() {
		return rows;
	}

	public void setRows(List<ArrayList<Cell>> rows) {
		this.rows = rows;
	}
	
	public String getFull()
	{
		return this.db+"."+this.tbl;
	}	

	public IncCAction getAction() {
		return action;
	}
	public void setAction(IncCAction action) {
		this.action = action;
	}
	
}
