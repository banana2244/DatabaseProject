public class Client {
    protected int clientID;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String phoneNumber;
    protected String creditCardInfo; 
    protected String password;

    // Constructors
    public Client() {
    }

    public Client(int clientID, String email, String firstName, String lastName, String address, String phoneNumber, String creditCardInfo, String password) {
        this.clientID = clientID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.creditCardInfo = creditCardInfo;
        this.password = password;
    }

    // Getter and Setter methods
    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreditCardInfo() {
        return creditCardInfo;
    }

    public void setCreditCardInfo(String creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    public String getPassword() {
        return password;
    }

}
