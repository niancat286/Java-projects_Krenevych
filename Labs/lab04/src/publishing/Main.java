package publishing;

public class Main {

    public static void main(String[] args) {
        Edition[] editions = {
                new ReferenceBook(
                        "Java: Довідник розробника",
                        "Бенджамін Еванс",
                        2021,
                        800,
                        "Комп'ютерні науки"
                ),
                new Encyclopedia(
                        "Британська енциклопедія",
                        "Колектив авторів",
                        2012,
                        3200,
                        32
                ),
                new ReferenceBook(
                        "Довідник інженера-будівельника",
                        "Іван Коваленко",
                        2018,
                        650,
                        "Будівництво"
                )
        };

        System.out.println("Каталог видань");
        for (Edition edition : editions) {
            edition.printDetails();
            System.out.println();
        }
    }

}
