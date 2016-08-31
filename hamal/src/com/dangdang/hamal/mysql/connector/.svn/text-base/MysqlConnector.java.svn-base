package com.dangdang.hamal.mysql.connector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.dangdang.hamal.conf.LogOriginConf;
import com.dangdang.hamal.conf.MysqlConf;
import com.dangdang.hamal.mysql.core.command.AuthenticateCommand;
import com.dangdang.hamal.mysql.core.command.QueryCommand;
import com.dangdang.hamal.mysql.core.network.AuthenticationException;
import com.dangdang.hamal.mysql.core.network.protocol.ErrorPacket;
import com.dangdang.hamal.mysql.core.network.protocol.GreetingPacket;
import com.dangdang.hamal.mysql.core.network.protocol.PacketChannel;
import com.dangdang.hamal.mysql.core.network.protocol.ResultSetRowPacket;
import com.dangdang.hamal.mysql.meta.TableMeta;

/**
 * Mysql连接器 
 * 主要用来从服务器查询信息 
 * @author zhangkunjs
 *
 */
public class MysqlConnector {

	private final String hostname;
	private final int port;
	private final String schema;
	private final String username;
	private final String password;

	public MysqlConnector(MysqlConf conf)
	{
		this.hostname = conf.getDbhost();
		this.port = conf.getPort();
		this.schema = null;
		this.username = conf.getUsername();
		this.password = conf.getPassword();
	}
	
	public MysqlConnector(String hostname, int port, String schema, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.schema = schema;
		this.username = username;
		this.password = password;
	}

	private PacketChannel channel;

	public void connect() throws IOException
	{
		Socket socket =  new Socket();
		socket.connect(new InetSocketAddress(hostname, port));
		channel = new PacketChannel(socket);
		GreetingPacket greetingPacket = new GreetingPacket(channel.read());
		login(greetingPacket.getScramble(), greetingPacket.getServerCollation());
	}

	private void login(String salt, int collation) throws IOException {
		AuthenticateCommand authenticateCommand = new AuthenticateCommand(schema, username, password, salt);
		authenticateCommand.setCollation(collation);
		channel.write(authenticateCommand);
		byte[] authenticationResult = channel.read();
		if (authenticationResult[0] != (byte) 0x00 /* ok */) {
			if (authenticationResult[0] == (byte) 0xFF /* error */) {
				byte[] bytes = Arrays.copyOfRange(authenticationResult, 1, authenticationResult.length);
				throw new AuthenticationException(new ErrorPacket(bytes).getErrorMessage());
			}
			throw new AuthenticationException("Unexpected authentication result (" + authenticationResult[0] + ")");
		}
	}

	public ResultSetRowPacket[] query(String sql) throws IOException
	{
		channel.write(new QueryCommand(sql));
		ResultSetRowPacket[] resultSet = readResultSet();
		return resultSet;
	}

	public ResultSetRowPacket[] getTableMeta(String db,String table) throws IOException
	{
		String sqlFormat="select ORDINAL_POSITION,COLUMN_NAME,DATA_TYPE from information_schema.COLUMNS  where TABLE_SCHEMA = '%s' and TABLE_NAME ='%s';";
		String sql=String.format(sqlFormat,db,table);
		ResultSetRowPacket[] resultSet=query(sql);
		return resultSet;
	}
	
	private ResultSetRowPacket[] readResultSet() throws IOException {
		List<ResultSetRowPacket> resultSet = new LinkedList<ResultSetRowPacket>();
		while ((channel.read())[0] != (byte) 0xFE /* eof */) { /* skip */ }
		for (byte[] bytes; (bytes = channel.read())[0] != (byte) 0xFE /* eof */; ) {
			resultSet.add(new ResultSetRowPacket(bytes));
		}
		return resultSet.toArray(new ResultSetRowPacket[resultSet.size()]);
	}

	public void disconnect()
	{
		if (channel != null && channel.isOpen()) {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static MysqlConnector instance;

	public static MysqlConnector createSingleton (String hostname, int port, String username, String password)
	{
		if(instance==null)
		{
			instance=new MysqlConnector(hostname,port , null,  username,  password);
			try {
				instance.connect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}
	public static MysqlConnector getSingleton()
	{
		return instance;
	}
	

	public static void main(String[] args) throws IOException {
		MysqlConnector connector=new MysqlConnector("localhost",3306,null,"root","root");
		connector.connect();	
		String sql="select ORDINAL_POSITION,COLUMN_NAME,DATA_TYPE from information_schema.COLUMNS  where TABLE_SCHEMA = 'test' and TABLE_NAME ='bin_log';";
		ResultSetRowPacket[] resultSet=connector.query(sql);
		if(resultSet!=null)					;
		{
			System.out.println("+++refreshTableMetaCache result:");
			for(ResultSetRowPacket row:resultSet)
			{
				System.out.println(Arrays.toString(row.getValues()));
			}
		}
		TableMeta tableMeta=TableMeta.fromQuery("test", "bin_log", resultSet);
		System.out.println(Arrays.toString(tableMeta.getFields()));
		connector.disconnect();	
	}

}
