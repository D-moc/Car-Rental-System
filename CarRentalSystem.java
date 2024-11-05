import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Interface to define rental behavior
interface Rentable {
    void rent();
    void returnItem();
    boolean isAvailable();
    double calculatePrice(int rentalDays);
}

// Abstract class Car that implements Rentable
abstract class Car implements Rentable {
    private String carRegNumber;
    private String brand;
    private String model;
    protected double basePricePerDay;
    private boolean isAvailable;
    private int rentedDays; // Added rentedDays field

    public Car(String carRegNumber, String brand, String model, double basePricePerDay) {
        this.carRegNumber = carRegNumber;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarRegNumber() {
        return carRegNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getRentedDays() {
        return rentedDays;
    }

    public void setRentedDays(int rentedDays) {
        this.rentedDays = rentedDays;
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void rent() {
        isAvailable = false;
    }

    @Override
    public void returnItem() {
        isAvailable = true;
    }

    @Override
    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public void displayCarDetails() {
        System.out.println(carRegNumber + " - " + brand + " " + model + " (Base Price: INR " + basePricePerDay + "/day)");
    }

    public void saveToFile(String customerName, int rentalDays) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("rental_records.txt", true))) {
            writer.write("Customer: " + customerName + ", Car: " + brand + " " + model + ", Reg. No: " + carRegNumber);
            writer.write(", Days: " + rentalDays + ", Total Price: INR " + calculatePrice(rentalDays));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving rental information: " + e.getMessage());
        }
    }
}

// Specific Car Types
class Hatchback extends Car {
    public Hatchback(String carRegNumber, String brand, String model, double basePricePerDay) {
        super(carRegNumber, brand, model, basePricePerDay);
    }
}

class Sedan extends Car {
    public Sedan(String carRegNumber, String brand, String model, double basePricePerDay) {
        super(carRegNumber, brand, model, basePricePerDay);
    }
}

class SUV extends Car {
    public SUV(String carRegNumber, String brand, String model, double basePricePerDay) {
        super(carRegNumber, brand, model, basePricePerDay);
    }
}

// Customer class with encapsulation
class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

// Rental class to manage car rentals
class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

// Payment class to handle payment processing
class Payment {
    private double amount;
    private boolean isPaid;

    public Payment(double amount) {
        this.amount = amount;
        this.isPaid = false;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void makePayment() {
        System.out.println("Processing payment of INR " + amount + "...");
        this.isPaid = true;  // Simulate successful payment
        System.out.println("Payment successful. Amount paid: INR " + amount);
    }
}

// Main CarRentalSystem class
class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void rentCar(Car car, Customer customer, int days) throws Exception {
        if (car.isAvailable()) {
            double totalPrice = car.calculatePrice(days);
            System.out.println("Total price to pay for " + days + " days: INR " + totalPrice);
            
            Payment payment = new Payment(totalPrice);
            payment.makePayment();
            
            if (payment.isPaid()) {
                car.rent();
                car.setRentedDays(days); // Store the rental days in the car
                rentals.add(new Rental(car, customer, days));
                car.saveToFile(customer.getName(), days);
                System.out.println("Car rented successfully. Payment completed.");
            } else {
                System.out.println("Payment failed. Car rental unsuccessful.");
            }
        } else {
            throw new Exception("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) throws Exception {
        if (!car.isAvailable()) {
            car.returnItem();
            System.out.println("Car returned successfully. Total amount paid was INR " + car.calculatePrice(car.getRentedDays()) + " for " + car.getRentedDays() + " days.");
            Rental rentalToRemove = null;
            for (Rental rental : rentals) {
                if (rental.getCar() == car) {
                    rentalToRemove = rental;
                    break;
                }
            }
            if (rentalToRemove != null) {
                rentals.remove(rentalToRemove);
            } else {
                throw new Exception("Car was not rented.");
            }
        } else {
            throw new Exception("Car is already available.");
        }
    }

    public void displayCarsByType(String type) {
        for (Car car : cars) {
            if (car.getClass().getSimpleName().equalsIgnoreCase(type) && car.isAvailable()) {
                car.displayCarDetails();
            }
        }
    }

    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        rentalSystem.addCar(new Hatchback("H123", "Toyota", "Yaris", 300.0));
        rentalSystem.addCar(new Sedan("S456", "Honda", "Civic", 500.0));
        rentalSystem.addCar(new SUV("U789", "Ford", "Explorer", 700.0));

        rentalSystem.menu();
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("===== Car Rental System =====");
                System.out.println("1. Rent a Car");
                System.out.println("2. Return a Car");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        handleRent(scanner);
                        break;
                    case 2:
                        handleReturn(scanner);
                        break;
                    case 3:
                        System.out.println("Thank you for using the Car Rental System!");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void handleRent(Scanner scanner) throws Exception {
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();

        System.out.println("Select car type to rent:");
        System.out.println("1. Hatchback");
        System.out.println("2. Sedan");
        System.out.println("3. SUV");
        System.out.print("Enter your choice: ");
        int carTypeChoice = scanner.nextInt();
        scanner.nextLine();

        String carType = switch (carTypeChoice) {
            case 1 -> "Hatchback";
            case 2 -> "Sedan";
            case 3 -> "SUV";
            default -> throw new IllegalArgumentException("Invalid car type choice.");
        };

        System.out.println("Available " + carType + " cars:");
        displayCarsByType(carType);

        System.out.print("Enter car registration number to rent: ");
        String carRegNumber = scanner.nextLine();

        Car selectedCar = cars.stream().filter(car -> car.getCarRegNumber().equalsIgnoreCase(carRegNumber)).findFirst().orElse(null);

        if (selectedCar == null || !selectedCar.isAvailable()) {
            System.out.println("Car is not available.");
            return;
        }

        System.out.print("Enter rental days: ");
        int days = scanner.nextInt();

        Customer customer = new Customer("C" + (customers.size() + 1), customerName);
        addCustomer(customer);

        rentCar(selectedCar, customer, days);
    }

    private void handleReturn(Scanner scanner) throws Exception {
        System.out.print("Enter car registration number to return: ");
        String carRegNumber = scanner.nextLine();

        Car carToReturn = cars.stream().filter(car -> car.getCarRegNumber().equalsIgnoreCase(carRegNumber)).findFirst().orElse(null);

        if (carToReturn == null) {
            System.out.println("Invalid car registration number.");
            return;
        }

        returnCar(carToReturn);
    }
}
