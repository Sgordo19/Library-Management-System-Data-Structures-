/*
Names: 
Jade Freeman: 2300078
Shavon Gordon: 2306989
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
*/

package librarySystemsProject;

import java.io.Serializable;

public class BookNode implements Serializable {
	private static final long serialVersionUID = 1L;
	private Book data;
	private BookNode nextNode;
	
	
	// Default Constructor
	public BookNode() {
		data = new Book();
		nextNode = null;
	}
	
	//Primary Constructor #1
	public BookNode(Book data,BookNode nextNode) {
		this.data = data;
		this.nextNode = nextNode;
	}
	
	//Primary Constructor #2
	public BookNode(Book data) {
		this.data = data;
		this.nextNode = null;
	}
	
	//Primary Constructor #3
	public BookNode(String title, String author, String ISBN, boolean isAvailable) {
		this.data = new Book(title,author,ISBN,isAvailable); 
		this.nextNode = null;
	}
	
	//Copy Construction
	public BookNode(BookNode obj) {
			this.data = obj.data;
			this.nextNode = obj.nextNode;
	}
		
	public Book getData() {
		return data;
	}

	public void setData(Book data) {
		this.data = data;
	}

	public BookNode getNextNode() {
		return nextNode;
	}

	public void setNextNode(BookNode nextNode) {
		this.nextNode = nextNode;
	}

	
	
}

