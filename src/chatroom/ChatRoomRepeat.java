package chatroom;

import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;

import edu.illinois.imunit.IMUnit;
import edu.illinois.imunit.Schedule;

public class ChatRoomRepeat {
	static class TestThread extends Thread {
		private final ChatRoom room;
		long successes;

		public TestThread(ChatRoom room) {
			super("T1");
			this.room = room;
		}

		@Override
		public void run() {
			Random r = new Random();
			for(int i=0;i<1000;i++) {
				waitABit(r);
				if(room.joinRoom("joe"+i, "Joe", "badpass"))
					successes ++;
			}
		}

		private void waitABit(Random r) {
			try {
				Thread.sleep((int)(r.nextDouble()*3));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Test 
	public void testJoins() throws Exception {
		ChatRoom room = new ChatRoom();
		TestThread t1 = new TestThread(room);
		TestThread t2 = new TestThread(room);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		Assert.assertEquals(1000, t1.successes + t2.successes);
	}
}