package librarySystemsProject;

/*
Names: 
Jade Freeman: 2300078
Shavon Gordon: 2306989
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
*/

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Password 
{
	//Default Constructor
    private String hashedPassword;
    private String username;
    private boolean isPasswordChanged;
    private boolean isFirstLogin;

    // Primary Constructor
    public Password(String username, String password) 
    {
        this.username = username;
        this.isPasswordChanged = false;
        this.hashedPassword = hashPassword(password);
        this.isFirstLogin = true;  // Mark as first login initially
    }

    // Getter and setter for first login flag
    public boolean isFirstLogin() 
    {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean isFirstLogin)
    {
        this.isFirstLogin = isFirstLogin;
    }

    // Hash Password using SHA-256
    private static String hashPassword(String password) 
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Generate a default 8-character password
    public static String generateDefaultPassword() 
    {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    // Save user and password to file
    public void savePasswordToFile() 
    {
        if (username.equals("admin")) {
            System.out.println("Admin registration is not allowed!");
            return;
        }

        File file = new File("passwords.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) 
        {
        	//Password file format
            writer.write(username + "," + hashedPassword + "," + isPasswordChanged + "," + isFirstLogin + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieve user from passwords.txt
    public static Password getUserFromFile(String username, String enteredPassword) 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("passwords.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;
              
                //Data store in File is split into 4 parts
                String storedUsername = parts[0];
                String storedHashedPassword = parts[1];
                boolean isPasswordChanged = Boolean.parseBoolean(parts[2]);
                boolean isFirstLogin = Boolean.parseBoolean(parts[3]);
                
                if (storedUsername.equals(username) && storedHashedPassword.equals(hashPassword(enteredPassword))) {
                    Password user = new Password(username, enteredPassword);
                    user.isPasswordChanged = isPasswordChanged;
                    user.isFirstLogin = isFirstLogin;
                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Change password method
    public void changePassword(String newPassword)
    {
        if (!isValidPassword(newPassword)) {
            System.out.println("Password must be at least 8 characters long and contain a mix of letters, numbers, and special characters.");
            return;
        }

        this.hashedPassword = hashPassword(newPassword);
        this.isPasswordChanged = true;
        this.isFirstLogin = false; // Password changed, so it's no longer the first login
        
        // Update password in file
        if (updatePasswordInFile()) {
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("Error: Password update failed.");
        }
    }

    // Format for password validation
    public boolean isValidPassword(String password) 
    {
        return password.length() >= 8 && 
               password.matches(".*[A-Z].*") && 
               password.matches(".*[a-z].*") && 
               password.matches(".*[0-9].*") && 
               password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    // Update Password in passwords.txt
    private boolean updatePasswordInFile() 
    {
        File originalFile = new File("passwords.txt");
        File tempFile = new File("temp_passwords.txt");
        boolean isUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) 
            {
            	//Data store in File is split into 4 parts
                String[] data = line.split(",");
                if (data.length < 4) continue;

                String storedUsername = data[0];

                if (storedUsername.equals(this.username))
                {
                	//Format of info stored
                    writer.write(username + "," + hashedPassword + "," + true + "," + false + "\n");
                    isUpdated = true;
                } else {
                    writer.write(line + "\n");
                }
            }
           //Check for errors will updating file
        } catch (IOException e) 
        {
            e.printStackTrace();
            return false;
        }
        //If no update was made, the temporary file (temp_passwords.txt) is deleted
        if (!isUpdated) 
        {
            tempFile.delete();
            return false;
        }
        //If the originalFile.delete() fails, an error message is printed
        if (!originalFile.delete()) 
        {
            System.err.println("Error: Could not delete original file.");
            return false;
        }
        //If renaming fails, an error message is printed
        if (!tempFile.renameTo(originalFile)) {
            System.err.println("Error: Could not rename temp file.");
            return false;
        }

        return true;
    }

    // Check if entered password matches stored password
    public boolean isPasswordCorrect(String enteredPassword) 
    {
        return hashedPassword.equals(hashPassword(enteredPassword));
    }

    // Getter for isPasswordChanged
    public boolean isPasswordChanged() 
    {
        return this.isPasswordChanged;
    }
}
