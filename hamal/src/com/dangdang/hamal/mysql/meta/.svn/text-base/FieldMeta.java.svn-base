/**
 * copyright @ dangdang.com 2015
 */
package com.dangdang.hamal.mysql.meta;

public class FieldMeta
{
	private int f_idx;
	private String data_type;
	private String f_name;
	

	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public int getF_idx() {
		return f_idx;
	}
	public void setF_idx(int f_idx) {
		this.f_idx = f_idx;
	}

	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	
	public static FieldMeta fromQuery(String[] vals)
	{
		FieldMeta meta=new FieldMeta();		
		String sIdx=vals[0];
		String name=vals[1];
		String datatype=vals[2];		
		int idx=Integer.parseInt(sIdx);
		meta.setF_idx(idx-1);
		meta.setData_type(datatype);
		meta.setF_name(name);	
		
		return meta;
	}
	
	public String toString()
	{
		return 	 f_idx+" "+ f_name+" "+ data_type;
	}
	
}