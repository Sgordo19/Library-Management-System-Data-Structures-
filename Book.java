/*
Names: 
Jade Freeman: 2300078
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
Shavon Gordon: 2306989

*/

package librarySystemsProject;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable;

    // Default Constructor
    public Book() {
        title = "It Ends with Us";
        author = "Colleen Hoover"; // Trimmed whitespace
        ISBN = "9781501110368";
        isAvailable = true;
    }

    // Primary Constructor
    public Book(String title, String author, String ISBN, boolean isAvailable) {
        this.title = title != null ? title.trim() : "";
        this.author = author != null ? author.trim() : "";
        this.ISBN = ISBN != null ? ISBN.trim() : "";
        this.isAvailable = isAvailable;
    }

    // Copy Constructor
    public Book(Book b) {
        this.title = b.title;
        this.author = b.author;
        this.ISBN = b.ISBN;
        this.isAvailable = b.isAvailable;
    }

    // Accessors
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Mutators
    public void setTitle(String title) {
        this.title = title != null ? title.trim() : "";
    }

    public void setAuthor(String author) {
        this.author = author != null ? author.trim() : "";
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN != null ? ISBN.trim() : "";
    }

    public void setIsAvailable(boolean available) {
        this.isAvailable = available;
    }

    // To String
    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + ISBN + ") - " +
               (isAvailable ? "Available" : "Checked Out");
    }

    public String toString2() {
        return title + " by " + author + " (ISBN: " + ISBN + ")";
    }
}