public class ClientRequest {
    private int id;
    private String clientName;
    private String clientEmail;
    private String requestDetails;
    private String response; // This will hold the response to the request
    private Boolean ready;
    private Boolean accepted;
    private Tree tree;

    // Constructor with all fields
    public ClientRequest(int id, String clientName, String clientEmail, String requestDetails, String response, Boolean ready, Boolean accepted) {
        this.id = id;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.requestDetails = requestDetails;
        this.response = response;
        this.ready = ready;
        this.accepted = accepted;
    }
    
    public Boolean getReady() {
    	return ready;
    }
    
    public void setReady(Boolean ready) {
    	this.ready = ready;
    }
    
    public void setAccepted(Boolean accepted) {
    	this.accepted = accepted;
    }
    
    public Boolean getAccepted() {
    	return accepted;
    }
    

    // Getters and setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(String requestDetails) {
        this.requestDetails = requestDetails;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

//     Optionally, include a constructor without the id for when creating new requests before they're persisted
    public ClientRequest(String clientName, String clientEmail, String requestDetails) {
        this(0, clientName, clientEmail, requestDetails, null, false, null);
    }

    // Optionally, include an empty constructor if needed for framework or other purposes
    public ClientRequest() {
    }

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

    // Additional methods and logic...
}
