package com.dangdang.hamal.event.listener;

import java.util.Properties;
import java.util.logging.Logger;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.dangdang.hamal.conf.ListenerConf;
import com.dangdang.hamal.conf.TopologyConf.ListenerParam;
import com.dangdang.hamal.data.IncCRowData;
import com.dangdang.hamal.data.parser.IncCRowDataParser;
import com.dangdang.hamal.mysql.core.BinaryLogClient;
import com.dangdang.hamal.mysql.core.event.Event;
import com.dangdang.hamal.util.Utils;
import com.dangdang.hamal.mysql.core.Listeners;
/**
 * 发送数据到kafka的监听器 
 * @author zhangkunjs
 *
 */

public class KafkaListener implements Listeners.EventListener{
	private final Logger logger = Logger.getLogger(getClass().getName());
	private  kafka.javaapi.producer.Producer<Integer, String> producer;
	private  String topic;
	private  Properties props = new Properties();

	private boolean is_debug =false;

	@Override
	public void onEvent(Event event) {
		IncCRowData ic=IncCRowDataParser.parse(event);
		String messageStr=getName()+":"+event.getHeader()+"/"+event.getData();		
		if(ic!=null)
			logger.info("kafka:"+Utils.toJson(ic));
		else 
			logger.info("kafka:"+messageStr);
		if(!is_debug)
			if(ic!=null)
			{			
				producer.send(new KeyedMessage<Integer, String>(topic, Utils.toJson(ic)));
			}
	}

	@Override
	public String getName() {
		return "kafka";
	}

	@Override
	public void onStart(ListenerParam param) {
		System.out.println("start KAFKA params:");
		for(String key:param.getParams().keySet())
		{
			System.out.println("----"+key+"-->"+param.getParams().get(key));
		}
		props.put("serializer.class", param.getParams().get("serializer.class"));
		props.put("metadata.broker.list", param.getParams().get("metadata.broker.list"));
		String s_is_debug=param.getParams().get("is_debug");
		is_debug=Boolean.parseBoolean(s_is_debug);
		this.topic = param.getParams().get("topic");
		if(!is_debug)
			producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));

	}


}
