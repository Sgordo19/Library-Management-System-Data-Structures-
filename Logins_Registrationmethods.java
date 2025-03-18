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
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Logins_Registrationmethods {
    public static int patroncount = 0; // Keep track of registered users
    public static Patron patron; 
    public Logins_Registrationmethods() {
        // Constructor can remain empty or initialize something if needed
    }

    // Main login method for console 
    public Patron Logins(String username, String password) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                	patron = null;
                    loggedIn = loginUser(scanner);
                    return patron;
                case 3:
                    System.out.println("Exiting...");
                    return null;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        return null;
    }

    // GUI-friendly login method
    public boolean login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        if (username.equals("admin")) {
        	patron = new Patron("admin",0); 
            return true;
        }

        Password user = Password.getUserFromFile(username, password);
        PatronLinkedList patronLink = new PatronLinkedList(); 
        patron = patronLink.SearchForANode(username);
        
        if (user != null) {
            return true; // Login successful
        }
        return false; // Login failed
    }

    // GUI-friendly registration method
    public String register(String username) {
    	PatronLinkedList patron = new PatronLinkedList();
    	
          
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty!";
        }

        if (username.equals("admin")) {
            return "Admin registration is not allowed!";
        }
        
        if(patron.SearchForANode(username) != null) {
        	return "Username already exits, choose a different username!";
        }

        String generatedPassword = Password.generateDefaultPassword();
        Password newUser = new Password(username, generatedPassword);
        newUser.savePasswordToFile();

        patroncount++;
        return "User registered successfully. Your default password is: " + generatedPassword;
    }

    // GUI-friendly password change method
    public String changePassword(String username, String oldPassword, String newPassword, boolean isFirstLogin) {
        Password user = Password.getUserFromFile(username, oldPassword);
        if (user == null) {
            return "Invalid current credentials!";
        }

        if (!user.isValidPassword(newPassword)) {
            return "Password must be 8+ characters with at least 1 capital letter, numbers, and special symbols!";
        }

        user.changePassword(newPassword);
        if (isFirstLogin) {
            user.setFirstLogin(false);
        }
        return "Password changed successfully!";
    }

    // Check if user exists and needs first login password change
    public boolean requiresFirstLoginChange(String username, String password) {
        Password user = Password.getUserFromFile(username, password);
        return user != null && user.isFirstLogin();
    }

  //Register user method with parameter scanner
    public void registerUser(java.util.Scanner scanner) {
        int error; 
        String username;
        PatronLinkedList patron = new PatronLinkedList();
    	do {
    		error = 0;
    		System.out.print("Enter a unique Username: ");
            username = scanner.nextLine();
            
            if(patron.SearchForANode(username) != null) {
            	error = 1;
            	System.out.println("\nUsername already exits, choose a different username....\n");
            }
        }while(error !=  0);
    	

        if (username.equals("admin")) {
            System.out.println("Admin registration is not allowed!");
            return;
        }

        String generatedPassword = Password.generateDefaultPassword();
        System.out.println("Generated default password: " + generatedPassword);

        Password newUser = new Password(username, generatedPassword);
        newUser.savePasswordToFile();

        patroncount++;
        patron.InsertAtBack(new Patron(username));
        System.out.println("User registered successfully.");
    }

    //login user method with parameter scanner
    private boolean loginUser(java.util.Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (username.equals("admin")) {
        	patron = new Patron("admin",0); 
        	return true;
        }
        File file = new File("RemovedPatrons.txt");
        if(file.isFile()) {
        	String temp; 
        	try {
				Scanner scannerR  = new Scanner(file);
				while(scannerR.hasNext()) {
					temp = scannerR.next();
					if(username.equals(username)) {
						System.out.println("Patron has been removed from Library System.");
						return false;
					}
				}
				scannerR.close();
			} catch (FileNotFoundException e) {
				System.err.println("Error, information could not be retrieved.");
				e.printStackTrace();
			}
        }
        Password user = Password.getUserFromFile(username, password);
        if (user != null) {
            System.out.println("Login successful!");
            if (user.isFirstLogin()) {
                changePasswordOnFirstLogin(user, scanner);
            } else {
                offerPasswordChange(user, scanner);
            }
            PatronLinkedList patronLink = new PatronLinkedList(); 
            patron = patronLink.SearchForANode(username);
            return true;
        } else {
            System.out.println("Incorrect username or password.");
            return false;
        }
    }

    // Change Password on First login Method
    private void changePasswordOnFirstLogin(Password user, java.util.Scanner scanner) {
        System.out.println("\nWe would now like you to change your password!");
        System.out.println("Password must be 8 characters long and contain at least (1) Capital letter, common letters, numbers, and special symbols.");
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();

        while (!user.isValidPassword(newPassword)) {
            System.out.println("Password must be at least 8 characters long and contain letters and numbers.");
            System.out.print("Enter new password: ");
            newPassword = scanner.nextLine();
        }

        user.changePassword(newPassword);
        user.setFirstLogin(false);
    }

    // Offer Password Change method
    private void offerPasswordChange(Password user, java.util.Scanner scanner) {
        System.out.println("\nWould you like to change your password?");
        System.out.println("1. Yes\n2. No");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.println("Password must be 8 characters long and contain at least (1) Capital letter, common letters, numbers, and special symbols.");
            System.out.print("Enter your new password: ");
            String newPassword = scanner.nextLine();
            user.changePassword(newPassword);
        }
    }  
}



        
