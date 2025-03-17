/*
Names: 
Jade Freeman: 2300078
Shavon Gordon: 2306989
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
*/
package librarySystemsProject;

public class BookStack {
	private BookStackElement top;
	
	public BookStack() {
		this.top = null;
	}
	
	public BookStackElement getTop() {
		return top;
	}

	public void setTop(BookStackElement top) {
		this.top = top;
	}

	public void push(Book book) {//Pushes book to stack for waiting checkout 
		BookStackElement newElement = new BookStackElement(book);
		newElement.setPreviousElement(top);
		top = newElement; 
	}
	
	public Book pop() {//Removes book from checkout
		
		if(isEmpty()) {
			System.out.println("Stack is Empty");
		}
		
		Book value = top.getBook();//if stack isn't empty get latest book in checkout
		top = top.getPreviousElement();
		return value;
		
	}
	
	public Book stackTop() {//Returns latest book in checkout waiting 
		if(isEmpty()) {
			System.out.println("Stack is Empty");
		}
		
		return top.getBook(); 
	}
	
	public int countStack() {//Count elements in stack
		int count = 0;
		if(isEmpty()) {
			System.out.println("Stack is Empty");
		}else {
			BookStackElement temp = top;
			while(temp != null) {
				count++; 
				temp = temp.getPreviousElement(); 
			}
		}
		
		return count;
	}
	
	//Displays content of stack
	public void displayStack() {
		if(isEmpty()) {
			System.out.println("Stack is Empty");
		}else {
			BookStackElement temp = top;
			while(temp != null) {
				System.out.println(temp.getBook().toString2());
				temp = temp.getPreviousElement(); 
			}
		}
	}
	
	//Change availability of books in stack
	public void changeAvailabilty() {
		if(!isEmpty()) {
			BookLinkedList list = new BookLinkedList();
			BookStackElement temp = top;
			while(temp != null) {
				list.ChangeToCheckedOut(temp.getBook().getISBN());
				temp = temp.getPreviousElement(); 
			}
		}
	}
	public boolean isEmpty() {
		return top==null; 
	}

	//GUI
	public String displayStackString() {
	    StringBuilder sb = new StringBuilder();
	    if (isEmpty()) {
	        sb.append("Stack is Empty");
	    } else {
	        BookStackElement temp = top;
	        while (temp != null) {
	            sb.append(temp.getBook().toString2()).append("\n");
	            temp = temp.getPreviousElement();
	        }
	    }
	    return sb.toString();
	}
}
