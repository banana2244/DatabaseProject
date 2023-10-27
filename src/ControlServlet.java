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
	    private userDAO userDAO = new userDAO();
	    private clientDAO clientDAO = new clientDAO();
	    private String currentUser;
	    private HttpSession session=null;
	    
	    public ControlServlet()
	    {
	    	
	    }
	    
	    public void init()
	    {
	    	userDAO = new userDAO();
	    	clientDAO = new clientDAO();
	    	currentUser= "";
	    }
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
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
        	 case "/list": 
                 System.out.println("The action is: list");
                 listUser(request, response);           	
                 break;
	    	}
	    }
	    catch(Exception ex) {
        	System.out.println(ex.getMessage());
	    	}
	    }
        	
	    private void listUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        System.out.println("listUser started: 00000000000000000000000000000000000");

	     
	        List<user> listUser = userDAO.listAllUsers();
	        request.setAttribute("listUser", listUser);       
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");       
	        dispatcher.forward(request, response);
	     
	        System.out.println("listPeople finished: 111111111111111111111111111111111111");
	    }
	    	        
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
 
	    	 else if (clientDAO.isValid(email, password)) {
	    	        // Redirect to client page
	    	        System.out.println("Login Successful! Redirecting to client page");
	    	        request.getRequestDispatcher("ClientView.jsp").forward(request, response);
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
	
  
	    
	    
}
	        
	        
	    
	        
	        
	        
	    


