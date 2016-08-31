package com.dangdang.hamal.data.parser;

/**
 * ALTER TABLE `test`.`bin_log` 
 * @author zhangkunjs
 *
 */
public class SqlParser {

	public static final String SQL_PREFIX_ALTER_TABLE="ALTER TABLE".toLowerCase();

	/**
	 * 获取修改的表名称
	 * @param sql
	 * @return
	 */
	public static String getAlterTable(String sql)
	{
		String newsql=getNewSql(sql);
		if(newsql.startsWith(SQL_PREFIX_ALTER_TABLE))
		{
			String parts[]=newsql.split(" ");
			String dbtbl=parts[2];
			dbtbl=dbtbl.replace("`", "");
			if(dbtbl.contains("."))
			{
				String [] db_tbls=dbtbl.split("\\.");
				return db_tbls[1];
			}
			return dbtbl;
			
		}
		return "";
	}


	/**
	 * 去除多余空格
	 * @param sql
	 * @return
	 */
	public static String getNewSql(String sql)
	{
		sql=sql.replace("\n", " ");
		sql=sql.trim();
		String newsql="";
		boolean preSpace=false;
		for(int i=0;i<sql.length();i++)
		{
			char c=sql.charAt(i);
			if(c==' ')
			{
				if(preSpace)
				{
				}
				else
				{
					preSpace=true;
					newsql+=c;
				}
			}
			else
			{
				preSpace=false;
				newsql+=c;
			}
		}
		return newsql.toLowerCase();
	}

	public static void main(String[] args)
	{
		String sql="ALTER TABLE `bin_log` "+
				"DROP COLUMN `what`;";
		String newsql=SqlParser.getNewSql(sql);
		System.out.println(newsql);
		System.out.println(getAlterTable(newsql));
	}

}
