package chatroom;

import chatroom.solution.ChatRoomSync;

public class DriverJPF {
	static class TestThread extends Thread {
		private final ChatRoom room;
		boolean success;

		public TestThread(ChatRoom room) {
			this.room = room;
		}

		@Override
		public void run() {
			success = room.joinRoom("joe", "Joe", "badpass");
		}
	}

	public static void main(String[] args) throws Exception {
		ChatRoom room = new chatroom.ChatRoom();
		TestThread t1 = new TestThread(room);
		TestThread t2 = new TestThread(room);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		assert(!(t1.success && t2.success));
	}
}