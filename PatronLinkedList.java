/*
Names: 
Jade Freeman: 2300078
Shavon Gordon: 2306989
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
*/

package librarySystemsProject;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;



public class PatronLinkedList implements Serializable{
	private static final long serialVersionUID = 1L;
    private PatronNode head;
    
    // Default Constructor
    public PatronLinkedList() {
    	try {
			File file = new File("Patrons.txt");
			if(file.isFile()) {
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
				head = (PatronNode)input.readObject(); 
				input.close();
			}else {
				head = null; 
			}
		}catch(IOException | ClassNotFoundException e){
			System.out.println("Exception: " + e.toString());
			
		}
    }

    // Primary Constructor
    public PatronLinkedList(PatronNode head) {
        this.head = head;
    }

    // Getters and Setters
    public PatronNode getHead() {
        return head;
    }

    public void setHead(PatronNode head) {
        this.head = head;
    }
    
 // InsertAtBack Method
    public void InsertAtBack(Patron dataToInsert) {
        PatronNode temp1;
        PatronNode temp2;

        temp1 = new PatronNode();

        if (temp1 != null) {
            temp1.setData(dataToInsert); // setting the data of the new node.
            temp1.setNextNode(null); // set the link portion of the node to point to null.

            if (head == null) { // if the list is empty.
                head = temp1; // setting head to point to the new node to be added to the list
            } else { // if the list is not empty
                temp2 = head; // initialize temp node to point to the first element in the list

                while (temp2.getNextNode() != null) { // while we are not at the last node in the list
                    temp2 = temp2.getNextNode(); // we are moving the current node temp2 to its next Node.
                }
                temp2.setNextNode(temp1); // set the link portion of the last node to point to the next node we are trying to add to the list
            }
            try {
    			File file = new File("Patrons.txt");
    			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
    			output.writeObject(head);
                output.close(); 
    		
    		}
            catch(IOException e){
    			System.out.println("Exception: " + e.toString());	
    		}
        } else { // if memory was not allocated successfully
            System.err.println("Error. List is full, it can not add a new node.");
        }
    }

    public int CountNode() {
        int count = 0; // initialize the counter
        PatronNode curr = head; // initialize curr to point to the first element of the list
        while (curr != null) { // while curr is pointing to a valid node
            count++;
            curr = curr.getNextNode(); // points curr to its next node
        }
        return count;
    }

    

    // Display list Method
    public void DisplayList() {
        PatronNode curr = head; // point curr to the first element in the list

        while (curr != null) { // while the list is not empty.
            System.out.println("Patron Information is : " + curr.getData().toString());

            curr = curr.getNextNode(); // point curr to the next node in the list
        }
        System.out.println("End of list.");
    } 

