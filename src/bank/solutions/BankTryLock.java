package bank.solutions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class BankTryLock {

	public static class Account {
		public final ReentrantLock lock = new ReentrantLock();
		private long balance;

		public Account(long balance) {
			this.balance = balance;
		}

		void withdraw(long amount) {
			this.balance -= amount;
		}

		void deposit(long amount) {
			this.balance += amount;
		}

		public long getBalance() {
			return this.balance;
		}
	}

	static class CrazyTeller extends Thread {
		private final Account from;
		private final Account to;

		public CrazyTeller(Account from, Account to) {
			this.from = from;
			this.to = to;
		}

		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				long amount;
				try {
					amount = nap();
					while (true) {
						boolean l1 = to.lock.tryLock(10, TimeUnit.MILLISECONDS);
						if (l1) {
							boolean l2 = from.lock.tryLock(10,
									TimeUnit.MILLISECONDS);
							if (l2) {
								from.withdraw(amount);
								to.deposit(amount);
								to.lock.unlock();
								from.lock.unlock();
								break;
							} else
								to.lock.unlock();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					to.lock.unlock();
					from.lock.unlock();
				}
			}
		}

		long nap() throws InterruptedException {
			Thread.sleep((long) (Math.random() * 2));
			return (long) (Math.random() * 100);
		}
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {
		long balanceA = 100000;
		long balanceB = 10000;
		Account a = new Account(balanceA);
		Account b = new Account(balanceB);
		CrazyTeller x = new CrazyTeller(a, b);
		CrazyTeller y = new CrazyTeller(b, a);
		x.start();
		y.start();
		x.join();
		y.join();
		System.out.println("Account a:" + a.getBalance());
		System.out.println("Account b:" + b.getBalance());
		System.out.println("Checksum: "
				+ (balanceA + balanceB - a.getBalance() - b.getBalance()));
	}
}
