package com.dangdang.hamal.conf;

public class ListenerConf {

	/*	
	<listener>
	<version>1</version>
	<!-- 监听器名称,在此文件中必须唯一 -->
	<name>kafka</name>
	<!-- 监听器class所在包的名称-->
	<jar>kafka-recever-1.0.0.jar</jar>
	<!-- 类全名 -->
	<class>com.dangdang.event.listener.KafkaRecever</class>	
    </listener>
    */
	private String version;
	private String name;
	private String jar;
	private String clazz;
	
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJar() {
		return jar;
	}
	public void setJar(String jar) {
		this.jar = jar;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}


}
