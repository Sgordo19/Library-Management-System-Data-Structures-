package librarySystemsProject;

public class BookStackElement {
	private Book book;
	private BookStackElement previousElement;
	
	public BookStackElement(Book book) {
		this.book = book;
		this.previousElement = null;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BookStackElement getPreviousElement() {
		return previousElement;
	}

	public void setPreviousElement(BookStackElement previousElement) {
		this.previousElement = previousElement;
	}
	
	

}