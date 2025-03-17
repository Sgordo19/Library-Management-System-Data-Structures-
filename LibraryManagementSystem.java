/*
Names: 
Jade Freeman: 2300078
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
Shavon Gordon: 2306989

*/

package librarySystemsProject;


import java.util.InputMismatchException;
import java.util.Scanner;




public class LibraryManagementSystem 
{

	
    public static void main(String[] args) 
    {
        
        Logins_Registrationmethods login = new Logins_Registrationmethods();
        displayLMS(); 
        login.Logins(null, null);
        displayMainMenu(); 
        
    }
    
  //Clear screen function
  	public static void clearScreen() {
  		for(double i = 1; i <= 50; i = i + .01) {
  			System.out.println();
  		}
  		
  	}
  	
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


    //Display Menu
    private static void displayMainMenu() 
    {
    	clearScreen(); 
    	displayLMS(); 
    	Scanner scanner = new Scanner(System.in);
    	int choice; 
    	
    	do {
    		System.out.println("\nLibrary System Main Menu:");
            System.out.println("1. Admin Section");
            System.out.println("2. Patron Section");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            choice = getValidInput(scanner);
            
            switch (choice) {
            case 1:
                adminSection(scanner);
                break;
            
            case 2:
    			patronSection(scanner);
                break;
            
            case 3:
                if (confirmExit(scanner)) 
                {
                    System.out.println("Exiting the library system. Goodbye!");
                    scanner.close();
                    clearScreen(); 
                    displayLMS();
                    return;
                }
                break;
            default:
                System.out.println("Invalid option! Please try again.");
            }
    	}while(choice != 1 && choice != 2 && choice != 3); 
        
    }

