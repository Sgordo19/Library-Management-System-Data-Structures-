/*
Jade Freeman: 2300078
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
Shavon Gordon: 2306989 
*/

package librarySystemsProject;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	
	protected String username ;
	

   public User() 
   {
	username = "Metal";	
   }

   public User (String username)
   {
	this.username = username; 
   }

   public User(User obj) 
   {
	this.username = obj.username;
   }

   public String getUsername() 
   {
	return username;
   }

   public void setName(String username) 
   {
	this.username = username;
   }

   public void Display()
   {
	System.out.println("");
   }
}