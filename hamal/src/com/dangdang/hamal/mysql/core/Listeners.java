package com.dangdang.hamal.mysql.core;

import com.dangdang.hamal.conf.TopologyConf.ListenerParam;
import com.dangdang.hamal.mysql.core.event.Event;

public class Listeners {

	public static interface EventListener {
		String getName();
		void onStart(ListenerParam param);
		void onEvent(Event event);
	}
	
	/**
	 * {@link BinaryLogClient}'s lifecycle listener.
	 */
	public static interface LifecycleListener {

		/**
		 * Called once client has successfully logged in but before started to receive binlog events.
		 */
		void onConnect(BinaryLogClient client);

		/**
		 * It's guarantied to be called before {@link #onDisconnect(BinaryLogClient)}) in case of
		 * communication failure.
		 */
		void onCommunicationFailure(BinaryLogClient client, Exception ex);

		/**
		 * Called in case of failed event deserialization. Note this type of error does NOT cause client to
		 * disconnect. If you wish to stop receiving events you'll need to fire client.disconnect() manually.
		 */
		void onEventDeserializationFailure(BinaryLogClient client, Exception ex);

		/**
		 * Called upon disconnect (regardless of the reason).
		 */
		void onDisconnect(BinaryLogClient client);
	}
	
	/**
	 * Default (no-op) implementation of {@link LifecycleListener}.
	 */
	public  static abstract class AbstractLifecycleListener implements LifecycleListener {

		public void onConnect(BinaryLogClient client) {
		}

		public void onCommunicationFailure(BinaryLogClient client, Exception ex) {
		}

		public void onEventDeserializationFailure(BinaryLogClient client, Exception ex) {
		}

		public void onDisconnect(BinaryLogClient client) {
		}

	}
	
	
}

