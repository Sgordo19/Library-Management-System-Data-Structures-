/*
Names: 
Jade Freeman: 2300078
Shavon Gordon: 2306989
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
*/

package librarySystemsProject;

import java.io.Serializable;

public class PatronNode implements Serializable{
	private static final long serialVersionUID = 1L;
    private Patron data;
    private PatronNode nextNode;

    // Default Constructor
    public PatronNode() {
        data = new Patron();
        nextNode = null;
    }

    // Primary Constructor #1
    public PatronNode(Patron data, PatronNode nextNode) {
        this.data = new Patron(data); // Deep copy
        this.nextNode = nextNode;
    }

    // Primary Constructor #2
    public PatronNode(Patron data) {
        this.data = new Patron(data); // Deep copy
        this.nextNode = null;
    }
    
    //Primary Constructor #3 
	public PatronNode ( String name, int cardNumber)
	{
		this.data = new Patron (name, cardNumber);
		this.nextNode = null;
	}

    // Copy Constructor
    public PatronNode(PatronNode node) {
        this.data = new Patron(node.data); // Deep copy
        this.nextNode = node.nextNode;
    }

    public Patron getData() {
        return data;
    }

    public void setData(Patron data) {
        this.data = new Patron(data); // Deep copy
    }

    public PatronNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(PatronNode nextNode) {
        this.nextNode = nextNode;
    }
}