package com.dangdang.hamal.event.filter;

import com.dangdang.hamal.mysql.core.event.Event;
import com.dangdang.hamal.mysql.core.event.EventType;

public class EventTypeFilter implements EventFilter{

	@Override
	public boolean accept(Event event) {
		EventType eventType=event.getHeader().getEventType();
		if(eventType==EventType.QUERY||eventType==EventType.WRITE_ROWS
				||eventType==EventType.WRITE_ROWS||eventType==EventType.UPDATE_ROWS
				)
			return true;
		return false;
	}

	@Override
	public void initParams(String[] params) {
		// TODO Auto-generated method stub
		
	}

}
