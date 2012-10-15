package chatroom;

import java.util.concurrent.CyclicBarrier;

import junit.framework.Assert;

import org.junit.Test;

public class DriverTestSolution {

	private final class TestThread extends Thread {
		private final CyclicBarrier barrier;
		private final ChatRoom room;
		private int noJoins = 0;

		public TestThread(CyclicBarrier barrier, ChatRoom room) {
			this.barrier = barrier;
			this.room = room;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 100; i++) {
					barrier.await();
					Thread.sleep((long) (Math.random() * 10));
					if(room.joinRoom("joe" + i, "Joe" + i, "somepass"))
						noJoins++;
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testJoinRoom() throws InterruptedException {
		CyclicBarrier barrier = new CyclicBarrier(2);
		ChatRoom room = new ChatRoom();
		TestThread t1 = new TestThread(barrier, room);
		TestThread t2 = new TestThread(barrier, room);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		Assert.assertEquals(100, t1.noJoins+t2.noJoins);
	}
}
