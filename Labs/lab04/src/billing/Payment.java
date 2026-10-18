package billing;

public class Payment {
    private String customerName;
    private Item[] items;
    private int itemCount;

    public class Item {
        private String productName;
        private double price;
        private int quantity;

        public Item(String productName, double price, int quantity) {
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }

        public double getTotalPrice() {
            return price * quantity;
        }

        public String getProductName() {
            return productName;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        @Override
        public String toString() {
            return String.format("%-20s | %8.2f грн | %4d шт. | %9.2f грн",
                    productName, price, quantity, getTotalPrice());
        }
    }

    public Payment(String customerName) {
        this.customerName = customerName;
        this.items = new Item[4]; // початкова місткість масиву
        this.itemCount = 0;
    }

    private void resizeArray() {
        Item[] newItems = new Item[items.length * 2];
        System.arraycopy(items, 0, newItems, 0, items.length);
        items = newItems;
    }

    public void addItem(String productName, double price, int quantity) {
        if (itemCount == items.length) {
            resizeArray();
        }
        items[itemCount] = new Item(productName, price, quantity);
        itemCount++;
    }

    public double calcTotal() {
        double total = 0.0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getTotalPrice();
        }
        return total;
    }

    public void printReceipt() {
        System.out.println("ЧЕК ОПЛАТИ");
        System.out.println("Клієнт: " + customerName);
        System.out.println();
        System.out.printf("%-20s | %10s | %6s | %11s\n", "Товар", "Ціна", "К-сть", "Сума");
        System.out.println();

        for (int i = 0; i < itemCount; i++) {
            System.out.println(items[i]);
        }

        System.out.println();
        System.out.printf("РАЗОМ ДО СПЛАТИ: %26.2f грн\n", calcTotal());
        System.out.println();
    }


}
