package com.dangdang.hamal.test;

import java.util.Random;
import java.util.UUID;

import net.sf.json.JSONObject;

public class JsonTest {

	public static class Bean
	{
		private String filed1;
		private String filed2;
		
		public String getFiled1() {
			return filed1;
		}

		public void setFiled1(String filed1) {
			this.filed1 = filed1;
		}

		public String getFiled2() {
			return filed2;
		}

		public void setFiled2(String filed2) {
			this.filed2 = filed2;
		}

		public String toString()
		{
			return filed1+"\t"+filed2;
		}
	}
	
	public static void main(String[]args)
	{
//		Bean bean=new Bean();
//		bean.setFiled1("1");
//		bean.setFiled2("2");
//		JSONObject jsonObject=JSONObject.fromObject(bean);
//		System.out.println(jsonObject.toString());
//		
//		String jsonStr="{"+"filed1"+":"+"1"+","+"filed2"+":"+"2"+"}";
//		Bean bean2=(Bean)JSONObject.toBean(JSONObject.fromObject(jsonStr));
//		System.out.println(bean2.getFiled1());
		
		String URL="http://yanbian.58.com/ershouche/?key=123";	
		String flag="";
		if(URL.endsWith("ershouche/"))
			flag="1";
		else if(URL.contains("ershouche/?key="))
			flag="2";
		else 
			flag="3";		
		System.out.println(flag);
	}
}
