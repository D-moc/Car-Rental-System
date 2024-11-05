# Car Rental System

## Overview
The **Car Rental System** is a Java-based application designed to streamline the car rental process. This project manages **car inventory, customer details, bookings, and payments** efficiently, making it easier for users to rent cars for various durations.

![Car Rental System](car-rental.png) <!-- Add a relevant image path here if available -->

## Objectives
- **Automate** the car rental process.
- Manage **inventory, customer details, bookings, and payments**.
- Provide an intuitive interface for renting cars, applying **Object-Oriented Programming** principles.

## Features
- **Car Inventory Management**: Track and update the availability of cars (Hatchback, Sedan, SUV).
- **Customer Registration**: Record customer details for rental history.
- **Booking System**: Allow customers to book and return cars with rental duration.
- **Payment Integration**: Calculate rental costs and handle payments (to be improved in future).

## Technologies Used
- **Java** (Core functionality and GUI using Swing)
- **MySQL** (or any database integration for data persistence – planned for future enhancements)

## Challenges and Solutions
Exception Handling: Ensured error messages for invalid inputs.
Data Persistence: Added basic in-memory storage and plan to integrate a database.

## Future Enhancements
Database Integration for permanent data storage.
Login System for admins and clients.
Improved GUI Design for a better user experience.

## Getting Started

### Prerequisites
- **Java JDK** installed (version 8 or higher).
- **MySQL** installed if planning to extend the project with database support.
- **Git** installed to clone the repository.
  
- ### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/username/car-rental-system.git

2. Navigate to the project directory:
```bash
   cd car-rental-system

3. Compile the Java files:
```bash
   javac -d bin src/*.java

4. Run the application:
```bash
  java -cp bin CarRentalSystemGUI

## Usage
1. Launch the application using the command above.
2. Choose options to Rent a Car, Return a Car, or Process Payment.
3. Input the necessary details as prompted to complete transactions.


