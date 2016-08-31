package com.dangdang.hamal.conf;

import java.util.HashMap;
import java.util.Map;

public class TopologyConf {

	public static class ListenerParam
	{		
		
		private Map<String, String> params;

		public ListenerParam()
		{
			this.params=new HashMap<String,String>();
		}
		
		public Map<String, String> getParams() {
			return params;
		}
		public void setParams(Map<String, String> params) {
			this.params = params;
		}		
		
		public void put(String key,String value)
		{
			this.params.put(key, value);
		}
	}
	
	/*<topology id="FROM_MYSQL_ORDER_TO_KAFKA_IDC3">
	<db type="mysql">
		<param key="ip" value="localhost" />
		<param key="port" value="3306" />
		<param key="username" value="root" />
		<param key="password" value="root" />
	</db>
	<listener name="kafka">
		<param key="ip" value="myipanapdb.idc3" />
		<param key="port" value="3306" />
		<param key="dbname" value="analyse_shop" />
		<param key="username" value="readuser" />
		<param key="password" value="password" />
		<param key="tables" value="shop_searchkey_detail_150104" />
		<param key="columns"
			value="id,product_id,replace(replace(replace(replace(replace(product_name,'|',' '),char(9),','),char(10),','),char(13),''),'\0',''),shop_id,replace(replace(replace(replace(keyword,'|',' '),char(13),','),char(10),','),'\0',''),click_pv,click_uv,order_num,order_uv,order_quantity,order_price,creation_date,date_add(date_format('2015-01-05','%Y-%m-%d'),interval -1 day),now()" />
		<param key="where" value="" />
		<param key="sql" value="" />
		<param key="encoding" value="GBK" />
		<param key="mysql.params" value="" />
		<param key="concurrency" value="1" />
		<!-- 能接收到的eventType,多个用逗号分隔 -->
		<param key="filter_eventtype" value="query,format_desc" />
	</listener>
</topology>*/
	private String id;
	private LogOriginConf sourceConf;
	private Map<String,ListenerParam> listenerParams;
	
	public TopologyConf()
	{
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	public LogOriginConf getSourceConf() {
		return sourceConf;
	}

	public void setSourceConf(LogOriginConf sourceConf) {
		this.sourceConf = sourceConf;
	}

	public Map<String, ListenerParam> getListenerParams() {
		return listenerParams;
	}

	public void setListenerParams(Map<String, ListenerParam> listenerParams) {
		this.listenerParams = listenerParams;
	}
	
}
