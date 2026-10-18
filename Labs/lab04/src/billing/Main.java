package billing;

public class Main {
    public static void main(String[] args) {
        Payment payment = new Payment("Ярослава");

        payment.addItem("Ноутбук", 28500.0, 1);
        payment.addItem("Миша", 650.0, 2);
        payment.addItem("Килимок", 250.0, 1);
        payment.addItem("Кабель", 180.0, 3);
        payment.addItem("Флешка", 320.0, 2);
        payment.addItem("Екофлоу", 40000.0, 2);


        payment.printReceipt();
    }
}
