public class RaceCondition {

    static class Account {
        int balance = 0;

        public synchronized void deposit(int amount) {
            balance += amount;
        }

        public synchronized boolean canWithdraw(int amount) {
            return balance >= amount;
        }

        public synchronized void withdraw(int amount, String threadName) {
            if (!canWithdraw(amount)) {
                System.out.println(threadName + " withdraw try, not enough");
                return;
            }

            System.out.println(threadName + " withdraw " + amount + " uah...");
            balance -= amount;
            System.out.println("balance: " + balance + " uah\n");
        }

        public synchronized int getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) {
        Account sharedAccount = new Account();
        sharedAccount.deposit(100);
        System.out.println("initial balance: " + sharedAccount.getBalance() + " uah\n");

        Runnable atmTask = () -> {
            String threadName = Thread.currentThread().getName();

            while (sharedAccount.getBalance() >= 10) {
                sharedAccount.withdraw(10, threadName);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread husbandThread = new Thread(atmTask, "husband");
        Thread wifeThread = new Thread(atmTask, "wife");

        husbandThread.start();
        wifeThread.start();
    }
}