package publishing;

public abstract class Book implements Edition{
    private String title;
    private String author;
    private int year;
    private int pages;

    public Book(String title, String author, int year, int pages){
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getYear() {
        return year;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public void printDetails() {
        System.out.println("Назва: \"" + title + "\", Автор: " + author
                + ", Рік: " + year + ", Сторінок: " + pages);
    }

}
