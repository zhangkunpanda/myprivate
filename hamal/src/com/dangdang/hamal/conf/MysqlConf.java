package com.dangdang.hamal.conf;

public class MysqlConf extends LogOriginConf{
	private String dbhost;
	private int port;
	private String username;
	private String password;
	
	
	public String getDbhost() {
		return this.params.get("dbhost");
	}
	public void setDbhost(String dbhost) {
		this.dbhost = dbhost;
	}
	public int getPort() {
		String sPort= this.params.get("port");
		return Integer.parseInt(sPort);
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return this.params.get("username");
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return this.params.get("password");
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
