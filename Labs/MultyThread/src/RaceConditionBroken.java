public class RaceConditionBroken {

    static class Account {
        int balance = 0;

        public void deposit(int amount) {
            balance += amount;
        }

        public boolean canWithdraw(int amount) {
            return balance >= amount;
        }

        public void withdraw(int amount, String threadName) {

            if (!canWithdraw(amount)) {
                System.out.println(threadName + " withdraw try, not enough");
                return;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(threadName + " withdraws " + amount + " uah...");
            balance -= amount;
            System.out.println("balance: " + balance + " uah\n");
        }

        public int getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) {
        Account sharedAccount = new Account();
        sharedAccount.deposit(10);
        System.out.println("initial balance: " + sharedAccount.getBalance() + " uah\n");

        Runnable atmTask = () -> {
            String threadName = Thread.currentThread().getName();
            sharedAccount.withdraw(10, threadName);
        };

        Thread husbandThread = new Thread(atmTask, "husband");
        Thread wifeThread = new Thread(atmTask, "wife");

        husbandThread.start();
        wifeThread.start();
    }
}
