import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class ControlServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
//	    private userDAO userDAO = new userDAO();
	    private clientDAO clientDAO = new clientDAO();
	    private String currentUser;
	    private HttpSession session=null;
	    
	    public ControlServlet()
	    {
	    	
	    }
	    
	    public void init()
	    {
//	    	userDAO = new userDAO();
	    	clientDAO = new clientDAO();
	    	currentUser= "";
	    }
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getParameter("action");
	        try {
	            if (action != null) {
	                switch(action) {
	                    case "submitClientRequest":
	                        handleSubmitClientRequest(request, response);
	                        break;
	                    case "accept":
	                        handleAcceptRequest(request, response);
	                        break;
	                    case "deny":
	                        handleDenyRequest(request, response);
	                        break;
	                    case "respond":
	                        handleRespondRequest(request, response);
	                        break;
	                    default:
	                        doGet(request, response);
	                }
	            } else {
	                // Handle the case where action is null
	                doGet(request, response);
	            }
	        } catch (SQLException e) {
	            throw new ServletException("Database access error!", e);
	        }
	    }
	    
	    
	    private void handleAcceptRequest(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	        int requestId = Integer.parseInt(request.getParameter("requestId"));
	        clientDAO.updateClientRequestAccepted(requestId, true);
	        response.sendRedirect("quoteManagement"); // Redirect to the management page
	    }

	    private void handleDenyRequest(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	        int requestId = Integer.parseInt(request.getParameter("requestId"));
	        clientDAO.updateClientRequestAccepted(requestId, false);
	        response.sendRedirect("quoteManagement"); // Redirect to the management page
	    }

	    private void handleRespondRequest(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	        int requestId = Integer.parseInt(request.getParameter("requestId"));
	        String responseText = request.getParameter("responseText");
	        clientDAO.updateClientRequestResponse(requestId, responseText);
//	        response.sendRedirect("quoteManagement"); // Redirect to the management page
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getServletPath();
	        System.out.println(action);
	    
	    try {
        	switch(action) {  
        	case "/login":
        		login(request,response);
        		break;
        	case "/register":
        		register(request, response);
        		break;
        	case "/initialize":
        		clientDAO.init();
        		System.out.println("Database successfully initialized!");
        		rootPage(request,response,"");
        		break;
        	case "/root":
        		rootPage(request,response, "");
        		break;
        	case "/logout":
        		logout(request,response);
        		break;
//        	 case "/list": 
//                 System.out.println("The action is: list");
//                 listUser(request, response);           	
//                 break;
        	case "/quoteManagement":
        		quoteManagement(request,response);
        		break;
            case "/submitClientRequest":
                handleSubmitClientRequest(request, response);
                break;
            case "/ClientView":
                displayClientRequests(request, response);
                break;
        		 
	    	}
	    }
	    catch(Exception ex) {
        	System.out.println(ex.getMessage());
	    	}
	    }
	    
	    
	    private void displayClientRequests(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, SQLException {
	        HttpSession session = request.getSession(false);
	        if (session != null && session.getAttribute("loggedClient") != null) {
	            Client loggedClient = (Client) session.getAttribute("loggedClient");
	            request.setAttribute("loggedClientReal", loggedClient);
	            List<ClientRequest> clientRequests = clientDAO.getClientRequestsByEmail(loggedClient.getEmail());
	            request.setAttribute("listClientRequests", clientRequests);
	            request.getRequestDispatcher("ClientView.jsp").forward(request, response);
	        } else {
	            response.sendRedirect("login.jsp"); // Redirect to login if the session or client is null
	        }
	    }

        	
//	    private void listUser(HttpServletRequest request, HttpServletResponse response)
//	            throws SQLException, IOException, ServletException {
//	        System.out.println("listUser started: 00000000000000000000000000000000000");
//
//	     
//	        List<user> listUser = userDAO.listAllUsers();
//	        request.setAttribute("listUser", listUser);       
//	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");       
//	        dispatcher.forward(request, response);
//	     
//	        System.out.println("listPeople finished: 111111111111111111111111111111111111");
//	    }
	    	        
	    private void rootPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
	    	System.out.println("root view");
			request.setAttribute("listclient", clientDAO.listAllClients());
	    	request.getRequestDispatcher("rootView.jsp").forward(request, response);
	    }
	    
	    
	    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	 String email = request.getParameter("email");
	    	 String password = request.getParameter("password");
	    	 
	    	 if (email.equals("root") && password.equals("pass1234")) {
				 System.out.println("Login Successful! Redirecting to root");
				 session = request.getSession();
				 session.setAttribute("username", email);
				 rootPage(request, response, "");
	    	 } else if(email.equals("david") && password.equals("h")) {
				 System.out.println("Login Successful! Redirecting to David Page!");
				 session = request.getSession();
				 session.setAttribute("username", email);
				 request.getRequestDispatcher("DavidHome.jsp").forward(request, response);
	    	 }
 
	    	    if (clientDAO.isValid(email, password)) {
	    	        // Redirect to client page
	    	        System.out.println("Login Successful! Redirecting to client page");

	    	        // Retrieve the client details based on the email
	    	        Client client = clientDAO.getClientByEmail(email);

	    	        if (client != null) {
	    	            // Create a new session or retrieve the existing one
	    	            HttpSession session = request.getSession();
	    	            session.setAttribute("loggedClient", client);
	    	            displayClientRequests(request, response);

	    	        } else {
	    	            // Client data not found
	    	            request.setAttribute("loginStr", "Login Failed: Client details not found.");
	    	            request.getRequestDispatcher("login.jsp").forward(request, response);
	    	        }
	    	    }

	    	 else {
	    		 request.setAttribute("loginStr","Login Failed: Please check your credentials.");
	    		 request.getRequestDispatcher("login.jsp").forward(request, response);
	    	 }
	    }
	           
	    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	        String email = request.getParameter("email");
	        String firstName = request.getParameter("firstName");
	        String lastName = request.getParameter("lastName");
	        String password = request.getParameter("password");
	        String address = request.getParameter("address"); 
	        String phoneNumber = request.getParameter("phoneNumber"); 
	        String creditCardInfo = request.getParameter("creditCardInfo"); 
	        String confirm = request.getParameter("confirmation");
	        
	        if (password.equals(confirm)) {
	            if (!clientDAO.checkEmail(email)) {
	                System.out.println("Registration Successful! Added to database");
	                Client client = new Client(0, email, firstName, lastName, address, phoneNumber, creditCardInfo, password); // 0 for clientID because it's auto-incremented
	                clientDAO.insert(client);
	                response.sendRedirect("login.jsp");
	            } else {
	                System.out.println("Email taken, please enter a new email");
	                request.setAttribute("errorOne","Registration failed: Email taken, please enter a new email.");
	                request.getRequestDispatcher("register.jsp").forward(request, response);
	            }
	        } else {
	            System.out.println("Password and Password Confirmation do not match");
	            request.setAttribute("errorTwo","Registration failed: Password and Password Confirmation do not match.");
	            request.getRequestDispatcher("register.jsp").forward(request, response);
	        }
	    }
 
	    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	currentUser = "";
        		response.sendRedirect("login.jsp");
        	}
	
  
	    private void handleSubmitClientRequest(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException {
	        
	        String clientName = request.getParameter("clientName");
	        String clientEmail = request.getParameter("clientEmail");
	        String requestDetails = request.getParameter("requestDetails");

	        ClientRequest clientRequest = new ClientRequest(clientName, clientEmail, requestDetails);
	        clientDAO dao = new clientDAO();

	        boolean isInserted = dao.insertClientRequest(clientRequest);

	        if (isInserted) {
	            request.setAttribute("message", "Request submitted successfully.");
	            getServletContext().getRequestDispatcher("/success.jsp").forward(request, response);
	        } else {
	            request.setAttribute("message", "Failed to submit request.");
	            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
	        }
	    }

	    private void quoteManagement(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException, SQLException {
	        System.out.println("quoteManagement");
	        try {
	            List<ClientRequest> clientRequests = clientDAO.getAllClientRequests();
	            if(clientRequests.isEmpty()) {
	                System.out.println("No client requests found.");
	            }
	            
	            request.setAttribute("listclient", clientRequests);
	            request.getRequestDispatcher("quoteManagement.jsp").forward(request, response);
	        } catch (Exception e) {
	            e.printStackTrace(); // Log the exception
	            // Handle the error appropriately
	        }
	    }

	    
	    
	    
}
	        
	        
	    
	        
	        
	        
	    


