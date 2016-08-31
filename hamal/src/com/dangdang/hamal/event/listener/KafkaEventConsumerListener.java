package com.dangdang.hamal.event.listener;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import com.dangdang.hamal.conf.TopologyConf.ListenerParam;
import com.dangdang.hamal.data.IncCRowData;
import com.dangdang.hamal.data.parser.IncCRowDataParser;
import com.dangdang.hamal.mysql.core.Listeners;
import com.dangdang.hamal.mysql.core.event.Event;
import com.dangdang.hamal.util.KafkaUtil;
import com.dangdang.hamal.util.KafkaUtil.KafkaParam;
import com.dangdang.hamal.util.Utils;

public class KafkaEventConsumerListener implements Listeners.EventListener{

	private BlockingQueue<Event> queue;	

	private static final int NUM_THREADS=5;

	public static class KafkaConsumer extends Thread
	{	
		Producer<Integer, String> producer;		
		KafkaParam kafkaParam;

		private int indx;
		
		
		public int getIndx() {
			return indx;
		}
		
		public String getConsumerName()
		{
			return "consumer-"+indx;
		}

		public void setIndx(int indx) {
			this.indx = indx;
		}

		public void setQueue(BlockingQueue<Event> queue) {
			this.queue = queue;
		}

		private BlockingQueue<Event> queue;

		public KafkaConsumer(final KafkaParam kafkaParam)
		{
			this.kafkaParam=kafkaParam;
			this.producer=KafkaUtil.createProducer(kafkaParam);
		}

		public void consume(Event e)
		{
			IncCRowData ic=IncCRowDataParser.parse(e);	
			if( ic!=null)
			{
				String json=Utils.toJson(ic);			
				if(!kafkaParam.is_debug)
				{			
					producer.send(new KeyedMessage<Integer, String>(kafkaParam.topic,json ));
				}
				System.out.println(getConsumerName()+":event creat ts:"+e.getHeader().getTimestamp()+":spout ts:"+e.getSpoutTs()+":to kafka ts:"+System.currentTimeMillis()+"\n----"+json);
			}
			else
			{
				System.out.println(getConsumerName()+":"+System.currentTimeMillis()+"\n----"+e.getHeader());
			}
		}

		@Override
		public void run() {
			while(true)
			{
				try {
					Event e=this.queue.take();
					consume(e);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(ListenerParam param) {		
		queue=new LinkedBlockingQueue<Event>();
		KafkaConsumer[] consumers=new KafkaConsumer[NUM_THREADS];
		KafkaParam kafkaParam=KafkaUtil.convertToKafkaParam(param);
		for(int i=0;i<NUM_THREADS;i++)
		{		
			consumers[i]=new KafkaConsumer(kafkaParam);
			consumers[i].setIndx(i);
			consumers[i].setQueue(queue);
			consumers[i].start();
		}
	}

	@Override
	public void onEvent(Event event) {	
		try {
			queue.put(event);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
