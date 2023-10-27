1. Prerequisites:
Java Development Kit (JDK): Ensure you have JDK installed. If not, download and install it from Oracle's official website. (https://www.oracle.com/java/technologies/downloads/#java11)

MySQL: Ensure you have MySQL installed and running. If not, download and install it from MySQL's official website. (https://dev.mysql.com/downloads/)

2. Install Eclipse:
Download the Eclipse IDE for Java EE Developers from Eclipse's official website. (https://www.eclipse.org/downloads/packages/)
Extract the downloaded zip file.
Run the eclipse.exe (on Windows) or eclipse (on Linux/Mac) to start Eclipse.
3. Install Apache Tomcat:
Download the desired version of Apache Tomcat from Tomcat's official website. Preferably, download the Core zip/tar.gz under the Binary Distributions section. (https://tomcat.apache.org/download-90.cgi)  
Extract the downloaded file to a directory of your choice. This directory will be referred to as TOMCAT_HOME.
4. Configure Apache Tomcat in Eclipse:
Open Eclipse.
Go to Window > Preferences.
In the left pane, navigate to Server > Runtime Environments.
Click on Add.
Select the version of Tomcat you've downloaded (e.g., Tomcat 9.0) and click Next.
For the Tomcat installation directory, browse and select your TOMCAT_HOME directory.
Click Finish and then Apply and Close.
5. Set Up the Project in Eclipse:
Open Eclipse.
Go to File > Import.
Select General > Existing Projects into Workspace and click Next.
Browse to the directory where your project is located and select the project.
Click Finish.
6. Add Required Libraries:
Right-click on your project in the Project Explorer.
Go to Build Path > Configure Build Path.
In the Libraries tab, click Add External JARs.
Add the necessary JAR files (like MySQL JDBC driver, JSTL, etc.).
Click Apply and Close.
7. Configure the Database:
Ensure your MySQL server is running.
Use a MySQL client (like MySQL Workbench or command line) to create a user with the username john and password pass1234 (or update the connection string in your DAO to match your MySQL credentials).
Ensure the user has the necessary privileges to create and modify databases.
8. Run the Project:
Right-click on your project in the Project Explorer.
Go to Run As > Run on Server.
Choose the Tomcat server you've configured and click Finish.
Your project should now be running, and you can access it via a web browser at http://localhost:8080/YourProjectName.
9. Initialize the Database:
Access the /initialize endpoint in your browser (e.g., http://localhost:8080/YourProjectName/initialize) to run the initialization script and set up the database.
10. Access the Application:
You can now access the application's functionalities like registration, login, etc., via the respective endpoints.

Remember to always keep your MySQL server running when you're working with the application. If you make changes to the code, you might need to restart the server in Eclipse for the changes to take effect.
