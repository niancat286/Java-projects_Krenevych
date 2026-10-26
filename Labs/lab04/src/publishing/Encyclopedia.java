package publishing;

public class Encyclopedia extends  Book{

    private int volumesCount;

    public Encyclopedia(String title, String author, int year, int pages, int volumesCount) {
        super(title, author, year, pages);
        this.volumesCount = volumesCount;
    }

    public int getVolumesCount() {
        return volumesCount;
    }

    @Override
    public void printDetails() {
        System.out.print("[Енциклопедія] ");
        super.printDetails();
        System.out.println("  Кількість томів: " + volumesCount);
    }

}