    public boolean IsEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }

    public boolean IsFull() {
        PatronNode temp = new PatronNode(); // attempt to reserve space for a new node by calling default constructor
        if (temp != null) {
            temp = null;
            return false;
        }
        return true;
    }
      
    public Patron SearchForANode(String username) {
        PatronNode curr = head;
        while (curr != null) {
            Patron patron = curr.getData();
            if (patron != null && patron.getUsername() != null && patron.getUsername().equalsIgnoreCase(username)) {
                return patron;
            }
            curr = curr.getNextNode();
        }
        return null; // Return null if no match is found
    }
   
  //Clear screen function
  	public static void clearScreen() {
  		for(double i = 1; i <= 50; i = i + .01) {
  			System.out.println();
  		}
  		
  	}
  	
  	//display screen
  	public static void displayLMS() {
 	     
  	    System.out.println( "                                       --------------------------------------------------------  ");
  	    System.out.println( "                                       ***               ***** **      ** *****      *****                 ");
  	    System.out.println( "                                       ***               *****   **   **  *****     **       ");
  	    System.out.println( "                                       ***               *****    ** **   *****       ***       ");
  	    System.out.println( "                                       ***               *****            *****         *** ");
  	    System.out.println( "                                       ***               *****            *****           **");
  	    System.out.println( "                                       *************     *****            *****          **");
  	    System.out.println( "                                       *************     *****            *****     ****");
  	    System.out.println( "                                       -------------------------------------------------------- ");
  	    System.out.println( "                                                    ----Library Management System---- ");
  	    System.out.println();
  	         
  	}

    public void createPatronMenu(PatronLinkedList patronList, Scanner scanner) {
    	clearScreen();
    	displayLMS();
    	
        String value = " ";
        
    	int choice;
    	do {
            System.out.println("\nPatron Management Menu:");
            System.out.println("1. Add a new patron");
            System.out.println("2. Display all patrons");
            System.out.println("3. Return to Admin Menu");
            System.out.print("Choose an option: ");

            choice = getValidInput(scanner);
            scanner.nextLine();

            switch (choice) {
                case 1:
                	Logins_Registrationmethods register = new Logins_Registrationmethods();
                	register.registerUser(scanner);
                	System.out.println("\nPress a key to go to menu....");
            		while(value.equals(" ")) {
            			value = scanner.next();
            		}
            		PatronLinkedList patronL = new PatronLinkedList(); 
            		createPatronMenu(patronL, scanner);
                    break;
                case 2:
                    patronList.DisplayList();
                    System.out.println("\nPress a key to go to menu....");
            		while(value.equals(" ")) {
            			value = scanner.next();
            		}
            		createPatronMenu(patronList, scanner); 
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }while(choice != 1 && choice != 2 && choice != 3 );
    }
  

    //VConsole Delete Node
    public Patron DeleteANode() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter patron card number to remove: ");
        
        String cardNumberStr = scanner.nextLine();
        return DeleteANode(cardNumberStr);
        
    }
    
    //GUI delete a Node
    public Patron DeleteANode(String cardNumberStr) {
        if (IsEmpty()) {
            System.err.println("Error: No patrons to delete.");
            return null;
        }

        int cardNumber;
        try {
            cardNumber = Integer.parseInt(cardNumberStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid card number format.");
            return null;
        }

        PatronNode curr = head, prev = null;
        while (curr != null) {
            if (curr.getData().getCardNumber() == cardNumber) {
                if (curr == head) {
                    head = head.getNextNode();
                } else {
                    prev.setNextNode(curr.getNextNode());
                }
                Patron removedPatron = curr.getData();
                curr = null;
                saveToFile();
                return removedPatron;
            }
            prev = curr;
            curr = curr.getNextNode();
        }
        return null;
      }

    //Both Console and Gui
    	public Patron SearchForANode() {
    	    if (IsEmpty()) {
    	        System.err.println("Error: No patrons in the system.");
    	        return null;
    	    }

    	    Scanner scanner = new Scanner(System.in);
    	    System.out.print("Enter patron username to search: ");
    	    String username = scanner.nextLine().trim();

    	    PatronNode curr = head;
    	    while (curr != null) {
    	        Patron patron = curr.getData(); // Get the patron object

    	        if (patron != null && patron.getUsername() != null && patron.getUsername().equalsIgnoreCase(username)) {
    	        	 System.out.println("Patron with username: " + username + " found.");
    	            return patron; // Return the found patron
    	        }

    	        curr = curr.getNextNode();
    	    }

    	    System.out.println("Patron with username '" + username + "' not found.");
    	    return null;
    	}
    
    //Both Console and GUI file
    private void saveToFile() {
        try {
            File file = new File("Patrons.txt");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(head);
            output.close();
        } catch (IOException e) {
            System.out.println("Exception: " + e.toString());
        }
    }

    //BOth Console and GUI
    public void addPatron(Scanner scanner) {
        System.out.print("Enter patron name: ");
        String username = scanner.nextLine();

        int cardNumber;
        do {
            cardNumber = generateUniqueCardNumber();
        } while (SearchForANode(username) != null);

        Patron newPatron = new Patron(username, cardNumber);
        InsertAtBack(newPatron);

        System.out.println("Patron added successfully: " + newPatron.username);
    }
    
    public int generateUniqueCardNumber() {
        Random rand = new Random();
        return 10000 + rand.nextInt(90000); // Generates a 5-digit card number
    }
    
    private int getValidInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next(); // Consume the invalid input
        }
        return scanner.nextInt();
    }
  
    // Console 
    private Patron searchPatron(String patronId)
    {
        try {
            int cardNumber = Integer.parseInt(patronId);
            return searchPatronByCardNumber(cardNumber);
        } catch (NumberFormatException e) {
            return null; // Invalid ID format
        }
    }
 // GUI Support: Search for Patron by ID as String
    public String searchForPatronString(String patronId) {
        StringBuilder sb = new StringBuilder();
        try {
            int cardNumber = Integer.parseInt(patronId);
            Patron patron = searchPatronByCardNumber(cardNumber);
            if (patron != null) {
                sb.append("Patron found:\n").append(patron.toString());
            } else {
                sb.append("No patron found with ID: ").append(patronId);
            }
        } catch (NumberFormatException e) {
            sb.append("Invalid card number format: ").append(patronId);
        }
        return sb.toString();
    }

    // Helper method to search patron by card number
    private Patron searchPatronByCardNumber(int cardNumber) {
        PatronNode curr = head;
        while (curr != null) {
            Patron patron = curr.getData();
            if (patron != null && patron.getCardNumber() == cardNumber) {
                return patron;
            }
            curr = curr.getNextNode();
        }
        return null;
    }

}
