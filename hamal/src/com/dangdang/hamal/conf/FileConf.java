package com.dangdang.hamal.conf;

public class FileConf extends LogOriginConf{

	private String logFilename;
	private long logPosition;
	
	private String dbhost;
	private int port;
	private String username;
	private String password;
	
	public String getLogFilename() {
		return this.params.get("logfilename");
	}
	public void setLogFilename(String logFilename) {
		this.logFilename = logFilename;
	}
	public long getLogPosition() {
		String sLogPosition=this.params.get("logfilename");
		return Long.parseLong(sLogPosition);
	}
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
