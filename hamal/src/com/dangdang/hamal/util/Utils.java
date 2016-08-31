package com.dangdang.hamal.util;

import net.sf.json.JSONObject;

public class Utils {

	public static String toJson(Object obj)
	{
		JSONObject jsonObject=JSONObject.fromObject(obj);
		return jsonObject.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
