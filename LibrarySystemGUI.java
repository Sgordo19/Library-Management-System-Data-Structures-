package librarySystemsProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class LibrarySystemGUI extends JFrame {
    private Logins_Registrationmethods login;
    private BookManagement bookManagement;
    private PatronLinkedList patronList;
    private PatronQueue patronQ;

    public LibrarySystemGUI() {
        login = new Logins_Registrationmethods();
        bookManagement = new BookManagement();
        patronList = new PatronLinkedList();
        patronQ = new PatronQueue();

        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Show login dialog before main GUI
        if (!showLoginRegistrationDialog()) {
            System.exit(0); // Exit if login/registration fails or is canceled
        }

        // Create main panel with LMS header
        JPanel mainPanel = new JPanel(new BorderLayout());
        JTextArea headerArea = new JTextArea(getLMSHeader());
        headerArea.setEditable(false);
        headerArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        headerArea.setColumns(80); // Match totalWidth for proper display
        mainPanel.add(headerArea, BorderLayout.NORTH);

        // Create menu buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton adminButton = new JButton("Admin Section");
        JButton patronButton = new JButton("Patron Section");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(adminButton);
        buttonPanel.add(patronButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Button actions
        adminButton.addActionListener(_ -> showAdminMenu());
        patronButton.addActionListener(_ -> showPatronMenu());
        exitButton.addActionListener(_ -> {
            if (JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", 
                "Confirm Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private String getLMSHeader() {
        int totalWidth = 80; // Fixed width for centering

        // Adjusted ASCII art with consistent spacing
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
            if (padding < 0) padding = 0; // No negative padding

            // Add left padding
            for (int i = 0; i < padding; i++) {
                centeredHeader.append(" ");
            }
            centeredHeader.append(line);

            // Ensure total width by adding right padding (optional for JTextArea)
            int remaining = totalWidth - (lineLength + padding);
            for (int i = 0; i < remaining; i++) {
                centeredHeader.append(" ");
            }
            centeredHeader.append("\n");
        }

        centeredHeader.append("\n"); // Extra newline
        return centeredHeader.toString();
    }
    private boolean showLoginRegistrationDialog() {
        JDialog dialog = new JDialog(this, "Login / Register", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setResizable(true);

        // Panel for login components
        JPanel contentPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");

        contentPanel.add(userLabel);
        contentPanel.add(userField);
        contentPanel.add(passLabel);
        contentPanel.add(passField);
        contentPanel.add(new JLabel("")); // Spacer
        contentPanel.add(loginButton);
        contentPanel.add(new JLabel("")); // Spacer
        contentPanel.add(registerButton);
        contentPanel.add(new JLabel("")); // Spacer
        contentPanel.add(exitButton);

        // Add a maximize button at the top
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton maximizeButton = new JButton("Maximize");
        topPanel.add(maximizeButton);

        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(contentPanel, BorderLayout.CENTER);

        final boolean[] success = {false};
        final String[] currentUsername = {""};
        final boolean[] isMaximized = {false}; // Track maximized state

        maximizeButton.addActionListener(_ -> {
            if (!isMaximized[0]) {
                dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Maximize to screen size
                dialog.setLocation(0, 0); // Move to top-left corner
                maximizeButton.setText("Restore");
                isMaximized[0] = true;
            } else {
                dialog.setSize(350, 250); // Restore to original size
                dialog.setLocationRelativeTo(null); // Center it again
                maximizeButton.setText("Maximize");
                isMaximized[0] = false;
            }
            dialog.revalidate(); // Ensure layout updates
            dialog.repaint();    // Refresh the dialog
        });

        loginButton.addActionListener(_ -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (login.login(username, password)) {
                currentUsername[0] = username;
                if (login.requiresFirstLoginChange(username, password)) {
                    if (changePasswordDialog(username, password)) {
                        success[0] = true;
                        dialog.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Login successful!");
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
                userField.setText("");
                passField.setText("");
            }
        });

        exitButton.addActionListener(_ -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
        return success[0];
    }

    private boolean changePasswordDialog(String username, String oldPassword) {
        JDialog changeDialog = new JDialog(this, "Change Password", true);
        changeDialog.setSize(350, 200);
        changeDialog.setLocationRelativeTo(null);
        changeDialog.setLayout(new GridLayout(3, 2, 10, 10));
        changeDialog.setResizable(true); // Allow resizing for consistency

        JLabel newPassLabel = new JLabel("New Password:");
        JPasswordField newPassField = new JPasswordField();
        JButton changeButton = new JButton("Change");
        JButton cancelButton = new JButton("Cancel");

        changeDialog.add(new JLabel("Password must be 8+ chars with capital, numbers, symbols"));
        changeDialog.add(new JLabel(""));
        changeDialog.add(newPassLabel);
        changeDialog.add(newPassField);
        changeDialog.add(changeButton);
        changeDialog.add(cancelButton);

        final boolean[] changed = {false};

        changeButton.addActionListener(_ -> {
            String newPassword = new String(newPassField.getPassword()).trim();
            String result = login.changePassword(username, oldPassword, newPassword, true);
            JOptionPane.showMessageDialog(changeDialog, result);
            if (result.equals("Password changed successfully!")) {
                changed[0] = true;
                changeDialog.dispose();
            } else {
                newPassField.setText("");
            }
        });

        cancelButton.addActionListener(_ -> {
            changeDialog.dispose();
        });

        changeDialog.setVisible(true);
        return changed[0];
    }

    private void showAdminMenu() {
        JDialog adminDialog = new JDialog(this, "Admin Menu", true);
        adminDialog.setSize(400, 500);
        adminDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(10, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] options = {
            "Add a book", "Display all books", "View total books",
            "Patron Management", "View total patrons", "View current checkout",
            "Search patron", "Remove patron", "Return to main menu"
        };

        for (int i = 0; i < options.length; i++) {
            JButton button = new JButton(options[i]);
            final int choice = i + 1;
            button.addActionListener(_ -> handleAdminAction(choice, adminDialog));
            panel.add(button);
        }

        adminDialog.add(panel);
        adminDialog.setVisible(true);
    }

    private void handleAdminAction(int choice, JDialog dialog) {
        switch (choice) {
        case 1: // Add book
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
            break;
            case 2: // Display books
                JTextArea textArea = new JTextArea(15, 40);
                textArea.setText(bookManagement.displaySortedBooksString());
                JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "All Books", JOptionPane.PLAIN_MESSAGE);
                break;
            case 3: // Total books
                JOptionPane.showMessageDialog(this, "Total books: " + bookManagement.totalbooks());
                break;
            case 4: // Patron management
                String patronName = JOptionPane.showInputDialog("Enter patron name:");
                if (patronName != null && !patronName.trim().isEmpty()) {
                    int cardNumber = patronList.generateUniqueCardNumber();
                    Patron newPatron = new Patron(patronName, cardNumber);
                    patronList.InsertAtBack(newPatron);
                    JOptionPane.showMessageDialog(this, "Patron added: " + newPatron.toString());
                }
                break;
            case 5: // Total patrons
                JOptionPane.showMessageDialog(this, "Total patrons: " + Logins_Registrationmethods.patroncount);
                break;
            case 6: // Current checkout
                JTextArea checkoutArea = new JTextArea(15, 40);
                checkoutArea.setText(patronQ.displayString());
                JOptionPane.showMessageDialog(this, new JScrollPane(checkoutArea), "Current Checkout", JOptionPane.PLAIN_MESSAGE);
                break;
            case 7: // Search patron
                String searchId = JOptionPane.showInputDialog("Enter patron card number:");
                if (searchId != null) {
                    String result = patronList.searchForPatronString(searchId);
                    JOptionPane.showMessageDialog(this, result);
                }
                break;
            case 8: // Remove patron
                String removeId = JOptionPane.showInputDialog("Enter patron card number to remove:");
                if (removeId != null) {
                    patronList.DeleteANode();
                    JOptionPane.showMessageDialog(this, "Patron removed if existed!");
                }
                break;
            case 9: // Return to main
                dialog.dispose();
                break;
        }
    }

    private void showPatronMenu() {
        JDialog patronDialog = new JDialog(this, "Patron Menu", true);
        patronDialog.setSize(400, 400);
        patronDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(7, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] options = {
            "Search by title", "Search by author", "Search by ISBN",
            "Checkout books", "Return books", "Exit"
        };

        for (int i = 0; i < options.length; i++) {
            JButton button = new JButton(options[i]);
            final int choice = i + 1;
            button.addActionListener(_ -> handlePatronAction(choice, patronDialog));
            panel.add(button);
        }

        patronDialog.add(panel);
        patronDialog.setVisible(true);
    }

    private void handlePatronAction(int choice, JDialog dialog) {
        BookManagement bookManagement1 = new BookManagement();
        switch (choice) {
            case 1: // Search by title
                String title = JOptionPane.showInputDialog("Enter book title:");
                if (title != null) {
                    Book book = bookManagement1.searchByTitle(title);
                    JOptionPane.showMessageDialog(this, book != null ? book.toString() : "Book not found!");
                }
                break;
            case 2: // Search by author
                String author = JOptionPane.showInputDialog("Enter author:");
                if (author != null) {
                    String result = bookManagement1.searchByAuthorString(author);
                    JOptionPane.showMessageDialog(this, result);
                }
                break;
            case 3: // Search by ISBN
                String isbn = JOptionPane.showInputDialog("Enter ISBN:");
                if (isbn != null) {
                    Book book1 = bookManagement1.searchByISBN(isbn);
                    JOptionPane.showMessageDialog(this, book1 != null ? book1.toString() : "Book not found!");
                }
                break;
            case 4: // Checkout books - Simplified
                String checkoutTitle = JOptionPane.showInputDialog("Enter book title to checkout:");
                if (checkoutTitle != null) {
                    Book bookC = bookManagement1.searchByTitle(checkoutTitle);
                    if (bookC != null && bookC.isAvailable()) {
                        bookC.setIsAvailable(false);
                        Patron currentPatron = new Patron("CurrentUser", patronList.generateUniqueCardNumber());
                        patronQ.enqueue(currentPatron);
                        JOptionPane.showMessageDialog(this, "Book checked out successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Book not available!");
                    }
                }
                break;
            case 5: // Return books - Simplified
                String returnTitle = JOptionPane.showInputDialog("Enter book title to return:");
                if (returnTitle != null) {
                    Book bookR = bookManagement1.searchByTitle(returnTitle);
                    if (bookR != null && !bookR.isAvailable()) {
                        bookR.setIsAvailable(true);
                        patronQ.dequeue();
                        JOptionPane.showMessageDialog(this, "Book returned successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Book not checked out or not found!");
                    }
                }
                break;
            case 6: // Exit
                dialog.dispose();
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibrarySystemGUI().setVisible(true);
        });
    }
}