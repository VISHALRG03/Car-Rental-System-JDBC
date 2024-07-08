package com.example;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CarRentalSystem {
	
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental", "root", "root");
        Statement stmt = connection.createStatement();
        Scanner sc = new Scanner(System.in);

        int operation;

        do {
            System.out.println("--------------------------------------------------------");
            System.out.println("Available Operations:");
            System.out.println("--------------------------------------------------------");

            System.out.println("1. Add Car");
            System.out.println("2. Retrieve Cars");
            System.out.println("3. Update Car");
            System.out.println("4. Delete Car");
            System.out.println("5. Add Customer");
            System.out.println("6. Retrieve Customers");
            System.out.println("7. Book Car");
            System.out.println("8. Retrieve Bookings");
            System.out.println("9. Exit");
            
            System.out.println("--------------------------------------------------------");

            System.out.println();
            System.out.print("Please Enter an Operation number you want to Perform: ");
            
            operation = sc.nextInt();
            sc.nextLine();
            System.out.println("--------------------------------------------------------");

            switch (operation) {
                case 1:
                    System.out.print("Enter car name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter car model: ");
                    String model = sc.nextLine();
                    System.out.print("Enter car year: ");
                    int year = sc.nextInt();
                    System.out.print("Enter car rental rate: ");
                    BigDecimal rentalRate = sc.nextBigDecimal();
                    sc.nextLine();
                    System.out.print("Enter car status (Available/Rented): ");
                    String status = sc.nextLine();

                    String createCarSql = "INSERT INTO Cars (name, model, year, rental_rate, status) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement createCarStatement = connection.prepareStatement(createCarSql)) {
                        createCarStatement.setString(1, name);
                        createCarStatement.setString(2, model);
                        createCarStatement.setInt(3, year);
                        createCarStatement.setBigDecimal(4, rentalRate);
                        createCarStatement.setString(5, status);
                        int rowsAffected = createCarStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Car added successfully.");
                        } else {
                            System.out.println("Failed to add car.");
                        }
                    }
                    break;

                case 2:
                    int carRetrieveOption;
                    do {
                        System.out.println("--------------------------------------------------------");
                        System.out.println("Retrieve Cars Options:");
                        System.out.println("--------------------------------------------------------");
                        System.out.println("1. Search by ID");
                        System.out.println("2. Retrieve All");
                        System.out.println("3. Exit to Previous Menu");
                        System.out.println("--------------------------------------------------------");
                        System.out.print("Please Enter an Option number: ");
                        carRetrieveOption = sc.nextInt();
                        sc.nextLine();
                        System.out.println("--------------------------------------------------------");

                        switch (carRetrieveOption) {
                            case 1:
                                System.out.print("Enter car ID to search: ");
                                int carIdToSearch = sc.nextInt();
                                String searchCarByIdSql = "SELECT * FROM Cars WHERE car_id = ?";
                                try (PreparedStatement searchCarByIdStatement = connection.prepareStatement(searchCarByIdSql)) {
                                    searchCarByIdStatement.setInt(1, carIdToSearch);
                                    ResultSet resultSet = searchCarByIdStatement.executeQuery();
                                    if (resultSet.next()) {
                                        int carId = resultSet.getInt("car_id");
                                        String carName = resultSet.getString("name");
                                        String carModel = resultSet.getString("model");
                                        int carYear = resultSet.getInt("year");
                                        BigDecimal carRentalRate = resultSet.getBigDecimal("rental_rate");
                                        String carStatus = resultSet.getString("status");
                                        System.out.println("ID: " + carId + ", Name: " + carName + ", Model: " + carModel + ", Year: " + carYear + ", Rental Rate: " + carRentalRate + ", Status: " + carStatus);
                                    } else {
                                        System.out.println("Car not found.");
                                    }
                                }
                                break;
                            case 2:
                                String readCarsSql = "SELECT * FROM Cars";
                                try (Statement readCarsStatement = connection.createStatement()) {
                                    ResultSet resultSet = readCarsStatement.executeQuery(readCarsSql);
                                    while (resultSet.next()) {
                                        int carId = resultSet.getInt("car_id");
                                        String carName = resultSet.getString("name");
                                        String carModel = resultSet.getString("model");
                                        int carYear = resultSet.getInt("year");
                                        BigDecimal carRentalRate = resultSet.getBigDecimal("rental_rate");
                                        String carStatus = resultSet.getString("status");
                                        System.out.println("ID: " + carId + ", Name: " + carName + ", Model: " + carModel + ", Year: " + carYear + ", Rental Rate: " + carRentalRate + ", Status: " + carStatus);
                                    }
                                }
                                break;
                            case 3:
                                System.out.println("Exiting to previous menu.");
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    } while (carRetrieveOption != 3);
                    break;

                case 3:
                    System.out.print("Enter car ID to update: ");
                    int carIdToUpdate = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Choose what to update:");
                    System.out.println("1. Update name");
                    System.out.println("2. Update model");
                    System.out.println("3. Update year");
                    System.out.println("4. Update rental rate");
                    System.out.println("5. Update status");
                    System.out.print("Enter your choice: ");
                    int updateChoice = sc.nextInt();
                    sc.nextLine();

                    String updateSql;
                    PreparedStatement updateStatement;

                    switch (updateChoice) {
                        case 1:
                            System.out.print("Enter new name: ");
                            String newMake = sc.nextLine();
                            updateSql = "UPDATE Cars SET name = ? WHERE car_id = ?";
                            updateStatement = connection.prepareStatement(updateSql);
                            updateStatement.setString(1, newMake);
                            updateStatement.setInt(2, carIdToUpdate);
                            break;
                        case 2:
                            System.out.print("Enter new model: ");
                            String newModel = sc.nextLine();
                            updateSql = "UPDATE Cars SET model = ? WHERE car_id = ?";
                            updateStatement = connection.prepareStatement(updateSql);
                            updateStatement.setString(1, newModel);
                            updateStatement.setInt(2, carIdToUpdate);
                            break;
                        case 3:
                            System.out.print("Enter new year: ");
                            int newYear = sc.nextInt();
                            updateSql = "UPDATE Cars SET year = ? WHERE car_id = ?";
                            updateStatement = connection.prepareStatement(updateSql);
                            updateStatement.setInt(1, newYear);
                            updateStatement.setInt(2, carIdToUpdate);
                            break;
                        case 4:
                            System.out.print("Enter new rental rate: ");
                            BigDecimal newRentalRate = sc.nextBigDecimal();
                            updateSql = "UPDATE Cars SET rental_rate = ? WHERE car_id = ?";
                            updateStatement = connection.prepareStatement(updateSql);
                            updateStatement.setBigDecimal(1, newRentalRate);
                            updateStatement.setInt(2, carIdToUpdate);
                            break;
                        case 5:
                            System.out.print("Enter new status (Available/Rented): ");
                            String newStatus = sc.nextLine();
                            updateSql = "UPDATE Cars SET status = ? WHERE car_id = ?";
                            updateStatement = connection.prepareStatement(updateSql);
                            updateStatement.setString(1, newStatus);
                            updateStatement.setInt(2, carIdToUpdate);
                            break;
                        default:
                            System.out.println("Invalid choice for update. Please try again.");
                            continue;
                    }

                    int rowsAffectedUpdate = updateStatement.executeUpdate();
                    if (rowsAffectedUpdate > 0) {
                        System.out.println("Car updated successfully.");
                    } else {
                        System.out.println("Car not found or update failed.");
                    }
                    break;

                case 4:
                    System.out.print("Enter car ID to delete: ");
                    int carIdToDelete = sc.nextInt();
                    String deleteCarSql = "DELETE FROM Cars WHERE car_id = ?";
                    try (PreparedStatement deleteCarStatement = connection.prepareStatement(deleteCarSql)) {
                        deleteCarStatement.setInt(1, carIdToDelete);
                        int rowsDeleted = deleteCarStatement.executeUpdate();
                        if (rowsDeleted > 0) {
                            System.out.println("Car deleted successfully.");
                        } else {
                            System.out.println("Car not found or delete failed.");
                        }
                    }
                    break;

                case 5:
                    // Add Customer
                    System.out.print("Enter customer first name: ");
                    String firstName = sc.nextLine();
                    System.out.print("Enter customer last name: ");
                    String lastName = sc.nextLine();
                    System.out.print("Enter customer email: ");
                    String email = sc.nextLine();
                    System.out.print("Enter customer phone: ");
                    String phone = sc.nextLine();

                    String createCustomerSql = "INSERT INTO Customers (first_name, last_name, email, phone) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement createCustomerStatement = connection.prepareStatement(createCustomerSql)) {
                        createCustomerStatement.setString(1, firstName);
                        createCustomerStatement.setString(2, lastName);
                        createCustomerStatement.setString(3, email);
                        createCustomerStatement.setString(4, phone);
                        int rowsAffectedCustomer = createCustomerStatement.executeUpdate();
                        if (rowsAffectedCustomer > 0) {
                            System.out.println("Customer added successfully.");
                        } else {
                            System.out.println("Failed to add customer.");
                        }
                    }
                    break;

                case 6:
                    int customerRetrieveOption;
                    do {
                        System.out.println("--------------------------------------------------------");
                        System.out.println("Retrieve Customers Options:");
                        System.out.println("--------------------------------------------------------");
                        System.out.println("1. Search by ID");
                        System.out.println("2. Retrieve All");
                        System.out.println("3. Exit to Previous Menu");
                        System.out.println("--------------------------------------------------------");
                        System.out.print("Please Enter an Option number: ");
                        customerRetrieveOption = sc.nextInt();
                        sc.nextLine();
                        System.out.println("--------------------------------------------------------");

                        switch (customerRetrieveOption) {
                            case 1:
                                System.out.print("Enter customer ID to search: ");
                                int customerIdToSearch = sc.nextInt();
                                String searchCustomerByIdSql = "SELECT * FROM Customers WHERE customer_id = ?";
                                try (PreparedStatement searchCustomerByIdStatement = connection.prepareStatement(searchCustomerByIdSql)) {
                                    searchCustomerByIdStatement.setInt(1, customerIdToSearch);
                                    ResultSet resultSet = searchCustomerByIdStatement.executeQuery();
                                    if (resultSet.next()) {
                                        int customerId = resultSet.getInt("customer_id");
                                        String customerFirstName = resultSet.getString("first_name");
                                        String customerLastName = resultSet.getString("last_name");
                                        String customerEmail = resultSet.getString("email");
                                        String customerPhone = resultSet.getString("phone");
                                        System.out.println("ID: " + customerId + ", Name: " + customerFirstName + " " + customerLastName + ", Email: " + customerEmail + ", Phone: " + customerPhone);
                                    } else {
                                        System.out.println("Customer not found.");
                                    }
                                }
                                break;
                            case 2:
                                String readCustomersSql = "SELECT * FROM Customers";
                                try (Statement readCustomersStatement = connection.createStatement()) {
                                    ResultSet resultSet = readCustomersStatement.executeQuery(readCustomersSql);
                                    while (resultSet.next()) {
                                        int customerId = resultSet.getInt("customer_id");
                                        String customerFirstName = resultSet.getString("first_name");
                                        String customerLastName = resultSet.getString("last_name");
                                        String customerEmail = resultSet.getString("email");
                                        String customerPhone = resultSet.getString("phone");
                                        System.out.println("ID: " + customerId + ", Name: " + customerFirstName + " " + customerLastName + ", Email: " + customerEmail + ", Phone: " + customerPhone);
                                    }
                                }
                                break;
                            case 3:
                                System.out.println("Exiting to previous menu.");
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    } while (customerRetrieveOption != 3);
                    break;

                case 7:
                    // Book Car
                    System.out.print("Enter customer ID: ");
                    int customerId = sc.nextInt();
                    sc.nextLine();  // Consume the newline

                    while (true) {
                        System.out.print("Enter car ID to book (or -1 to finish): ");
                        int carIdToBook = sc.nextInt();
                        if (carIdToBook == -1) {
                            break;
                        }

                        // Check the status of the car
                        String checkCarStatusSql = "SELECT status FROM Cars WHERE car_id = ?";
                        try (PreparedStatement checkCarStatusStatement = connection.prepareStatement(checkCarStatusSql)) {
                            checkCarStatusStatement.setInt(1, carIdToBook);
                            ResultSet statusResultSet = checkCarStatusStatement.executeQuery();
                            if (statusResultSet.next()) {
                                String carStatus = statusResultSet.getString("status");
                                if (!"Available".equalsIgnoreCase(carStatus)) {
                                    System.out.println("Car is not available. Please choose another car.");
                                    continue;
                                }
                            } else {
                                System.out.println("Car not found. Please enter a valid car ID.");
                                continue;
                            }
                        }

                        System.out.print("Enter rental date (YYYY-MM-DD): ");
                        Date rentalDate = Date.valueOf(sc.next());
                        System.out.print("Enter return date (YYYY-MM-DD): ");
                        Date returnDate = Date.valueOf(sc.next());
                        System.out.print("Enter total cost: ");
                        BigDecimal totalCost = sc.nextBigDecimal();
                        sc.nextLine();  // Consume the newline

                        // Check if the car is already booked for the requested dates
                        String checkBookingSql = "SELECT * FROM Bookings WHERE car_id = ? AND ((rental_date <= ? AND return_date >= ?) OR (rental_date <= ? AND return_date >= ?))";
                        try (PreparedStatement checkBookingStatement = connection.prepareStatement(checkBookingSql)) {
                            checkBookingStatement.setInt(1, carIdToBook);
                            checkBookingStatement.setDate(2, rentalDate);
                            checkBookingStatement.setDate(3, rentalDate);
                            checkBookingStatement.setDate(4, returnDate);
                            checkBookingStatement.setDate(5, returnDate);
                            ResultSet resultSet = checkBookingStatement.executeQuery();

                            if (resultSet.next()) {
                                System.out.println("Car is already booked for the selected dates. Please choose another car or date.");
                                continue;
                            }
                        }

                        String createBookingSql = "INSERT INTO Bookings (car_id, customer_id, rental_date, return_date, total_cost) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement createBookingStatement = connection.prepareStatement(createBookingSql)) {
                            createBookingStatement.setInt(1, carIdToBook);
                            createBookingStatement.setInt(2, customerId);
                            createBookingStatement.setDate(3, rentalDate);
                            createBookingStatement.setDate(4, returnDate);
                            createBookingStatement.setBigDecimal(5, totalCost);
                            int rowsAffectedBooking = createBookingStatement.executeUpdate();
                            if (rowsAffectedBooking > 0) {
                                System.out.println("Booking created successfully.");
                            } else {
                                System.out.println("Failed to create booking.");
                            }
                        }
                    }
                    break;



                case 8:
                    int bookingRetrieveOption;
                    do {
                        System.out.println("--------------------------------------------------------");
                        System.out.println("Retrieve Bookings Options:");
                        System.out.println("--------------------------------------------------------");
                        System.out.println("1. Search by ID");
                        System.out.println("2. Retrieve All");
                        System.out.println("3. Exit to Previous Menu");
                        System.out.println("--------------------------------------------------------");
                        System.out.print("Please Enter an Option number: ");
                        bookingRetrieveOption = sc.nextInt();
                        sc.nextLine();
                        System.out.println("--------------------------------------------------------");

                        switch (bookingRetrieveOption) {
                            case 1:
                                System.out.print("Enter booking ID to search: ");
                                int bookingIdToSearch = sc.nextInt();
                                String searchBookingByIdSql = "SELECT * FROM Bookings WHERE booking_id = ?";
                                try (PreparedStatement searchBookingByIdStatement = connection.prepareStatement(searchBookingByIdSql)) {
                                    searchBookingByIdStatement.setInt(1, bookingIdToSearch);
                                    ResultSet resultSet = searchBookingByIdStatement.executeQuery();
                                    if (resultSet.next()) {
                                        int bookingId = resultSet.getInt("booking_id");
                                        int bookedCarId = resultSet.getInt("car_id");
                                        int bookingCustomerId = resultSet.getInt("customer_id");
                                        Date bookingRentalDate = resultSet.getDate("rental_date");
                                        Date bookingReturnDate = resultSet.getDate("return_date");
                                        BigDecimal bookingTotalCost = resultSet.getBigDecimal("total_cost");
                                        System.out.println("Booking ID: " + bookingId + ", Car ID: " + bookedCarId + ", Customer ID: " + bookingCustomerId + ", Rental Date: " + bookingRentalDate + ", Return Date: " + bookingReturnDate + ", Total Cost: " + bookingTotalCost);
                                    } else {
                                        System.out.println("Booking not found.");
                                    }
                                }
                                break;
                            case 2:
                                String readBookingsSql = "SELECT * FROM Bookings";
                                try (Statement readBookingsStatement = connection.createStatement()) {
                                    ResultSet resultSet = readBookingsStatement.executeQuery(readBookingsSql);
                                    while (resultSet.next()) {
                                        int bookingId = resultSet.getInt("booking_id");
                                        int bookedCarId = resultSet.getInt("car_id");
                                        int bookingCustomerId = resultSet.getInt("customer_id");
                                        Date bookingRentalDate = resultSet.getDate("rental_date");
                                        Date bookingReturnDate = resultSet.getDate("return_date");
                                        BigDecimal bookingTotalCost = resultSet.getBigDecimal("total_cost");
                                        System.out.println("Booking ID: " + bookingId + ", Car ID: " + bookedCarId + ", Customer ID: " + bookingCustomerId + ", Rental Date: " + bookingRentalDate + ", Return Date: " + bookingReturnDate + ", Total Cost: " + bookingTotalCost);
                                    }
                                }
                                break;
                            case 3:
                                System.out.println("Exiting to previous menu.");
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    } while (bookingRetrieveOption != 3);
                    break;

                case 9:
                    System.out.println("Exiting program.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (operation != 9);

        sc.close();
        stmt.close();
        connection.close();
    }
}
