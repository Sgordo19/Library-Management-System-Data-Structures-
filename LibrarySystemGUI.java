/*
Names: 
Jade Freeman: 2300078
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
Shavon Gordon: 2306989

*/
package librarySystemsProject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class LibrarySystemGUI extends JFrame {
    private Logins_Registrationmethods login; //Instance of Prior classes
    private BookManagement bookManagement;
    private PatronLinkedList patronList;
    private PatronQueue patronQ;
    private BookStack bookStack;
    private Patron currentPatron;
    private Dimension defaultSize = new Dimension(1000, 600);
    private Dimension defaultLoginSize = new Dimension(500, 400);
    private Dimension defaultAdminSize = new Dimension(600, 700);
    private Dimension defaultPatronSize = new Dimension(600, 500);
    private Dimension defaultPatronMgmtSize = new Dimension(500, 400);
    

    public LibrarySystemGUI() {
        login = new Logins_Registrationmethods();//Object Instantiations
        bookManagement = new BookManagement();
        patronList = new PatronLinkedList();
        patronQ = new PatronQueue();
        bookStack = new BookStack();

        setTitle("Library Management System"); //Title below logo display
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600); 
        setLocationRelativeTo(null);

        if (!showLoginRegistrationDialog()) {
            System.exit(0);
        }

        currentPatron = Logins_Registrationmethods.patron;
        if (currentPatron != null) {
            if ("admin".equalsIgnoreCase(currentPatron.getUsername())) {
                showAdminMenu();
            } else {
                showPatronMenu();
            }
        }
    }
    //Screen methods
    private void toggleFullScreen(JDialog dialog, JButton toggleButton, Dimension defaultSize) {
        if (dialog.getSize().equals(Toolkit.getDefaultToolkit().getScreenSize())) {
            dialog.setSize(defaultSize);
            dialog.setLocationRelativeTo(null);
            toggleButton.setText("Full Screen");
        } else {
            dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            dialog.setLocation(0, 0);
            toggleButton.setText("Restore");
        }
        dialog.revalidate();
        dialog.repaint();
    }
    
    //Display logo
    private String getLMSHeader() {
        int totalWidth = 80;
        String[] lines = {
            "--------------------------------------------------------",
            "***             ***** **      ** *****      *****",
            "***             *****   **   **  *****     **   ",
            "***             *****    ** **   *****       ***",
            "***             *****            *****         ***",
            "***             *****            *****           **",
            "*************   *****            *****          **",
            "*************   *****            *****     **** ",
            "--------------------------------------------------------",
            "----Library Management System----"
        };
        StringBuilder centeredHeader = new StringBuilder();
        for (String line : lines) {
            int lineLength = line.length();
            int padding = (totalWidth - lineLength) / 2;
            if (padding < 0) padding = 0;
            for (int i = 0; i < padding; i++) {
                centeredHeader.append(" ");
            }
            centeredHeader.append(line).append("\n");
        }
        centeredHeader.append("\n");
        return centeredHeader.toString();
    }

    //Login/ Registration Window
    private boolean showLoginRegistrationDialog() {
        JDialog dialog = new JDialog(this, "Login / Register", true);
        dialog.setSize(defaultLoginSize);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setResizable(true);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextArea headerArea = new JTextArea(getLMSHeader());
        headerArea.setEditable(false);
        headerArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        headerArea.setBackground(contentPanel.getBackground());

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");

        gbc.gridx = 0; gbc.gridy = 1; contentPanel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; contentPanel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; contentPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; contentPanel.add(passField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; contentPanel.add(new JLabel(""), gbc);
        gbc.gridx = 1; gbc.gridy = 3; contentPanel.add(loginButton, gbc);
        gbc.gridx = 0; gbc.gridy = 4; contentPanel.add(new JLabel(""), gbc);
        gbc.gridx = 1; gbc.gridy = 4; contentPanel.add(registerButton, gbc);
        gbc.gridx = 0; gbc.gridy = 5; contentPanel.add(new JLabel(""), gbc);
        gbc.gridx = 1; gbc.gridy = 5; contentPanel.add(exitButton, gbc);

        dialog.add(headerArea, BorderLayout.CENTER);
        dialog.add(contentPanel, BorderLayout.SOUTH);

        final boolean[] success = {false};

        loginButton.addActionListener(_ -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (login.login(username, password)) {
                Patron loggedInPatron = patronList.SearchForANode(username);//Check to see if patron was removed from system
                if (loggedInPatron == null && !"admin".equalsIgnoreCase(username)) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Error: Your account has been removed from the system.", 
                        "Login Error", 
                        JOptionPane.ERROR_MESSAGE);
                    userField.setText("");
                    passField.setText("");
                } else if (login.requiresFirstLoginChange(username, password)) {
                    if (changePasswordDialog(username, password)) {
                        success[0] = true;
                        dialog.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Login successful!");
                    // Offer optional password change after login
                    int choice = JOptionPane.showConfirmDialog(dialog, 
                        "Would you like to change your password?", 
                        "Password Change", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        if (changePasswordDialog(username, password)) {
                            JOptionPane.showMessageDialog(dialog, "Password changed successfully!");
                        }
                    }
                    success[0] = true;
                    dialog.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Incorrect username or password.");
                userField.setText("");
                passField.setText("");
            }
        });

        registerButton.addActionListener(_ -> {
            String username = userField.getText().trim();
            String result = login.register(username);
            JOptionPane.showMessageDialog(dialog, result);
            if (result.startsWith("User registered successfully")) {
                // Add the new patron to patronList after successful registration
                Patron newPatron = new Patron(username, Logins_Registrationmethods.patron.getCardNumber());
                patronList.InsertAtBack(newPatron); // Ensure patronList is updated
                userField.setText("");
                passField.setText("");
            }
        });

        exitButton.addActionListener(_ -> System.exit(0));

        dialog.setVisible(true);
        return success[0];
    }

    //Change Password window
    private boolean changePasswordDialog(String username, String oldPassword) {
        JDialog changeDialog = new JDialog(this, "Change Password", true);
        changeDialog.setSize(500, 300);
        changeDialog.setLocationRelativeTo(null);
        changeDialog.setLayout(new GridLayout(3, 2, 10, 10));
        changeDialog.setResizable(true);

        JLabel newPassLabel = new JLabel("New Password:"); //Output Statement
        JPasswordField newPassField = new JPasswordField(); //Input Statement
        JButton changeButton = new JButton("Change");// Change Button
        JButton cancelButton = new JButton("Cancel");// Cancel Button

        changeDialog.add(new JLabel("Password must be 8+ chars with capital, numbers, symbols"));
        changeDialog.add(new JLabel(""));
        changeDialog.add(newPassLabel); //Dialog slot created for Output statement
        changeDialog.add(newPassField);//Dialog slot created for Input statement
        changeDialog.add(changeButton); // Dialog slot created for Change Button
        changeDialog.add(cancelButton);  // Dialog slot created for Change Button

        final boolean[] changed = {false};

        changeButton.addActionListener(_ -> {
            String newPassword = new String(newPassField.getPassword()).trim();
            String result = login.changePassword(username, oldPassword, newPassword, false); // false since not first login
            JOptionPane.showMessageDialog(changeDialog, result);
            if (result.equals("Password changed successfully!")) {
                changed[0] = true;
                changeDialog.dispose();
            } else {
                newPassField.setText("");
            }
        });

        cancelButton.addActionListener(_ -> changeDialog.dispose());

        changeDialog.setVisible(true);
        return changed[0];
    }

    private void showAdminMenu() {
        JDialog adminDialog = new JDialog(this, "Admin Menu", true);
        adminDialog.setSize(defaultAdminSize); // Use default size (600x700)
        adminDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea headerArea = new JTextArea(getLMSHeader());
        headerArea.setEditable(false);
        headerArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        headerArea.setBackground(panel.getBackground());
        headerArea.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the text area horizontally
        headerArea.setLineWrap(false); // Prevent wrapping to maintain fixed width

        // Wrap headerArea in a panel to control its alignment
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(headerArea);
        panel.add(headerPanel, BorderLayout.CENTER); // Place header in CENTER for vertical flexibility

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 9, 9));//increase gap
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton fullScreenButton = new JButton("Full Screen");
        fullScreenButton.setFont(new Font("SansSerif", Font.PLAIN, 16)); //make button size bigger
        topPanel.add(fullScreenButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH); // Buttons at the bottom

        String[] options = {
            "Add a book", "Display all books", "View total books",
            "Patron Management", "View total patrons", "View current checkout",
            "Search patron", "Remove patron", "Return to login page"
        };

        for (int i = 0; i < options.length; i++) {
            JButton button = new JButton(options[i]);
            final int choice = i + 1;
            button.addActionListener(e -> handleAdminAction(choice, adminDialog));
            buttonPanel.add(button);
        }

        fullScreenButton.addActionListener(e -> toggleFullScreen(adminDialog, fullScreenButton, defaultAdminSize));

        adminDialog.add(panel);
        adminDialog.setVisible(true);
    }

    //Admin Actions Handler
    private void handleAdminAction(int choice, JDialog dialog) {
        switch (choice) {
            case 1: // Add a book
                String title = JOptionPane.showInputDialog("Enter book title:");
                String author = JOptionPane.showInputDialog("Enter author:");
                String isbn = JOptionPane.showInputDialog("Enter ISBN:");
                String availableStr = JOptionPane.showInputDialog("Is the book available? (true/false):");
                if (title != null && author != null && isbn != null && availableStr != null) {
                    try {
                        boolean available = Boolean.parseBoolean(availableStr.trim());
                        bookManagement.addBook(title, author, isbn, available);
                        JOptionPane.showMessageDialog(this, "Book added successfully!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Invalid availability input! Use 'true' or 'false'.");
                    }
                }
                dialog.dispose();
                showAdminMenu();
                break;
            case 2: // Display all books
                JTextArea textArea = new JTextArea(15, 40);
                textArea.setText(bookManagement.displaySortedBooksString());
                JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "All Books", JOptionPane.PLAIN_MESSAGE);
                dialog.dispose();
                showAdminMenu();
                break;
            case 3: // View total books
                JOptionPane.showMessageDialog(this, "Total books: " + bookManagement.totalbooks());
                dialog.dispose();
                showAdminMenu();
                break;
            case 4: // Patron Management
                showPatronManagementMenu(dialog);
                break;
            case 5: // View total patrons
                JOptionPane.showMessageDialog(this, "Total patrons: " + Logins_Registrationmethods.patroncount);
                dialog.dispose();
                showAdminMenu();
                break;
            case 6: // View current checkout
                JTextArea checkoutArea = new JTextArea(15, 40);
                checkoutArea.setText(patronQ.displayString());
                JOptionPane.showMessageDialog(this, new JScrollPane(checkoutArea), "Current Checkout", JOptionPane.PLAIN_MESSAGE);
                dialog.dispose();
                showAdminMenu();
                break;
            case 7: // Search patron
                String username = JOptionPane.showInputDialog("Enter patron username:");
                if (username != null) {
                    Patron found = patronList.SearchForANode(username);
                    JOptionPane.showMessageDialog(this, found != null ? found.toString() : "Patron not found!");
                }
                dialog.dispose();
                showAdminMenu();
                break;
            case 8: // Remove patron
                dialog.dispose();
                String cardNumberStr = JOptionPane.showInputDialog(this, "Enter the card number of the patron to remove");
                if (cardNumberStr != null && !cardNumberStr.trim().isEmpty())
                	
                  {
                        Patron removed = patronList.DeleteANode(cardNumberStr); 
                        JOptionPane.showMessageDialog(this, removed != null ? "Patron removed!" : "Patron not found!");
                    } else if ( cardNumberStr != null) {
                    	JOptionPane.showMessageDialog(this, "Card number cannot be empty!");
                    }
                
                // Simulate "Press a key to go to menu..." with a simple OK dialog
                JOptionPane.showMessageDialog(this, "Press any key to return to the menu.", "Info", JOptionPane.INFORMATION_MESSAGE);
                showAdminMenu(); // Return to admin menu
                break;

            case 9: // Return to login page
                dialog.dispose();
                new LibrarySystemGUI();
                dispose();
                break;
        }
    }

    //Patron Management method in the admin Menu
    private void showPatronManagementMenu(JDialog parentDialog) {
        JDialog patronMgmtDialog = new JDialog(this, "Patron Management", true);
        patronMgmtDialog.setSize(500, 400); // Enlarged from 400x250
        patronMgmtDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] options = {"Add a new patron", "Display all patrons", "Return to admin menu"};
        for (int i = 0; i < options.length; i++) {
            JButton button = new JButton(options[i]);
            final int choice = i + 1;
            button.addActionListener(e -> handlePatronManagementAction(choice, patronMgmtDialog, parentDialog));
            panel.add(button);
        }

        patronMgmtDialog.add(panel);
        patronMgmtDialog.setVisible(true);
    }

    //Admin Management Method for adding and displaying patron from the admin menu
    private void handlePatronManagementAction(int choice, JDialog dialog, JDialog parentDialog) {
        switch (choice) {
            case 1: // Add a new patron
                String username = JOptionPane.showInputDialog("Enter a unique username:");
                if (username != null && !username.trim().isEmpty()) {
                    String result = login.register(username);
                    JOptionPane.showMessageDialog(this, result);
                }
                dialog.dispose();
                showPatronManagementMenu(parentDialog);
                break;
            case 2: // Display all patrons
                JTextArea patronsArea = new JTextArea(15, 40);
                StringBuilder sb = new StringBuilder();
                PatronNode curr = patronList.getHead();
                while (curr != null) {
                    sb.append(curr.getData().toString()).append("\n");
                    curr = curr.getNextNode();
                }
                patronsArea.setText(sb.length() > 0 ? sb.toString() : "No patrons in the system.");
                JOptionPane.showMessageDialog(this, new JScrollPane(patronsArea), "All Patrons", JOptionPane.PLAIN_MESSAGE);
                dialog.dispose();
                showPatronManagementMenu(parentDialog);
                break;
            case 3: // Return to admin menu
                dialog.dispose();
                parentDialog.dispose();
                showAdminMenu();
                break;
        }
    }

    //Patron Menu Method
    private void showPatronMenu() {
        JDialog patronDialog = new JDialog(this, "Patron Menu", true);//Create title for window
        patronDialog.setSize(defaultPatronSize); 
        patronDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout());
        
        JTextArea headerArea = new JTextArea(getLMSHeader());
        headerArea.setEditable(false);
        headerArea.setFont(new Font("Monospaced", Font.PLAIN, 16));//LMS HEADER SIZE
        headerArea.setBackground(panel.getBackground());
        headerArea.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the text area horizontally
        headerArea.setLineWrap(false); // Prevent wrapping to maintain fixed width
        headerArea.setRows(12); // Ensure all lines are visible
        headerArea.setColumns(80);
        
        // Wrap headerArea in a panel to control its alignment
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(headerArea);
        panel.add(headerPanel, BorderLayout.CENTER); // Place header in CENTER for vertical flexibility

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 9, 9));//increase gap
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton fullScreenButton = new JButton("Full Screen");
        fullScreenButton.setFont(new Font("SansSerif", Font.PLAIN, 16)); //make button size bigger
        topPanel.add(fullScreenButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH); // Buttons at the bottom

        String[] options = {
            "Search by title", "Search by author", "Search by ISBN",
            "Checkout books", "Return books", "Return to login page"
        };

        //button creation based on the option
        for (int i = 0; i < options.length; i++) {
            JButton button = new JButton(options[i]); // Create a button with the text from options[i]
            final int choice = i + 1;
            button.addActionListener(e -> handlePatronAction(choice, patronDialog));
            buttonPanel.add(button);
        }

        fullScreenButton.addActionListener(e -> toggleFullScreen(patronDialog, fullScreenButton, defaultPatronSize));
        
        //create and display dialog window
        patronDialog.add(panel);
        patronDialog.setVisible(true);
    }

    //Method to navigate Patron menu
    private void handlePatronAction(int choice, JDialog dialog) {
        switch (choice) {
            case 1: // Search by title
                String title = JOptionPane.showInputDialog("Enter book title:");
                if (title != null) {
                    Book book = bookManagement.searchByTitle(title);
                    JOptionPane.showMessageDialog(this, book != null ? book.toString() : "No book with that title found!");
                }
                dialog.dispose();
                showPatronMenu();
                break;
            case 2: // Search by author
                String author = JOptionPane.showInputDialog("Enter author:");
                if (author != null) {
                    JTextArea textArea = new JTextArea(15, 40);
                    textArea.setText(bookManagement.searchByAuthorString(author));
                    JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Books by Author", JOptionPane.PLAIN_MESSAGE);
                }
                dialog.dispose();
                showPatronMenu();
                break;
            case 3: // Search by ISBN
                String isbn = JOptionPane.showInputDialog("Enter ISBN:");
                if (isbn != null) {
                    Book book = bookManagement.searchByISBN(isbn);
                    JOptionPane.showMessageDialog(this, book != null ? book.toString() : "No book with that ISBN found!");
                }
                dialog.dispose();
                showPatronMenu();
                break;
            case 4: // Checkout books
                if (patronQ.search(currentPatron.getCardNumber())) {
                    JOptionPane.showMessageDialog(this, "You have books to return; cannot checkout more!");
                } else {
                    checkoutBooks();
                }
                dialog.dispose();
                showPatronMenu();
                break;
            case 5: // Return books
                returnBooks();
                dialog.dispose();
                showPatronMenu();
                break;
            case 6: // Return to login page
                dialog.dispose();
                new LibrarySystemGUI();
                dispose();
                break;
        }
    }

    //GUI checkoutBooks Methods
    private void checkoutBooks() {
        BookStack tempStack = new BookStack();
        while (true) {
            String title = JOptionPane.showInputDialog("Enter title of book to checkout (or Cancel to finish):");
            if (title == null) break;
            Book book = bookManagement.searchByTitle(title);
            if (book == null) {
                JOptionPane.showMessageDialog(this, "No book with that title found!");
            } else if (!book.isAvailable()) {
                JOptionPane.showMessageDialog(this, "Book is currently checked out!");
            } else {
                tempStack.push(book);
            }

            if (tempStack.countStack() > 0) {
                int undo = JOptionPane.showConfirmDialog(this, "Undo this checkout?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (undo == JOptionPane.YES_OPTION) {
                    tempStack.pop();
                }
            }

            int more = JOptionPane.showConfirmDialog(this, "Checkout another book?", "Continue", JOptionPane.YES_NO_OPTION);
            if (more != JOptionPane.YES_OPTION) break;
        }

        if (tempStack.countStack() > 0) {
            tempStack.changeAvailabilty();
            currentPatron.getBooksCheckedOut().insertStack(tempStack);
            patronQ.enqueue(currentPatron);
            JTextArea checkoutArea = new JTextArea(15, 40);
            checkoutArea.setText(tempStack.displayStackString());
            JOptionPane.showMessageDialog(this, new JScrollPane(checkoutArea), "Books Checked Out", JOptionPane.PLAIN_MESSAGE);
        }
    }

    //GUI return Books Methods
    private void returnBooks() {
        if (!patronQ.search(currentPatron.getCardNumber())) {
            JOptionPane.showMessageDialog(this, "You don't have any books to return!");
            return;
        }

        Patron queueHead = patronQ.peek();
        if (queueHead.getCardNumber() != currentPatron.getCardNumber()) {
            JOptionPane.showMessageDialog(this, "Cannot return books until patron with card number " + 
                queueHead.getCardNumber() + " returns all books!");
            return;
        }

        int count = currentPatron.getBooksCheckedOut().CountNode();
        if (count == 1) {
            currentPatron.getBooksCheckedOut().changeAllAvailability();
            patronQ.dequeue();
            JOptionPane.showMessageDialog(this, "Book returned successfully!");
        } else if (count > 1) {
            int returnAll = JOptionPane.showConfirmDialog(this, "Return all books?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (returnAll == JOptionPane.YES_OPTION) {
                currentPatron.getBooksCheckedOut().changeAllAvailability();
                patronQ.dequeue();
                JOptionPane.showMessageDialog(this, "Books returned successfully!");
            } else {
                JTextArea booksArea = new JTextArea(15, 40);
                booksArea.setText(currentPatron.getBooksCheckedOut().displayListString());
                JOptionPane.showMessageDialog(this, new JScrollPane(booksArea), "Your Checked Out Books", JOptionPane.PLAIN_MESSAGE);
                String returnTitle = JOptionPane.showInputDialog("Enter title of book to return:");
                if (returnTitle != null && currentPatron.getBooksCheckedOut().SearchForANode(returnTitle)) {
                    currentPatron.getBooksCheckedOut().DeleteANode1(returnTitle);
                    patronQ.addFront(currentPatron);
                    JOptionPane.showMessageDialog(this, "Book returned successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "You have not checked out a book with this title!");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySystemGUI());
    }
}
