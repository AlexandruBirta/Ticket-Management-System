# Bachelor's thesis (2020)

Airline ticket management system
following the MVC design pattern and using the Java
programming language, SQL and APIs such as JDBC for
database connectivity, Swing for GUI integration, Java
Security for cryptography algorithms using RSA
encryption for user passwords, ZXing for barcode
generation, JavaMail for password reset via e-mail and
Junit for creating test cases.

Here's how the program works in action:

### Welcome screen

This is the first window that is shown for the user upon opening the program.

![welcome1](https://user-images.githubusercontent.com/80108383/110237430-bec4bc00-7f44-11eb-9b05-b2b7e2649beb.png)

### Login screen

After clicking on the "Login" button on the welcome screen we are redirected here:

![login](https://user-images.githubusercontent.com/80108383/110237520-31359c00-7f45-11eb-8871-d552a11ddf9b.png)

### Register page

If we want a new user account to be made we'll proceed to register one like this:

- passwords must match
- username should be unique
- email should be unique and valid (validated using regular expressions)

![register](https://user-images.githubusercontent.com/80108383/110237554-53c7b500-7f45-11eb-98f4-f9a9f755f375.png)

### Passowrd reset

If we forgot our password and wish to reset it we can do so by clicking on the "Forgot your password?" link on the login screen.

![password1](https://user-images.githubusercontent.com/80108383/110237640-aef9a780-7f45-11eb-82b8-309da41e9802.png)

After we put in our email we should recieve a verification code to reset our password in our inbox like this:

![password2](https://user-images.githubusercontent.com/80108383/110237687-faac5100-7f45-11eb-9a2e-bc58867e4210.png)

Then we introduce it in the text field:

![password3](https://user-images.githubusercontent.com/80108383/110237721-216a8780-7f46-11eb-86b3-031948ca00a0.png)

And we proceed to reset the password.

![password4](https://user-images.githubusercontent.com/80108383/110237742-3fd08300-7f46-11eb-82e2-62824094d553.png)

### Search flights

After logging in we are redirected to the ticket search engine where we will find tickets based on our destination and price range.

![search4](https://user-images.githubusercontent.com/80108383/110237830-cf763180-7f46-11eb-8826-13e189e818bf.png)

If we don't find any, we will be prompted accordingly with an error message:

![search0](https://user-images.githubusercontent.com/80108383/110237841-db61f380-7f46-11eb-850c-d3c8572499f4.png)

### Choose a ticket

Now after initiating a search we will be shown all the available tickets for the destination and their price.

![bilete](https://user-images.githubusercontent.com/80108383/110237866-00eefd00-7f47-11eb-9481-d5c03fc363d7.png)

### Ticket generation

After buying a ticket we finally recieve our boarding pass with all the information required and a unique qr code based on the hash code of the ticket.

![boarding_pass](https://user-images.githubusercontent.com/80108383/110237890-2da31480-7f47-11eb-90e8-60c57ae24336.png)


### Admin panel

Behind the scenes of the application the admins of the platform can insert new flights into the database and also look over the reports generated by the application and its database entries.

(flight requirements shown in red after a wrong addition)

![admin1](https://user-images.githubusercontent.com/80108383/110237988-a73b0280-7f47-11eb-9d72-709a93791639.png)

Here we can see the reports:

![admin5](https://user-images.githubusercontent.com/80108383/110238005-bb7eff80-7f47-11eb-9aad-4c7a270cc639.png)






