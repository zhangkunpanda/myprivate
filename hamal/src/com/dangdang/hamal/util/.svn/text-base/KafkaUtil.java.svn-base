package com.dangdang.hamal.util;

import java.util.Properties;
import java.util.logging.Logger;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.dangdang.hamal.conf.ListenerConf;
import com.dangdang.hamal.conf.TopologyConf.ListenerParam;
import com.dangdang.hamal.data.IncCRowData;
import com.dangdang.hamal.data.parser.IncCRowDataParser;
import com.dangdang.hamal.mysql.core.BinaryLogClient;
import com.dangdang.hamal.mysql.core.event.Event;
import com.dangdang.hamal.mysql.core.Listeners;
/**
 * kafka的通用类
 * @author zhangkunjs
 *
 */

public class KafkaUtil {
	
	public static class KafkaParam
	{
		public boolean is_debug=false;
		public String topic;
		public String serializer_class;
		public String metadata_broker_list;
		
		Properties getProps()
		{
			Properties props = new Properties();
			props.put("serializer.class", serializer_class);
			props.put("metadata.broker.list", metadata_broker_list);		
			return props;
		}
	}
	
	/**
	 * 把KafkaListener的ListenParam参数转为KafkaParam
	 * @param param
	 * @return
	 */
	public static KafkaParam convertToKafkaParam(ListenerParam param)
	{
		KafkaParam kafkaParam=new KafkaParam();
		kafkaParam.serializer_class= param.getParams().get("serializer.class");
		kafkaParam.metadata_broker_list= param.getParams().get("metadata.broker.list");
		String s_is_debug=param.getParams().get("is_debug");
		kafkaParam.is_debug=Boolean.parseBoolean(s_is_debug);
		kafkaParam.topic = param.getParams().get("topic");
		return kafkaParam;
	}
	/**
	 * 根据kafkaParam参数创建producer
	 * @param kafkaParam
	 * @return
	 */
	public static Producer<Integer, String> createProducer(KafkaParam kafkaParam)
	{
		kafka.javaapi.producer.Producer<Integer, String> producer=null;
		Properties props =kafkaParam.getProps();
		if(!kafkaParam.is_debug)
			producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));
		return producer;
	}	
}
