package chatroom;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import edu.illinois.imunit.IMUnitRunner;
import edu.illinois.imunit.Schedule;

@RunWith(IMUnitRunner.class)
public class ChatRoomIMUnit {

	private final class TestThread extends Thread {
		private final ChatRoom room;
		private boolean succcessful;

		public TestThread(ChatRoom room, String name) {
			super(name);
			this.room = room;
		}

		@Override
		public void run() {
			try {
				succcessful = room.joinRoom("joe", "Joe", "somepass");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public boolean isSucccessful() {
			return succcessful;
		}
	}

	@Test
	@Schedule("afterCheck@t1->beforePut@t2, afterCheck@t2->beforePut@t1")
	public void testJoinRoom() throws InterruptedException {
		ChatRoom room = new ChatRoom();
		TestThread t1 = new TestThread(room, "t1");
		TestThread t2 = new TestThread(room, "t2");
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		Assert.assertFalse("Two successful joins. There should be only one.", t1.isSucccessful() && t2.isSucccessful());
	}
}
