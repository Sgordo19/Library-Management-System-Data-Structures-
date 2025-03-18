/*
Names: 
Jade Freeman: 2300078
Shavon Gordon: 2306989
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
*/

package librarySystemsProject;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.Serializable;

public class Patron extends User implements Serializable{
	private static final long serialVersionUID = 1L;
    private int cardNumber;
    private CheckoutBookLinkedList booksCheckedOut; 
    //private BookNode head; // Linked list to track books

   public Patron(){
    	cardNumber = 0;
    	booksCheckedOut = new CheckoutBookLinkedList();
    }

public Patron(String username, int cardNumber) {
    super(username);
    this.cardNumber = cardNumber;
    booksCheckedOut = new CheckoutBookLinkedList();
}

public Patron(String username) {
	super(username);
	int number = 1; 
	
    File file = new File("Cardnumbers.txt");
    
    //Ensures unique cardnumber
    if(file.isFile()) {
    	int error; 
    	int temp;
    	do {
    		Random rand = new Random();
    		number =  10000 + rand.nextInt(90000); // Generates a 5-digit card number
    		error = 0; 
    		try {
				Scanner scanner  = new Scanner(file);
				while(scanner.hasNext()) {
					temp = scanner.nextInt();
					if(number == temp) {
						error++; 
						break;
					}
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				System.err.println("Error, information could not be retrieved.");
				e.printStackTrace();
			}
    		
    	}while(error > 0); 
    	
    	try {
			FileWriter inp = new FileWriter(file,true);
			inp.write(number + " ");
			inp.close(); 
		} catch (IOException e) {
			System.err.println("Error,  information could not be stored.");
			e.printStackTrace();
		}
    	
    }else {
    	try {
    		Random rand = new Random();
    		number =  10000 + rand.nextInt(90000); // Generates a 5-digit card number
			FileWriter inp = new FileWriter(file);
			inp.write(number + " ");
			inp.close(); 
		} catch (IOException e) {
			System.err.println("Error,  information could not be stored.");
			e.printStackTrace();
		}
    }
    
    this.cardNumber = number; 
    booksCheckedOut = new CheckoutBookLinkedList();
   
  }

    public Patron(Patron obj) 
    {
       super(obj);
       this.cardNumber = obj.cardNumber;
       this. booksCheckedOut = obj.booksCheckedOut; 
    }


    public CheckoutBookLinkedList getBooksCheckedOut()
    {
	   return booksCheckedOut; 
    }


    public int getCardNumber() 
    {
	  return cardNumber;
    }


    public void setCardNumber(int cardNumber) 
    {
	  this.cardNumber = cardNumber;
    }


    public void Display() 
    {
      System.out.println("Patron Information - Card Number: " + cardNumber + ", Name: " + getUsername());
      if (booksCheckedOut != null && !booksCheckedOut.IsEmpty()) {
    	  System.out.println("------------------------------------------------");
    	  System.out.println("\nBooks Checked Out:\n"); 
    	  booksCheckedOut.DisplayList();
    	  System.out.println("------------------------------------------------");
    } else 
       {
    	System.out.println("\nNo books checked out.");
       }
    
    }
	 @Override
    public String toString() {
        return "Patron Name: " + getUsername() + ", CardNumber: " + cardNumber;
    
}
}