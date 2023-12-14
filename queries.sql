-- BIG CLIENTS
SELECT ClientID, FirstName, LastName, NumTrees
FROM Clients
ORDER BY NumTrees DESC;

-- EASY CLIENTS
SELECT DISTINCT c.ClientID, c.FirstName, c.LastName
FROM Clients c
JOIN client_requests cr ON c.Email = cr.clientEmail
WHERE cr.easy = TRUE;

-- ONE TREE QUOTES
SELECT ClientID, FirstName, LastName, NumTrees
FROM Clients
WHERE NumTrees = 1;

-- PROSPECTIVE CLIENTS
SELECT DISTINCT c.ClientID, c.FirstName, c.LastName
FROM Clients c
JOIN client_requests cr ON c.Email = cr.clientEmail
WHERE cr.ready = FALSE AND c.NumTrees <= 1;

-- HIGHEST TREE
SELECT * FROM Trees
WHERE Height = (SELECT MAX(Height) FROM Trees)
AND TreeID IN (SELECT requestDetails FROM client_requests WHERE completed = TRUE);

-- OVERDUE BILLS
SELECT *
FROM client_requests
WHERE paid = FALSE AND accepted = TRUE AND  TIMESTAMPDIFF(DAY, created_at, NOW()) > 7;

-- BAD CLIENTS
SELECT ClientID, FirstName, LastName
FROM Clients
WHERE ClientID IN (
    SELECT DISTINCT c.ClientID
    FROM Clients c
    JOIN client_requests cr ON c.Email = cr.clientEmail
    WHERE cr.paid = FALSE AND accepted = TRUE AND TIMESTAMPDIFF(DAY, cr.created_at, NOW()) > 7
)
AND ClientID NOT IN (
    SELECT DISTINCT c.ClientID
    FROM Clients c
    JOIN client_requests cr ON c.Email = cr.clientEmail
    WHERE cr.paid = TRUE
);

-- GOOD CLIENTS
SELECT DISTINCT c.ClientID, c.FirstName, c.LastName
FROM Clients c
JOIN client_requests cr ON c.Email = cr.clientEmail
WHERE cr.paid = TRUE AND TIMESTAMPDIFF(HOUR, cr.created_at, cr.paidAt) <= 24;

-- STATISTICS
SELECT c.ClientID, c.FirstName, c.LastName,
       SUM(CASE WHEN cr.completed = TRUE THEN 1 ELSE 0 END) AS NumberOfTreesCompleted,
       SUM(CASE WHEN cr.paid = FALSE THEN 1 ELSE 0 END) AS TotalDue,
       SUM(CASE WHEN cr.paid = TRUE THEN 1 ELSE 0 END) AS TotalPaid,
       cr.created_at AS WorkDoneDate
FROM Clients c
JOIN client_requests cr ON c.Email = cr.clientEmail
WHERE cr.completed = TRUE
GROUP BY c.ClientID, cr.created_at;

-- SETUP


drop database if exists testdb;
create database testdb;
use testdb;

-- Clients Table
CREATE TABLE if not exists Clients ( 
ClientID INT AUTO_INCREMENT PRIMARY KEY, 
FirstName VARCHAR(50) NOT NULL, 
LastName VARCHAR(50) NOT NULL, 
Address VARCHAR(255) NOT NULL, 
CreditCardInfo VARCHAR(255), 
PhoneNumber VARCHAR(15) NOT NULL, 
Email VARCHAR(100) NOT NULL UNIQUE, 
Password VARCHAR(255) NOT NULL, 
NumTrees INT NOT NULL DEFAULT 0 
);

-- Trees Table
CREATE TABLE if not exists Trees ( 
TreeID INT AUTO_INCREMENT PRIMARY KEY, 
ClientID INT, 
Size INT NOT NULL, 
Height INT NOT NULL, 
Location VARCHAR(255) NOT NULL, 
ProximityToHouse VARCHAR(255) NOT NULL, 
Picture1 VARCHAR(255), 
Picture2 VARCHAR(255), 
Picture3 VARCHAR(255), 
FOREIGN KEY (ClientID) REFERENCES Clients(ClientID) 
);

-- Client Requests Table
CREATE TABLE IF NOT EXISTS client_requests ( 
        id INT AUTO_INCREMENT PRIMARY KEY, 
        clientName VARCHAR(255) NOT NULL, 
        clientEmail VARCHAR(255) NOT NULL, 
        requestDetails INT, 
        FOREIGN KEY (requestDetails) REFERENCES TREES(TreeID), 
        response TEXT, 
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
        ready BOOLEAN, 
        accepted BOOLEAN, 
        paid BOOLEAN, 
        easy BOOLEAN, 
        paidAt TIMESTAMP, 
        completed BOOLEAN, 
        FOREIGN KEY (clientEmail) REFERENCES Clients(Email)
        );


-- INSERTS

-- Clients
INSERT INTO Clients (FirstName, LastName, Address, CreditCardInfo, PhoneNumber, Email, Password, NumTrees) 
VALUES 
('John', 'Doe', '123 Elm St', '1234567812345678', '123-456-7890', 'john.doe@example.com', 'password123', 2),
('Jane', 'Smith', '456 Oak St', '8765432187654321', '987-654-3210', 'jane.smith@example.com', 'password123', 1),
('Alice', 'Johnson', '789 Pine St', '1111222233334444', '555-555-5555', 'alice.johnson@example.com', 'password123', 0),
('Emily', 'Clark', '101 Cherry Lane', '5555666677778888', '123-987-6543', 'emily.clark@example.com', 'password123', 0),
('Michael', 'Brown', '202 Oak Ave', '1111222233334444', '321-654-0987', 'michael.brown@example.com', 'password123', 2);



-- Trees
INSERT INTO Trees (ClientID, Size, Height, Location, ProximityToHouse) 
VALUES 
(1, 10, 20, 'Backyard', '10ft'),
(1, 15, 25, 'Frontyard', '5ft'),
(2, 12, 30, 'Sideyard', '8ft'),
(5, 18, 35, 'Garden', '12ft'),
(5, 20, 40, 'Field', '20ft');

-- Client Requests
INSERT INTO client_requests (clientName, clientEmail, requestDetails, response, created_at, ready, accepted, paid, easy, paidAt, completed) 
VALUES 
('Michael Brown', 'michael.brown@example.com', 4, NULL, NOW() - INTERVAL 9 DAY, FALSE, TRUE, FALSE, FALSE, NULL, FALSE),
('Emily Clark', 'emily.clark@example.com', NULL, NULL, NOW() - INTERVAL 2 DAY, TRUE, FALSE, FALSE, TRUE, NOW(), TRUE),
('John Doe', 'john.doe@example.com', 5, NULL, NOW() - INTERVAL 8 HOUR, TRUE, TRUE, TRUE, TRUE, NOW() - INTERVAL 6 HOUR, TRUE),
('Jane Smith', 'jane.smith@example.com', 3, NULL, NOW() - INTERVAL 10 DAY, TRUE, FALSE, FALSE, FALSE, NULL, FALSE),
('Alice Johnson', 'alice.johnson@example.com', NULL, NULL, NOW() - INTERVAL 12 DAY, FALSE, FALSE, FALSE, FALSE, NULL, FALSE);