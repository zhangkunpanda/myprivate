package com.dangdang.hamal.conf;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class ListenerConfLoader {

	private static final Logger LOG = Logger.getLogger(ListenerConfLoader.class);
	@SuppressWarnings("unchecked")
	public static Map<String, ListenerConf> loadListenerConfig()  {
		Map<String, ListenerConf> listenerConfs = new HashMap<String, ListenerConf>();
		File file = new File(Constants.LISTENER_XML);
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(file);
		} catch (DocumentException e) {
			LOG.error("DataX Can not find " + Constants.LISTENER_XML);
			System.out.print(e.getCause());
		}
		String xpath = "/listeners/listener";
		List<Node> pluginnode = (List<Node>) document.selectNodes(xpath);
		for (Node node : pluginnode) {
			ListenerConf plugin = new ListenerConf();
			plugin.setVersion(node.selectSingleNode("./version")
					.getStringValue());
			plugin.setName(node.selectSingleNode("./name").getStringValue());
			plugin.setJar(node.selectSingleNode("./jar").getStringValue());		
			plugin.setClazz(node.selectSingleNode("./class")
					.getStringValue());
			listenerConfs.put(plugin.getName(), plugin);
		}
		return listenerConfs;
	}

}