    //Admin Functionalities Section
    private static void adminSection(Scanner scanner) 
    {  
    	clearScreen(); 
    	displayLMS(); 
    	String value = " ";
    	BookManagement bookManagement = new BookManagement();
    	PatronLinkedList patronList = new PatronLinkedList();
       
    	int choice; 
    	
    	do 
        {
    		//Admin Menu
            System.out.println("\n Admin Menu");
            System.out.println("1. Add a book to the System ");
            System.out.println("2. Display all books (sorted by title)");
            System.out.println("3. View total Books in System");
            System.out.println("4. Patron Management");
            System.out.println("5. View Total Patrons");
            System.out.println("6. View Current Checkout");
            System.out.println("7. Search Patron");
            System.out.println("8. Remove Patron");
            System.out.println("9. Return to Main Menu");
            System.out.print("Choose an option: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();

           
            switch (choice) 
            {
            case 1:
            	bookManagement.addBook(scanner);
            	System.out.println("\nPress a key to go to menu....");
        		while(value.equals(" ")) {
        			value = scanner.next();
        		}
        		adminSection(scanner); 
        		
            break;
            
            case 2:
            	bookManagement.displaySortedBooks();
            	System.out.println("\nPress a key to go to menu....");
        		while(value.equals(" ")) {
        			value = scanner.next();
        		}
        		adminSection(scanner); 
            break;
            
            case 3:
            	int totalBooks = bookManagement.totalbooks();
            	System.out.println("Total number of Books in the System is : " + totalBooks);
            	System.out.println("\nPress a key to go to menu....");
        		while(value.equals(" ")) {
        			value = scanner.next();
        		}
        		adminSection(scanner); 
            break;
                  
            case 4:
              	patronList.createPatronMenu(patronList, scanner);
              	System.out.println("\nPress a key to go to menu....");
        		while(value.equals(" ")) {
        			value = scanner.next();
        		}
        		adminSection(scanner); 
                break;
            case 5:
            	System.out.println("Total Patrons registered in the System is: " + Logins_Registrationmethods.patroncount);
            	System.out.println("\nPress a key to go to menu....");
        		while(value.equals(" ")) {
        			value = scanner.next();
        		}
        		adminSection(scanner); 
            break;
            
            case 6:
            	PatronQueue patronQ = new PatronQueue();
            	patronQ.display();
            	System.out.println("\nPress a key to go to menu....");
        		while(value.equals(" ")) {
        			value = scanner.next();
        		}
        		adminSection(scanner); 
                break;
           
            case 7:          
            	patronList.SearchForANode();
            	System.out.println("\nPress a key to go to menu....");
        		while(value.equals(" ")) {
        			value = scanner.next();
        		}
        		adminSection(scanner); 
                break;
                
            case 8:
            	patronList.DeleteANode();
            	System.out.println("\nPress a key to go to menu....");
        		while(value.equals(" ")) {
        			value = scanner.next();
        		}
        		adminSection(scanner); 
                break;
                
            case 9:
            	System.out.println("Returning to Main Menu...");
            	displayMainMenu();
                break;
            default:
                System.out.println("Invalid option. Try again.");
                System.out.println("\nPress a key to go to menu....");
        		while(value.equals(" ")) {
        			value = scanner.next();
        		}
        		adminSection(scanner); 
                break;
            }              
        } while(choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6 && choice != 7 && choice != 8 && choice != 9); 
      }

    //Patron Functionalities Section
    @SuppressWarnings("unused")
	private static void patronSection(Scanner scanner)
    {
    	String value = " ";
    	clearScreen(); 
    	displayLMS(); 
    	int choice; 
         
        do
        {
         	//Patron Menu
         	  System.out.println("\n Patron Menu:");
              System.out.println("1. Search for Book by title");
              System.out.println("2. Search for Books by author");
              System.out.println("3. Search for Book by ISBN");
              System.out.println("4. Checkout Books");
              System.out.println("5. Return books");
              System.out.println("6. Exit");
              
              System.out.println("Choose an option: ");
              
              choice = scanner.nextInt();
              scanner.nextLine();
              
              BookLinkedList bookLinked = new  BookLinkedList();
              BookManagement bookManagement1 = new BookManagement();
              PatronQueue patronQ = new PatronQueue(); 
              Patron patron = new Patron();
              BookBST booktree = new BookBST();
              
              switch(choice) 
              {
              	case 1: 
              		 System.out.print("\n\nEnter book's title: ");
                     String title = scanner.nextLine();
                     Book book =  new Book(); 
                     book = bookManagement1.searchByTitle(title);
                     if(book != null) 
                     {
                     	System.out.println("\n"+book.toString());
                     }else 
                     {
                     	System.out.println("\nNo book of that title is available in the library");
                     }
                     System.out.println("\nPress a key to go to menu....");
             		 while(value.equals(" ")) {
             			value = scanner.next();
             		 }
             		 patronSection(scanner); 
              		break;
              	
              	case 2:
              		System.out.print("\nEnter author's name: ");
                     String author = scanner.nextLine();
                     bookManagement1.searchByAuthor(author);
                     System.out.println("\nPress a key to go to menu....");
             		 while(value.equals(" ")) {
             			value = scanner.next();
             		 }
             		 patronSection(scanner); 
              		break;
              	
              	case 3:
              		 System.out.print("\nEnter the book's ISBN: ");
                      String ISBN = scanner.nextLine();
                      Book book1 =  new Book(); 
                      book = bookManagement1.searchByISBN(ISBN); 
                      if(book1 != null) 
                      {
                      	System.out.println("\n"+book.toString());
                      }else 
                      {
                      	System.out.println("\nNo book of that ISBN is available in the library");
                      }
                      System.out.println("\nPress a key to go to menu....");
              		  while(value.equals(" ")) {
              			value = scanner.next();
              		  }
              		  patronSection(scanner); 
              		break;
              	
              	case 4:
              		String opt; 
              		BookStack bookS = new BookStack();
              		if(patronQ.search(patron.getCardNumber()))
              		{//checks if patron has books to return 
              			System.out.println("\n\nPatron has books to return, can't check out anymore books!");
              			//Call menu function
              		}else 
              		 {
              			System.out.println("Enter title of book you want to checkout: ");
              			String tempTitle= scanner.next().trim(); 
              			Book bookC = new Book();
              			bookC =  bookManagement1.searchByTitle(tempTitle);
              		 if(bookC == null) {
              				System.out.println("\nNo book of that title is available in the library");
              				//Call menu function
              			}else {
              				if(bookC.isAvailable()) {
              					bookS.push(bookC);
              				}else {
              					System.out.println("\nBook is not available,currently checked out"); 
              				}
              				
              				if(bookS.countStack() != 0) {
              					System.out.println("\nDo you want to undo this checkout?(yes/no)");
                  				opt = scanner.next().trim(); 
                  				if(opt.equalsIgnoreCase("yes")) {
                  					bookS.pop();
                  				}
              				}
              				
              				System.out.println("\nDo you want to checkout another book?(yes/no)");
              				opt = scanner.next().trim(); 
              				while(opt.equalsIgnoreCase("yes")) {
              					System.out.println("Enter title of book you want to checkout: ");
                      			tempTitle= scanner.next().trim(); 
                      			bookC =  bookManagement1.searchByTitle(tempTitle);
                      			if(bookC == null) {
                      				System.out.println("\nNo book of that title is available in the library");
                      			}else {
                      				if(bookC.isAvailable()) {
                      					bookS.push(bookC);
                      				}else {
                      					System.out.println("\nBook is not available,currently checkedout"); 
                      				}
                      				
                      				if(bookS.countStack() != 0) {
                      					System.out.println("\nDo you want to undo this checkout?(yes/no)");
                          				opt = scanner.next().trim(); 
                          				if(opt.equalsIgnoreCase("yes")) {
                          					bookS.pop();
                          				}
                      				}
                      				System.out.println("\nDo you want to checkout another book?(yes/no)");
                      				opt = scanner.next().trim(); 
                      			}
              				}
              				
              				if(bookS.countStack() != 0) {
              					System.out.println("\n--------------------------BOOKS CHECKEDOUT--------------------------");
              					bookS.displayStack();
              					System.out.println("\n--------------------------------------------------------------------");
              					
              					bookS.changeAvailabilty(); //Changes availability of checkout books

              					patron.getBooksCheckedOut().insertStack(bookS);
                 				patronQ.enqueue(patron);
              				}          				
              		     }
              		}
              		System.out.println("\nPress a key to go to menu....");
            		  while(value.equals(" ")) {
            			value = scanner.next();
            		  }
            		  patronSection(scanner); 
              		break;
              
              	    case 5:
              		String opt1; 
              		if(patronQ.search(patron.getCardNumber())) {//checks if patron has books to return 
              			Patron tempPatron = patronQ.peek();
              			if(tempPatron.getCardNumber() == patron.getCardNumber()) {
              				if(tempPatron.getBooksCheckedOut().CountNode()  == 1) {
              					tempPatron.getBooksCheckedOut().changeAllAvailability();
              					patronQ.dequeue();
              					System.out.println("\nBook returned sucessfully"); 
              					System.out.println("\nPress a key to go to menu....");
          						while(value.equals(" ")) {
          							value = scanner.next();
          						}
          						patronSection(scanner); 
              					break;
              					
              				}else if(tempPatron.getBooksCheckedOut().CountNode()  > 1) {
              					System.out.println("\n Do you want to return all books? (yes/no)");
                  				opt1 = scanner.next().trim();
                  				
                  				if(opt1.equalsIgnoreCase("yes")) {
                  					tempPatron.getBooksCheckedOut().changeAllAvailability();
                  					patronQ.dequeue();
                  					System.out.println("\nBooks returned sucessfully"); 
                  					System.out.println("\nPress a key to go to menu....");
              						while(value.equals(" ")) {
              							value = scanner.next();
              						}
              						patronSection(scanner); 
                  					break;
                  				}else if (opt1.equalsIgnoreCase("no")) {
                  					System.out.println("\nThese are the books you have checkedout");
                  					tempPatron.getBooksCheckedOut().DisplayList();
                  					System.out.println("\nEnter title of book you want to return");
                  					opt1 = scanner.next().trim();
                  					if(tempPatron.getBooksCheckedOut().SearchForANode(opt1)) {
                  						tempPatron.getBooksCheckedOut().DeleteANode1(opt1);
                  						patronQ.addFront(tempPatron); 
                  						System.out.println("\nBook returned sucessfully"); 
                  						System.out.println("\nPress a key to go to menu....");
                  						while(value.equals(" ")) {
                  							value = scanner.next();
                  						}
                  						patronSection(scanner); 
                  						break;
                  					}else {
                  						System.out.println("\nYou have not checked out a book with this title!");
                  						System.out.println("\nPress a key to go to menu....");
	                              		 while(value.equals(" ")) {
	                              			value = scanner.next();
	                              		  }
	                              		  patronSection(scanner); 
                  						break;
                  					}
                  					
                  				}
              				}	
              			}
              			else 
              			{ 
              				System.out.println("\n\nPatron can't return book(s) until Patron with cardnumber - "+tempPatron.getCardNumber()+" return all books");
              			}
              		}
              		else 
              		{
              			System.out.println("\n\nPatron doesn't have books to return.");
              			//Call menu function
              		}
              		break;
              	case 6:
              		System.out.println(" Exiting the Patron Menu. Goodbye!");
              		displayMainMenu();
                    return; 
              	default:
              		System.out.println("Invalid input selected");
              		System.out.println("\nPress a key to go to menu....");
            		  while(value.equals(" ")) {
            			value = scanner.next();
            		  }
            		  patronSection(scanner); 
              		return;    		
              }
    	 }while(choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6);
    }

    public static int getValidInput(Scanner scanner) 
    {
        while (true) 
        {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    private static boolean confirmExit(Scanner scanner) 
    {
        System.out.print("Are you sure you want to exit? (yes/no): ");
        String response = scanner.next().trim().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }
}