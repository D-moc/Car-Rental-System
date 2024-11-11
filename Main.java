import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.LineBorder;

class Car {
    private String carRegistrationNumber;
    private String brand;
    private String model;
    private String type;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carRegistrationNumber, String brand, String model, String type, double basePricePerDay) 
    {
        this.carRegistrationNumber = carRegistrationNumber;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarRegistrationNumber() {
        return carRegistrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

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




// GUI CODE

class CarRentalSystemGUI {
    private List<Car> cars = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();

    public CarRentalSystemGUI() {
        
        cars.add(new Car("KABC000001", "Hyundai", "Verna", "Sedan", 3500.0));
        cars.add(new Car("GJ00000001", "Audi", "A6", "Sedan", 7000.0));
        cars.add(new Car("MH00000001", "Mercedes", "E-Class", "Sedan", 10000.0));

        cars.add(new Car("TN00000002", "Ford", "EcoSport", "SUV", 4500.0));
        cars.add(new Car("UP00000002", "BMW", "X5", "SUV", 8000.0));
        cars.add(new Car("DL00000002", "Mahindra", "Thar", "SUV", 5000.0));
        
        cars.add(new Car("MH00000003", "Maruti", "Swift", "Hatchback", 2500.0));
        cars.add(new Car("DL00000003", "Suzuki", "Baleno", "Hatchback", 2800.0));
        cars.add(new Car("MH00000003", "Honda", "City", "Hatchback", 3000.0));

        setupGUI();
    }

    private void setupGUI() {
        JFrame frame = new JFrame("Car Rental System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Car Rental System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 70));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton rentButton = new JButton("Rent a Car");
        JButton returnButton = new JButton("Return a Car");
        JButton exitButton = new JButton("Exit");

        rentButton.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        returnButton.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        exitButton.setFont(new Font("Times New Roman", Font.PLAIN, 50));

        buttonPanel.add(rentButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

        rentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openRentCarDialog();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openReturnCarDialog();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }




    //RENT  BUTTON FUNCTION

    private void openRentCarDialog() {
        JFrame rentFrame = new JFrame("USER DETAILS");
        rentFrame.setSize(400, 300);
        rentFrame.setLayout(new GridLayout(8, 2));  
        Font labelFont = new Font("Times New Roman", Font.BOLD, 40);
        Font fieldFont = new Font("Times New Roman", Font.BOLD, 40);
    
        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setFont(labelFont);
    
        JTextField nameField = new JTextField();
        nameField.setFont(fieldFont);
        nameField.setBorder(new LineBorder(Color.BLACK, 4, true));
    
        JLabel typeLabel = new JLabel("Choose car type (Hatchback, Sedan, SUV):");
        typeLabel.setFont(labelFont);
    
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Hatchback", "Sedan", "SUV"});
        typeComboBox.setFont(fieldFont);
    
        JLabel availableCarsLabel = new JLabel("Available Cars:");
        availableCarsLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
    

        // Dropdown for selecting a car
        JComboBox<String> carComboBox = new JComboBox<>();
        carComboBox.setFont(fieldFont);
    
        JLabel daysLabel = new JLabel("Enter rental days:");
        daysLabel.setFont(labelFont);
    
        JTextField daysField = new JTextField();
        daysField.setFont(fieldFont);
        daysField.setBorder(new LineBorder(Color.BLACK, 4, true));
    
        JButton rentButton = new JButton("Rent");
        rentButton.setFont(new Font("Times New Roman", Font.BOLD, 50));
    
        rentFrame.add(nameLabel);
        rentFrame.add(nameField);
        rentFrame.add(typeLabel);
        rentFrame.add(typeComboBox);
        rentFrame.add(availableCarsLabel);
        rentFrame.add(carComboBox);
        rentFrame.add(daysLabel);
        rentFrame.add(daysField);
        rentFrame.add(new JLabel());
        rentFrame.add(rentButton);
    
       
        typeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) typeComboBox.getSelectedItem();
                carComboBox.removeAllItems();
                for (Car car : cars) {
                    if (car.getType().equalsIgnoreCase(selectedType) && car.isAvailable()) {
                        carComboBox.addItem(car.getCarRegistrationNumber());
                    }
                }
            }
        });
    
        rentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String selectedCarRegNum = (String) carComboBox.getSelectedItem();
                int days = Integer.parseInt(daysField.getText());
    
                Customer customer = new Customer("CUS" + (customers.size() + 1), name);
                customers.add(customer);
    
                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarRegistrationNumber().equalsIgnoreCase(selectedCarRegNum) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
    
                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(days);
                    int response = JOptionPane.showConfirmDialog(rentFrame, "Total price: ₹" + totalPrice + "\nDo you want to proceed?", 
                    "Confirm Payment", 
                    JOptionPane.YES_NO_OPTION);
    
                    if (response == JOptionPane.YES_OPTION) {
                        selectedCar.rent();
                        rentals.add(new Rental(selectedCar, customer, days));
                        JOptionPane.showMessageDialog(rentFrame, "Car rented successfully.");
                    } else {
                        JOptionPane.showMessageDialog(rentFrame, "Rental cancelled.");
                    }
                } else {
                    JOptionPane.showMessageDialog(rentFrame, "No available cars selected.");
                }
            }
        });
    

        typeComboBox.setSelectedIndex(0);         // default car select
        typeComboBox.getActionListeners()[0].actionPerformed(new ActionEvent(typeComboBox, ActionEvent.ACTION_PERFORMED, ""));
        
        rentFrame.setVisible(true);
    }
    




    //RETURN BUTTON FUNCTION

    private void openReturnCarDialog() {
        JFrame returnFrame = new JFrame("Return a Car");
        returnFrame.setSize(400, 300);
        returnFrame.setLayout(new GridLayout(3,0));
    
        
        Font labelFont = new Font("Times New Roman", Font.BOLD, 50);  
        Font fieldFont = new Font("Times New Roman", Font.BOLD, 50);
    
        
        JLabel regNumLabel = new JLabel("Enter car registration number:");
        regNumLabel.setFont(labelFont);
    
        JTextField regNumField = new JTextField();
        regNumField.setFont(fieldFont);  
        regNumField.setPreferredSize(new Dimension(300, 100));  
        regNumField.setBorder(new LineBorder(Color.BLACK, 4, true));
    
        JButton returnButton = new JButton("Return");
        returnButton.setFont(new Font("Times New Roman", Font.BOLD, 50));
    

        returnFrame.add(regNumLabel);
        returnFrame.add(regNumField);
        returnFrame.add(new JLabel());  
        returnFrame.add(returnButton);
    
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String regNum = regNumField.getText().trim();
    
                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarRegistrationNumber().equalsIgnoreCase(regNum) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }
    
                if (carToReturn != null) 
                {
                    carToReturn.returnCar();
                    removeRentalByCar(carToReturn);
    
                    String feedback = JOptionPane.showInputDialog(returnFrame, "Enter feedback for the car:");
                    JOptionPane.showMessageDialog(returnFrame, "Car returned successfully. Feedback: " + feedback);
                } else {
                    JOptionPane.showMessageDialog(returnFrame, "Car not rented or invalid registration number.");
                }
            }
        });
    
        returnFrame.setVisible(true);
    }
    

    private void removeRentalByCar(Car car) {
        rentals.removeIf(rental -> rental.getCar().equals(car));
    }


    public static void main(String[] args) {
        new CarRentalSystemGUI();
    }
}
