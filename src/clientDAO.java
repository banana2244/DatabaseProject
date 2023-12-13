import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class clientDAO {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public clientDAO() {}

    protected void connect_func() throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=pass1234");
        }
    }

    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
            connect.close();
        }
    }

    public List<Client> listAllClients() throws SQLException {
        List<Client> listClient = new ArrayList<Client>();
        String sql = "SELECT ClientID, FirstName, LastName, Address, Email, PhoneNumber, CreditCardInfo, Password FROM Clients";
        connect_func();
        statement = connect.createStatement();
        resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int clientID = resultSet.getInt("ClientID");
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            String address = resultSet.getString("Address");
            String email = resultSet.getString("Email");
            String creditCardInfo = resultSet.getString("CreditCardInfo");
            String phoneNumber = resultSet.getString("PhoneNumber");
            String password = resultSet.getString("Password");

            Client client = new Client(clientID, email, firstName, lastName, address, phoneNumber, creditCardInfo, password);
            listClient.add(client);
        }

        resultSet.close();
        disconnect();
        return listClient;
    }

    public void insert(Client client) throws SQLException {
        String sql = "INSERT INTO Clients (FirstName, LastName, Address, CreditCardInfo, PhoneNumber, Email, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, client.getFirstName());
        preparedStatement.setString(2, client.getLastName());
        preparedStatement.setString(3, client.getAddress());
        preparedStatement.setString(4, client.getCreditCardInfo());
        preparedStatement.setString(5, client.getPhoneNumber());
        preparedStatement.setString(6, client.getEmail());
        preparedStatement.setString(7, client.getPassword());  // Add this line
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    // ... (Other methods like delete, update, etc. can be added similarly)

    public boolean checkEmail(String email) throws SQLException {
        boolean checks = false;
        String sql = "SELECT * FROM Clients WHERE Email = ?";
        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            checks = true;
        }

        return checks;
    }
    
    public boolean isValid(String email, String password) throws SQLException {
        String sql = "SELECT * FROM Clients WHERE Email = ? AND Password = ?";
        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        boolean valid = resultSet.next();
        resultSet.close();
        disconnect();
        return valid;
    }
    
    public boolean insertClientRequest(ClientRequest request) {
        boolean insertSuccess = false;
        PreparedStatement pstmt = null;

        try {
            connect_func();  // Connect to the database
            
            // Include the ready column and set it to false by default
            String sql = "INSERT INTO client_requests (clientName, clientEmail, requestDetails, ready) VALUES (?, ?, ?, ?)";
            pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, request.getClientName());
            pstmt.setString(2, request.getClientEmail());
            pstmt.setString(3, request.getRequestDetails());
            pstmt.setBoolean(4, false); // Set ready to false
            
            int affectedRows = pstmt.executeUpdate();
            insertSuccess = affectedRows > 0;
            
        } catch (SQLException e) {
            // Log the exception
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return insertSuccess;
    }

    
    
    public boolean updateClientRequestResponse(int requestId, String response) {
        boolean updateSuccess = false;
        PreparedStatement pstmt = null;

        try {
            connect_func();  // Connect to the database
            
            String sql = "UPDATE client_requests SET response = ?, ready = NOT ready WHERE id = ?";
            pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, response);
            pstmt.setInt(2, requestId);
            
            int affectedRows = pstmt.executeUpdate();
            updateSuccess = affectedRows > 0;
            
        } catch (SQLException e) {
            // Log the exception
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return updateSuccess;
    }

    
    
    public boolean updateClientRequestAccepted(int requestId, Boolean accepted) {
        boolean updateSuccess = false;
        PreparedStatement pstmt = null;

        try {
            connect_func();  // Connect to the database

            String sql = "UPDATE client_requests SET accepted = ? WHERE id = ?";
            pstmt = connect.prepareStatement(sql);
            if (accepted == null) {
                pstmt.setNull(1, Types.BOOLEAN); // Set null if accepted is null
            } else {
                pstmt.setBoolean(1, accepted);
            }
            pstmt.setInt(2, requestId);

            int affectedRows = pstmt.executeUpdate();
            updateSuccess = affectedRows > 0;

        } catch (SQLException e) {
            // Log the exception
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return updateSuccess;
    }

    
    
    public List<ClientRequest> getAllClientRequests() throws SQLException {
        List<ClientRequest> listClientRequests = new ArrayList<ClientRequest>();
        String sql = "SELECT id, clientName, clientEmail, requestDetails, response, ready, accepted FROM client_requests";
        
        connect_func(); // Connect to the database
        statement = connect.createStatement();
        resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String clientName = resultSet.getString("clientName");
            String clientEmail = resultSet.getString("clientEmail");
            String requestDetails = resultSet.getString("requestDetails");
            String response = resultSet.getString("response"); 
            boolean readyPrimitive = resultSet.getBoolean("ready");
            Boolean ready = resultSet.wasNull() ? null : readyPrimitive;
            boolean acceptedPrimitive = resultSet.getBoolean("accepted");
            Boolean accepted = resultSet.wasNull() ? null : acceptedPrimitive;

            
            ClientRequest request = new ClientRequest(id, clientName, clientEmail, requestDetails, response, ready, accepted);

            listClientRequests.add(request);
        }

        resultSet.close();
        disconnect(); // Disconnect from the database
        
        return listClientRequests;
    }
    
    
    
    public Client getClientByEmail(String email) throws SQLException {
        Client client = null;
        String sql = "SELECT * FROM Clients WHERE Email = ?";
        
        connect_func();
        
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next()) {
            int clientID = resultSet.getInt("ClientID");
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            String address = resultSet.getString("Address");
            String phoneNumber = resultSet.getString("PhoneNumber");
            String creditCardInfo = resultSet.getString("CreditCardInfo");
            String password = resultSet.getString("Password");
            // Create a new Client object using the retrieved data
            client = new Client(clientID, email, firstName, lastName, address, phoneNumber, creditCardInfo, password);
        }
        
        resultSet.close();
        preparedStatement.close();
        disconnect();
        
        return client;
    }
    
    
    

    public List<ClientRequest> getClientRequestsByEmail(String email) throws SQLException {
        List<ClientRequest> listClientRequests = new ArrayList<>();
        String sql = "SELECT * FROM client_requests WHERE clientEmail = ?";
        
        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        resultSet = preparedStatement.executeQuery();
        
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String clientName = resultSet.getString("clientName");
            String clientEmail = resultSet.getString("clientEmail");
            String requestDetails = resultSet.getString("requestDetails");
            String response = resultSet.getString("response");
            boolean readyPrimitive = resultSet.getBoolean("ready");
            Boolean ready = resultSet.wasNull() ? null : readyPrimitive;
            boolean acceptedPrimitive = resultSet.getBoolean("accepted");
            Boolean accepted = resultSet.wasNull() ? null : acceptedPrimitive;
            
            ClientRequest request = new ClientRequest(id, clientName, clientEmail, requestDetails, response, ready, accepted);
            listClientRequests.add(request);
        }
        
        resultSet.close();
        preparedStatement.close();
        disconnect();
        
        return listClientRequests;
    }

    // public List<Tree> getAllTrees() throws SQLException {
    //     List<Tree> listTree = new ArrayList<Tree>();
    //     String sql = "SELECT * FROM Trees";
    //     connect_func();
    //     statement = connect.createStatement();
    //     resultSet = statement.executeQuery(sql);

    //     while (resultSet.next()) {
    //         int treeID = resultSet.getInt("TreeID");
    //         int clientID = resultSet.getInt("ClientID");
    //         int size = resultSet.getInt("Size");
    //         int height = resultSet.getInt("Height");
    //         String location = resultSet.getString("Location");
    //         String proximityToHouse = resultSet.getString("ProximityToHouse");
    //         String picture1 = resultSet.getString("Picture1");
    //         String picture2 = resultSet.getString("Picture2");
    //         String picture3 = resultSet.getString("Picture3");

    //         Tree tree = new Tree(treeID, clientID, size, height, location, proximityToHouse, picture1, picture2, picture3);
    //         listTree.add(tree);
    //     }

    //     resultSet.close();
    //     disconnect();
    //     return listTree;
    // }

    // public List<Client> getBigClients() throws SQLException {
    //     List<Client> listClient = new ArrayList<Client>();
    //     //top client is the one with the most trees
    //     String sql = "SELECT * FROM Clients ORDER BY NumTrees DESC LIMIT 1";
    //     connect_func();
    //     statement = connect.createStatement();
    //     resultSet = statement.executeQuery(sql);

    //     while (resultSet.next()) {
    //         int clientID = resultSet.getInt("ClientID");
    //         String firstName = resultSet.getString("FirstName");
    //         String lastName = resultSet.getString("LastName");
    //         String address = resultSet.getString("Address");
    //         String email = resultSet.getString("Email");
    //         String creditCardInfo = resultSet.getString("CreditCardInfo");
    //         String phoneNumber = resultSet.getString("PhoneNumber");
    //         String password = resultSet.getString("Password");
    //         int numTrees = resultSet.getInt("NumTrees");

    //         Client client = new Client(clientID, email, firstName, lastName, address, phoneNumber, creditCardInfo, password , numTrees);
    //         listClient.add(client);
    //     }

    //     resultSet.close();
    //     disconnect();
    //     return listClient;
    // }



    
    public void init() throws SQLException, FileNotFoundException, IOException{
    	connect_func();
        statement =  (Statement) connect.createStatement();
        
        String[] INITIAL = {
                "drop database if exists testdb;",
                "create database testdb;",
                "use testdb;",

                // Clients Table
                "CREATE TABLE if not exists Clients (" +
                "ClientID INT AUTO_INCREMENT PRIMARY KEY," +
                "FirstName VARCHAR(50) NOT NULL," +
                "LastName VARCHAR(50) NOT NULL," +
                "Address VARCHAR(255) NOT NULL," +
                "CreditCardInfo VARCHAR(255)," +
                "PhoneNumber VARCHAR(15) NOT NULL," +
                "Email VARCHAR(100) NOT NULL UNIQUE," +
                "Password VARCHAR(255) NOT NULL," +
                "NumTrees INT NOT NULL DEFAULT 0" +
                ");",

                // Trees Table
                "CREATE TABLE if not exists Trees (" +
                "TreeID INT AUTO_INCREMENT PRIMARY KEY," +
                "ClientID INT," +
                "Size INT NOT NULL," +
                "Height INT NOT NULL," +
                "Location VARCHAR(255) NOT NULL," +
                "ProximityToHouse VARCHAR(255) NOT NULL," +
                "Picture1 VARCHAR(255)," +
                "Picture2 VARCHAR(255)," +
                "Picture3 VARCHAR(255)," +
                "FOREIGN KEY (ClientID) REFERENCES Clients(ClientID)" +
                ");",
                
                "CREATE TABLE IF NOT EXISTS client_requests (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "clientName VARCHAR(255) NOT NULL," +
                        "clientEmail VARCHAR(255) NOT NULL," +
                        "requestDetails INT," +
                        "FOREIGN KEY (requestDetails) REFERENCES TREE(TreeID)," +
                        "response TEXT," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "ready BOOLEAN," +
                        "accepted BOOLEAN," +
                        "paid BOOLEAN," +
                        "easy BOOLEAN," +
                        "paidAt TIMESTAMP," +
                        "completed BOOLEAN," +
                        "FOREIGN KEY (clientEmail) REFERENCES Clients(Email)"+
                        ");",

                // Admin Table
                "CREATE TABLE if not exists Admin (" +
                "AdminID INT AUTO_INCREMENT PRIMARY KEY," +
                "Username VARCHAR(50) NOT NULL UNIQUE," +
                "Password VARCHAR(255) NOT NULL" +
                ");"
            };
        
        String[] TUPLES = {
        	    // Sample data for Clients
        		"INSERT INTO Clients (FirstName, LastName, Address, CreditCardInfo, PhoneNumber, Email, Password) VALUES ('John', 'Doe', '123 Main St', '1234567812345678', '123-456-7890', 'john.doe@example.com', 'johnspassword');",
        		"INSERT INTO Clients (FirstName, LastName, Address, CreditCardInfo, PhoneNumber, Email, Password) VALUES ('Jane', 'Smith', '456 Elm St', '8765432187654321', '987-654-3210', 'jane.smith@example.com', 'janespassword');",
        		"INSERT INTO Clients (FirstName, LastName, Address, CreditCardInfo, PhoneNumber, Email, Password) VALUES ('Alice', 'Johnson', '789 Oak St', '1122334455667788', '111-222-3333', 'alice.johnson@example.com', 'alicespassword');",
        		"INSERT INTO Clients (FirstName, LastName, Address, CreditCardInfo, PhoneNumber, Email, Password) VALUES ('Bob', 'Williams', '101 Pine St', '9988776655443322', '444-555-6666', 'bob.williams@example.com', 'bobspassword');",
        		"INSERT INTO Clients (FirstName, LastName, Address, CreditCardInfo, PhoneNumber, Email, Password) VALUES ('Charlie', 'Brown', '202 Maple St', '2233445566778899', '777-888-9999', 'charlie.brown@example.com', 'charliespassword');",
        		"INSERT INTO Clients (FirstName, LastName, Address, CreditCardInfo, PhoneNumber, Email, Password) VALUES ('David', 'Jones', '303 Cedar St', '3344556677889900', '000-111-2222', 'david.jones@example.com', 'davidspassword');",
        		"INSERT INTO Clients (FirstName, LastName, Address, CreditCardInfo, PhoneNumber, Email, Password) VALUES ('Eve', 'Davis', '404 Birch St', '4455667788990011', '333-444-5555', 'eve.davis@example.com', 'evespassword');",
        		"INSERT INTO Clients (FirstName, LastName, Address, CreditCardInfo, PhoneNumber, Email, Password) VALUES ('Frank', 'Miller', '505 Cherry St', '5566778899001122', '666-777-8888', 'frank.miller@example.com', 'frankspassword');",
        		"INSERT INTO Clients (FirstName, LastName, Address, CreditCardInfo, PhoneNumber, Email, Password) VALUES ('Grace', 'Garcia', '606 Dogwood St', '6677889900112233', '999-000-1111', 'grace.garcia@example.com', 'gracespassword');",
        		"INSERT INTO Clients (FirstName, LastName, Address, CreditCardInfo, PhoneNumber, Email, Password) VALUES ('Henry', 'Rodriguez', '707 Fir St', '7788990011223344', '222-333-4444', 'henry.rodriguez@example.com', 'henryspassword');",

        	    // (Other sample data can be added similarly)

        	    // Sample data for Admin
        	    "INSERT INTO Admin (Username, Password) VALUES ('root', 'pass1234');"
        	};

        
        //for loop to put these in database
        for (int i = 0; i < INITIAL.length; i++)
        	statement.execute(INITIAL[i]);
        for (int i = 0; i < TUPLES.length; i++)	
        	statement.execute(TUPLES[i]);
        disconnect();
    }

    // ... (Other methods can be added similarly)
}
