/*
 * Copyright 2013
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dangdang.hamal.mysql.core;

import com.dangdang.hamal.event.listener.DefaultListener;
import com.dangdang.hamal.io.ByteArrayInputStream;
import com.dangdang.hamal.mysql.core.event.Event;
import com.dangdang.hamal.mysql.core.event.parser.EventDeserializer;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

/**
 * MySQL binary log file reader.
 *
 * @author  
 */
public class BinaryLogFileReader implements Closeable {

	public static final byte[] MAGIC_HEADER = new byte[]{(byte) 0xfe, (byte) 0x62, (byte) 0x69, (byte) 0x6e};

	private final ByteArrayInputStream inputStream;
	private final EventDeserializer eventDeserializer;

	private final List<Listeners.EventListener> eventListeners = new LinkedList<Listeners.EventListener>();
	
	public BinaryLogFileReader(File file) throws IOException {
		this(file, new EventDeserializer());
	}

	public BinaryLogFileReader(File file, EventDeserializer eventDeserializer) throws IOException {
		this(file != null ? new BufferedInputStream(new FileInputStream(file)) : null, eventDeserializer);
	}

	public BinaryLogFileReader(InputStream inputStream) throws IOException {
		this(inputStream, new EventDeserializer());
	}

	public BinaryLogFileReader(InputStream inputStream, EventDeserializer eventDeserializer) throws IOException {
		if (inputStream == null) {
			throw new IllegalArgumentException("Input stream cannot be NULL");
		}
		if (eventDeserializer == null) {
			throw new IllegalArgumentException("Event deserializer cannot be NULL");
		}
		this.inputStream = new ByteArrayInputStream(inputStream);
		try {
			byte[] magicHeader = this.inputStream.read(MAGIC_HEADER.length);
			if (!Arrays.equals(magicHeader, MAGIC_HEADER)) {
				throw new IOException("Not a valid binary log");
			}
		} catch (IOException e) {
			try {
				this.inputStream.close();
			} catch (IOException ex) {
				// ignore
			}
			throw e;
		}
		this.eventDeserializer = eventDeserializer;
	}

	/**
	 * @return deserialized event or null in case of end-of-stream
	 */
	public Event readEvent() throws IOException {
		Event event= eventDeserializer.nextEvent(inputStream);
		notifyEventListeners(event);
		return event;
	}

	public void registerEventListener(Listeners.EventListener eventListener) {
		synchronized (eventListeners) {
			eventListeners.add(eventListener);
		}
	}
	
	private void notifyEventListeners(Event event) {
		synchronized (eventListeners) {
			for (Listeners.EventListener eventListener : eventListeners) {
				try {
					eventListener.onEvent(event);
				} catch (Exception e) {
					
				}
			}
		}
	}
	
	@Override
	public void close() throws IOException {
		inputStream.close();
	}

	public static void main(String[] args)
	{
		String logFile="G:\\mysql-bin.008009";
		if(args.length>0)
		{
			logFile=args[0];
		}
		File file=new File(logFile);
		try {
			BinaryLogFileReader reader=new 	BinaryLogFileReader( file);
			reader.registerEventListener(new DefaultListener());			
			Event event=reader.readEvent();
			while(event!=null)
			{				
				event=reader.readEvent();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block 1 4224 9400 9000
			//1 4056 5125 7000
			e.printStackTrace();
		}
	}

}
