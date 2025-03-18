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
	    	
	        loginRegis(); //Simulates login/registration page
	        
	    }
	    
	    
	    //Login/Register Function
	    public static void loginRegis() {
	    	String value = " ";
	    	clearScreen(); 
	    	Scanner scanner = new Scanner(System.in);
	        Logins_Registrationmethods login = new Logins_Registrationmethods();
	        displayLMS(); 
	        Patron patron = login.Logins(null, null);
	        
	        if(patron != null && patron.getUsername().equals("admin")) {
	        	adminSection(scanner);
	        }else if(patron != null && !patron.getUsername().equals("admin")) {
	        	patronSection(scanner,patron);
	        }else if(patron == null) {
	        	System.out.println("\nPress a key to go back to menu....");
	    		while(value.equals(" ")) {
	    			value = scanner.next();
	    		}
	    		
	    		loginRegis();
	        }
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
	            System.out.println("9. Return to Login Page");
	            System.out.print("Choose an option: ");
	            
	            choice = scanner.nextInt();
	            scanner.nextLine();

	           
	            switch (choice) 
	            {
	            case 1:
	            	bookManagement.addBook(scanner);//Add Book
	            	System.out.println("\nPress a key to go to menu....");
	        		while(value.equals(" ")) {
	        			value = scanner.next();
	        		}
	        		adminSection(scanner); 
	        		
	            break;
	            
	            case 2:
	            	bookManagement.displaySortedBooks(); //Display Sorted Books
	            	System.out.println("\nPress a key to go to menu....");
	        		while(value.equals(" ")) {
	        			value = scanner.next();
	        		}
	        		adminSection(scanner); 
	            break;
	            
	            case 3:
	            	int totalBooks = bookManagement.totalbooks(); //Display Total books
	            	System.out.println("Total number of Books in the System is : " + totalBooks);
	            	System.out.println("\nPress a key to go to menu....");
	        		while(value.equals(" ")) {
	        			value = scanner.next();
	        		}
	        		adminSection(scanner); 
	            break;
	                  
	            case 4:
	              	patronList.createPatronMenu(patronList, scanner); //display patron management menu
	              	System.out.println("\nPress a key to go to menu....");
	        		while(value.equals(" ")) {
	        			value = scanner.next();
	        		}
	        		adminSection(scanner); 
	                break;
	            case 5:
	            	System.out.println("Total Patrons registered in the System is: " + patronList.CountNode());
	            	System.out.println("\nPress a key to go to menu....");// the above display total patron registered
	        		while(value.equals(" ")) {
	        			value = scanner.next();
	        		}
	        		adminSection(scanner); 
	            break;
	            
	            case 6:
	            	PatronQueue patronQ = new PatronQueue(); //Current Checkout
	            	patronQ.display();
	            	System.out.println("\nPress a key to go to menu....");
	        		while(value.equals(" ")) {
	        			value = scanner.next();
	        		}
	        		adminSection(scanner); 
	                break;
	           
	            case 7:          
	            	patronList.SearchForANode();//search patron menu
	            	System.out.println("\nPress a key to go to menu....");
	        		while(value.equals(" ")) {
	        			value = scanner.next();
	        		}
	        		adminSection(scanner); 
	                break;
	                
	            case 8:
	            	patronList.DeleteANode();//remove patron
	            	System.out.println("\nPress a key to go to menu....");
	        		while(value.equals(" ")) {
	        			value = scanner.next();
	        		}
	        		adminSection(scanner); 
	                break;
	                
	            case 9:
	            	System.out.println("Returning to Login Page...");
	            	loginRegis(); 
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
		private static void patronSection(Scanner scanner, Patron patron)
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
	              System.out.println("6. Return to Login Page");
	              
	              System.out.println("Choose an option: ");
	              
	              choice = scanner.nextInt();
	              scanner.nextLine();
	              
	              //Object Instances
	              BookLinkedList bookLinked = new  BookLinkedList(); 
	              BookManagement bookManagement1 = new BookManagement();
	              PatronQueue patronQ = new PatronQueue(); 
	              BookBST booktree = new BookBST();
	              
	              switch(choice) 
	              {
	              	case 1: 
	              		 System.out.print("\n\nEnter book's title: "); //search by book name
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
	             		 patronSection(scanner,patron); 
	              		break;
	              	
	              	case 2:
	              		System.out.print("\nEnter author's name: "); // search by book author
	                     String author = scanner.nextLine();
	                     bookManagement1.searchByAuthor(author);
	                     System.out.println("\nPress a key to go to menu....");
	             		 while(value.equals(" ")) {
	             			value = scanner.next();
	             		 }
	             		 patronSection(scanner,patron); 
	              		break;
	              	
	              	case 3:
	              		 System.out.print("\nEnter the book's ISBN: "); //search by book ISBN
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
	              		  patronSection(scanner,patron); 
	              		break;
	              	
	              	case 4:
	              		String opt; 
	              		BookStack bookS = new BookStack(); //Checkout section
	              		if(patronQ.search(patron.getCardNumber()))
	              		{//checks if patron has books to return 
	              			System.out.println("\n\nPatron has books to return, can't check out anymore books!");
	              			//Call menu function
	              		}else 
	              		 {
	              			System.out.println("Enter title of book you want to checkout: ");
	              			String tempTitle= scanner.nextLine().trim();
	              			Book bookC = new Book();
	              			bookC =  bookManagement1.searchByTitle(tempTitle);
	              		 if(bookC == null) {
	              				System.out.println("\nNo book of that title is available in the library");
	              				
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
	                      			tempTitle= scanner.nextLine().trim();
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
	            		  patronSection(scanner,patron); 
	              		break;
	              
	              	    case 5:
	              		String opt1; 
	              		if(patronQ.search(patron.getCardNumber())) {//checks if patron has books to return 
	              			Patron tempPatron = patronQ.peek();
	              			if(tempPatron.getCardNumber() == patron.getCardNumber()) {
	              				if(tempPatron.getBooksCheckedOut().CountNode()  == 1) {
	              					tempPatron.getBooksCheckedOut().changeAllAvailability();
	              					patronQ.dequeue();
	              					System.out.println("\nBook with title ' "+tempPatron.getBooksCheckedOut().getHead().getData().getTitle()+" ' returned successfully!"); 
	              					System.out.println("\nPress a key to go to menu....");
	          						while(value.equals(" ")) {
	          							value = scanner.next();
	          						}
	          						patronSection(scanner,patron); 
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
	              						patronSection(scanner,patron); 
	                  					break;
	                  				}else if (opt1.equalsIgnoreCase("no")) {
	                  					System.out.println("\nThese are the books you have checkedout");
	                  					tempPatron.getBooksCheckedOut().DisplayList();
	                  					System.out.println("\nEnter title of book you want to return");
	                  					opt1 = scanner.next().trim();
	                  					if(tempPatron.getBooksCheckedOut().SearchForANode(opt1)) {
	                  						tempPatron.getBooksCheckedOut().DeleteANode1(opt1);
	                  						patronQ.addFront(tempPatron); 
	                  						System.out.println("\nBook with title ' "+opt1+" ' returned successfully!"); 
	                  						System.out.println("\nPress a key to go to menu....");
	                  						while(value.equals(" ")) {
	                  							value = scanner.next();
	                  						}
	                  						patronSection(scanner,patron); 
	                  						break;
	                  					}else {
	                  						System.out.println("\nYou have not checked out a book with this title!");
	                  						System.out.println("\nPress a key to go to menu....");
		                              		 while(value.equals(" ")) {
		                              			value = scanner.next();
		                              		  }
		                              		  patronSection(scanner,patron); 
	                  						break;
	                  					}
	                  					
	                  				}
	              				}	
	              			}
	              			else 
	              			{ 
	              				System.out.println("\n\nPatron can't return book(s) until Patron with cardnumber - "+tempPatron.getCardNumber()+" return all books");
	              				while(value.equals(" ")) {
                          			value = scanner.next();
                          		  }
                          		  patronSection(scanner,patron); 
	              			}
	              		}
	              		else 
	              		{
	              			System.out.println("\n\nPatron doesn't have books to return.");
	              			//Call menu function
	              		}
	              		break;
	              	case 6:
	              		System.out.println("Returning to Login Page...");
	                	loginRegis(); 
	                    return; 
	              	default:
	              		System.out.println("Invalid input selected");
	              		System.out.println("\nPress a key to go to menu....");
	            		  while(value.equals(" ")) {
	            			value = scanner.next();
	            		  }
	            		  patronSection(scanner,patron); 
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

	    //Method to exit the System
	    private static boolean confirmExit(Scanner scanner) 
	    {
	        System.out.print("Are you sure you want to exit? (yes/no): ");
	        String response = scanner.next().trim().toLowerCase();
	        return response.equals("yes") || response.equals("y");
	    }
	}
