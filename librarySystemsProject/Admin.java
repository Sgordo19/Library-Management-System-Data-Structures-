/*
Jade Freeman: 2300078
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
Shavon Gordon: 2306989 
 */

package librarySystemsProject;

public class Admin extends User{
	private String password;
	private boolean chkadmin;
	
	// Default Constructor
	public Admin() {
		super.username = "admin";
		password = "admin1234";
		chkadmin = true;
	}
	
	// Primary Constructor
	public Admin(String username, String password, boolean chkadmin)
	{
		super(username);
		this.password = password;
		this.chkadmin = chkadmin;
	}
	
	// Copy Constructor
	public Admin(Admin obj)
	{
		super(obj);
		this.password = obj.password;
		this.chkadmin = obj.chkadmin;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isChkadmin() {
		return chkadmin;
	}

	public void setChkadmin(boolean chkadmin) {
		this.chkadmin = chkadmin;
	}
	
	
}