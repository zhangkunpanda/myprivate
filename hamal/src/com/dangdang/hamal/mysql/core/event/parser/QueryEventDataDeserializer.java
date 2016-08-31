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
import com.dangdang.hamal.mysql.core.event.QueryEventData;

import java.io.IOException;

/**
 * @author  
 */
public class QueryEventDataDeserializer implements EventDataDeserializer<QueryEventData> {

	/**
	 *   slave_proxy_id= thread_id = uint4korr(buf + Q_THREAD_ID_OFFSET);
	 *   exec_time = uint4korr(buf + Q_EXEC_TIME_OFFSET);
	 *   db_len = (uint)buf[Q_DB_LEN_OFFSET]; // TODO: add a check of all *_len vars
	 *   error_code = uint2korr(buf + Q_ERR_CODE_OFFSET);
	 *   start[data_len]= '\0'; // End query with \0 (For safetly)
	 *   db= (char *)start;
	 *   query= (char *)(start + db_len + 1);
	 */
	@Override
	public QueryEventData deserialize(ByteArrayInputStream inputStream) throws IOException {
		QueryEventData eventData = new QueryEventData();
		eventData.setThreadId(inputStream.readLong(4));
		eventData.setExecutionTime(inputStream.readLong(4));
		inputStream.skip(1); // length of the name of the database
		eventData.setErrorCode(inputStream.readInteger(2));
		inputStream.skip(inputStream.readInteger(2)); // status variables block
		eventData.setDatabase(inputStream.readZeroTerminatedString());   
		
		eventData.setSql(inputStream.readString(inputStream.available()));
//		byte[] buf=inputStream.read(inputStream.available());
//		byte[] newbuf=new byte[buf.length-4];
//		System.arraycopy(buf, 0, newbuf, 0, newbuf.length);
//		eventData.setSql(new String(newbuf));
		return eventData;
	}
}
