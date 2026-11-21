# Car Rental Management

A desktop application that lets a car rental agency manage its cars, clients and rentals. An administrator runs the agency, and clients can browse cars, request rentals and see their invoices.

## Features

- **Login and registration:** users log in with a username and password. New clients can create an account from the login screen. The interface shown depends on the role (admin or client).
- **Car management (admin):** add, edit and delete cars, with brand, type, price per day and availability.
- **Client management (admin):** add and delete clients.
- **Rental requests (admin):** review the requests clients send and accept them. Accepting a request creates the rental and its invoice, and marks the car as unavailable.
- **Accepted rentals (admin):** see the rentals that have been accepted.
- **Browse cars (client):** see only the cars that are currently available.
- **Request a rental (client):** pick a car, a start and end date, a rental type (standard, week-end or premium) and a payment method (card or cash).
- **Invoices (client):** view the invoices of past rentals.
- **Automatic pricing:** the total is the price per day times the number of days, adjusted by the car type (SUV, sedan, coupé) and the rental type, with a 5% discount for cash payment.

## Tools used

- **Java** – application logic
- **JavaFX 21** (FXML) – graphical interface
- **MySQL** with **JDBC** (MySQL Connector/J 9.5.0, included in `lib/`) – data storage
- **MySQL Workbench** – database model
- **draw.io** – use-case and class diagrams (in the `conception/` folder)
- **VS Code** – development environment

## Setup

1. Install JDK 17 or newer, [JavaFX SDK 21](https://gluonhq.com/products/javafx/) and MySQL.
2. Import the database: `mysql -u root -p < "base de donnee/db.sql"`

3. Put your MySQL password in `gestion_location_voitures/src/dao/DBUtil.java`.
4. In VS Code, open the `gestion_location_voitures` folder and run `ui.MainApp` with these VM arguments (adapt the path):
   `--module-path "/path/to/javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml`



## NOTE: admin credentials: username:admin / password:admin

## Author

Mohamed Aziz Trabelsi – Computer Science student, ISI Ariana
