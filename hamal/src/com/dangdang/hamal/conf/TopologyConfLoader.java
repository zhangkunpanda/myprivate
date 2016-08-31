package com.dangdang.hamal.conf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dangdang.hamal.conf.TopologyConf.ListenerParam;

public class TopologyConfLoader {

	private static final Logger LOG = Logger.getLogger(TopologyConfLoader.class);
	
	public static TopologyConf loadTopologyConfig() {
		
		TopologyConf topologyConf = new TopologyConf();
		Document document=null;
		try {
			String xml = FileUtils
					.readFileToString(new File(Constants.TOPOLOGY_XML), "UTF-8");
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			LOG.error("DataX Can not find " + Constants.TOPOLOGY_XML + " .");
			System.out.println(e.getCause());
		} catch (IOException e) {
			LOG.error(String.format("DataX read config file %s failed .",
					Constants.TOPOLOGY_XML));
			System.out.println(e.getCause());
		}

		String xpath = "/topology";
		Element topologyE = (Element) document.selectSingleNode(xpath);
		topologyConf.setId(topologyE.attributeValue("id"));

		LogOriginConf sourceConf =null;
		Element dbE = (Element) document
				.selectSingleNode(xpath + "/logsource");
		String sourceType = dbE.attributeValue("type");
		
		if(sourceType.equals(LogOriginConf.LOT_MYSQL))
		{
			sourceConf=new MysqlConf();
		}
		else if(sourceType.equals(LogOriginConf.LOT_FILE))
		{
			sourceConf=new FileConf();
		}
		else if(sourceType.equals(LogOriginConf.LOT_MIXED))
		{
			sourceConf=new MixedConf();
		}
		
		Map<String,String > dbParamMap=new HashMap<String,String>();
		List<Element> dbParamE = (List<Element>) dbE
				.selectNodes("param");
		for (Element e : dbParamE) {
			dbParamMap.put(e.attributeValue("key").toLowerCase(),
					e.attributeValue("value").trim());
		}
		sourceConf.setType(sourceType);
		sourceConf.setParams(dbParamMap);

		Map<String,ListenerParam> nameParamMap=new HashMap<String,ListenerParam> ();
		List<Element> listenerEs = (List<Element>) document.selectNodes(xpath
				+ "/listener");
		for (Element listenerE : listenerEs) {
			ListenerParam listenerParam = new ListenerParam();

			String listener_name = listenerE.attributeValue("name");
			List<Element> listenerParamE = (List<Element>) listenerE
					.selectNodes("param");
			for (Element e : listenerParamE) {
				listenerParam
				.put(e.attributeValue("key").toLowerCase(),
						e.attributeValue("value").trim());
			}
			nameParamMap.put(listener_name, listenerParam);
		}

		topologyConf.setSourceConf(sourceConf);
		topologyConf.setListenerParams(nameParamMap);

		return topologyConf;
	}

}
