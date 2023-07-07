# BankApp - Foodmora
This is a bank app created using java and it runs in the terminal as it doesn't have any GUI. <br />
New users can signup with their social security number in format YYYY-ABC where YYYY is numeric year of birth and ABC are any alphabets. <br />

Registerd users can perform below listed tasks: <br />
[1] View Transactions <br />
[2] Deposit Money <br />
[3] Withdraw Money <br />
[4] Send Money <br />
[5] Change Password <br />
[6] Logout <br />

All the data is being saved to text files, so no data is lost when application runs again after closing.

# Test Users
1. 	SSN : 1990-roh<br />
	Password : Pas$w0rd
2. 	SSN : 1990-rrr<br />
	Password : Pas$w0rd
	


# Tests
I have included some test cases that can be found in the _tests_ folder inside src folder
Some tests have been disabled as they need to have new data every time they run (like creating new user)


# Java Version
17.0.2

# How to run the application
Your system should have JDK17 installed to run the application. <br />
Steps to run the application. <br />
  1.  Close this repo
  2.  open terminal
  3.  locate BankApp folder
  4.  run command "java -jar SwedBank.jar"

# Documents
Folder ProjectManagement has documents related to management of project <br />
  1. Class diagrams <br />
         [LucidChart Class Diagram](https://lucid.app/lucidchart/f3c39bd8-328c-496c-a37a-b65db1ad0d5b/edit?viewport_loc=-1635%2C-167%2C3840%2C1848%2C0_0&invitationId=inv_fba2f3c4-2e37-423c-9ab1-3a1d106eda3a) <br />
  3. Sequense diagrams <br />
         [Sequence Diagram for New User](https://github.com/aggarrohit/BankApp/blob/master/ProjectManagement/SequenceDiagramPerson.png) <br />
         [Sequence Diagram for Logged-In User](https://github.com/aggarrohit/BankApp/blob/master/ProjectManagement/SequenceDiagramUser.png)
  5. Stories and tasks - Trello [Click here](https://trello.com/invite/b/zm4XgYgq/ATTIa5fa25fac4e9ece4dceef053198867db6C2C0D63/swedenbank)
  6. Use case diagram [Click here](https://github.com/aggarrohit/BankApp/blob/master/ProjectManagement/UseCaseDiagram.png)
     
