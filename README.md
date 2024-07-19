Registration Form with Database Integration (Java Swing and MySQL)
This Java code creates a simple registration form using Swing and interacts with a MySQL database to store and retrieve user information.

Key Features:

User Interface: The form includes fields for ID, name, date of birth, mobile number, address, gender, and a checkbox for accepting terms and conditions.
New User/Existing User: Users can choose to register as a new user or update their details if they are an existing user.
Database Integration: The code connects to a MySQL database (registration_system) to store and retrieve user data.
Error Handling: Basic error handling is implemented using JOptionPane to display error messages to the user.
Reset Functionality:  "Reset" button clears all fields and resets the form.
View User Details: A separate window displays the details of the last registered user.
Connection Details:

Database: registration_system
Host: localhost
Port: 3306
Username: root
Password: 4228
Code Structure:

RegistrationForm Class:
Creates the main JFrame and its components using GridBagLayout for layout.
Handles user input and database interactions.
Contains methods for registration, reset, populating existing users, creating the database table, and displaying user details.
main Method:
Loads the MySQL JDBC driver.
Creates the RegistrationForm object and makes it visible.
Creates the users table in the database if it doesn't exist.
