package chatroom;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import edu.illinois.imunit.IMUnitRunner;
import edu.illinois.imunit.Schedule;

@RunWith(IMUnitRunner.class)
public class DriverTest {

	private final class TestThread extends Thread {
		private final ChatRoom room;
		private boolean succcessful;

		public TestThread(ChatRoom room) {
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
	@Schedule("b@T1->a@T2,b@T2->a@T1")
	public void testJoinRoom() throws InterruptedException {
		ChatRoom room = new ChatRoom();
		TestThread t1 = new TestThread(room);
		TestThread t2 = new TestThread(room);
		t1.setName("T1");
		t2.setName("T2");
		t1.start();
		System.out.println("bla");
		t2.start();
		t1.join();
		t2.join();
		Assert.assertFalse("Two successful joins. There should be only one.",
				t1.isSucccessful() && t2.isSucccessful());
	}
}
