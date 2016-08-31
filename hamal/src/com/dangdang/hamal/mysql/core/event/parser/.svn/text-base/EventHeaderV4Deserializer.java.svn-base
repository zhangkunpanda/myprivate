/*
 *  
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
package com.dangdang.hamal.mysql.core.event.parser;

import com.dangdang.hamal.io.ByteArrayInputStream;
import com.dangdang.hamal.mysql.core.event.EventHeaderV4;
import com.dangdang.hamal.mysql.core.event.EventType;

import java.io.IOException;

/**
 * @author  
 */
public class EventHeaderV4Deserializer implements EventHeaderDeserializer<EventHeaderV4> {

	private static final EventType[] EVENT_TYPES = EventType.values();

	@Override
	public EventHeaderV4 deserialize(ByteArrayInputStream inputStream) {
		EventHeaderV4 header = new EventHeaderV4();
		try {

			header.setTimestamp( inputStream.readLong(4) * 1000L);
			header.setEventType(getEventType(inputStream.readInteger(1)));
			header.setServerId(inputStream.readLong(4));
			header.setEventLength(inputStream.readInteger(4));
			header.setNextPosition(inputStream.readLong(4));
			header.setFlags(inputStream.readInteger(2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return header;
	}

	private static EventType getEventType(int ordinal) throws IOException {
		if (ordinal >= EVENT_TYPES.length) {
			throw new IOException("Unknown event type " + ordinal);
		}
		return EVENT_TYPES[ordinal];
	}

}
