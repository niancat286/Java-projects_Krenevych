package publishing;

public class ReferenceBook extends Book{
    private String domain;

    public ReferenceBook(String title, String author, int year, int pages, String domain) {
        super(title, author, year, pages);
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    @Override
    public void printDetails() {
        System.out.print("[Довідник] ");
        super.printDetails();
        System.out.println("  Галузь: " + domain);
    }
}
