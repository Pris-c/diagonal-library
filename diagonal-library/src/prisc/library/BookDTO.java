package prisc.library;

// TODO: Rename Class
public class BookDTO {

    private int id;
    private String title;
    private String author;
    private int year;


    public BookDTO(String title, String author, int year) {
        this.id = 0;
        this.title = title;
        this.author = author;
        this.year = year;
    }public BookDTO(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }





    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }



    @Override
    public String toString() {
        return " Book " + id + ", Title: " + title + ", Author: " + author + ", Year: " + year;
    }



}
