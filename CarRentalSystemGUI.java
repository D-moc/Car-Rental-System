import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarRentalSystemGUI {
    private CarRentalSystem rentalSystem;
    private JFrame frame;

    public CarRentalSystemGUI() {
        rentalSystem = new CarRentalSystem();
        initializeCars(); // Initialize cars on GUI start
        createAndShowGUI();
    }

    private void initializeCars() {
        rentalSystem.addCar(new Hatchback("H123", "Toyota", "Yaris", 300.0));
        rentalSystem.addCar(new Sedan("S456", "Honda", "Civic", 500.0));
        rentalSystem.addCar(new SUV("U789", "Ford", "Explorer", 700.0));
    }

    private void createAndShowGUI() {
        frame = new JFrame("Car Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome to Car Rental System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton rentButton = new JButton("Rent Car");
        rentButton.setFont(new Font("Arial", Font.BOLD, 25));
        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRent();
            }
        });
        buttonPanel.add(rentButton);

        JButton returnButton = new JButton("Return Car");
        returnButton.setFont(new Font("Arial", Font.BOLD, 25));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReturn();
            }
        });
        buttonPanel.add(returnButton);

        JButton paymentButton = new JButton("Payment");
        paymentButton.setFont(new Font("Arial", Font.BOLD, 25));
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePayment();
            }
        });
        buttonPanel.add(paymentButton);

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void handleRent() {
        String customerName = JOptionPane.showInputDialog(null, "Enter your name:", "Rent Car", JOptionPane.QUESTION_MESSAGE);
        if (customerName == null) return;

        String[] options = {"Hatchback", "Sedan", "SUV"};
        String carType = (String) JOptionPane.showInputDialog(null, "Select car type to rent:", "Car Type",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (carType == null) return;

        String carRegNumber = JOptionPane.showInputDialog(null, "Enter the car registration number:", "Rent Car", JOptionPane.QUESTION_MESSAGE);
        if (carRegNumber == null) return;

        String rentalDaysStr = JOptionPane.showInputDialog(null, "Enter the number of rental days:", "Rent Car", JOptionPane.QUESTION_MESSAGE);
        if (rentalDaysStr == null) return;

        int rentalDays = Integer.parseInt(rentalDaysStr);

        // Find the car and calculate cost
        Car selectedCar = rentalSystem.getCars().stream()
                .filter(car -> car.getCarRegNumber().equals(carRegNumber) && car.isAvailable())
                .findFirst()
                .orElse(null);

        if (selectedCar == null) {
            JOptionPane.showMessageDialog(null, "Car not available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double totalCost = selectedCar.getRentalRate() * rentalDays;
        JOptionPane.showMessageDialog(null, "Total rental cost: ₹" + totalCost, "Rent Car", JOptionPane.INFORMATION_MESSAGE);

        Customer customer = new Customer("CUS" + (rentalSystem.getCustomers().size() + 1), customerName);
        rentalSystem.addCustomer(customer);
        
        rentalSystem.rentCar(selectedCar, customer, rentalDays);
    }

    private void handleReturn() {
        String carRegNumber = JOptionPane.showInputDialog(null, "Enter the car registration number to return:", "Return Car", JOptionPane.QUESTION_MESSAGE);
        if (carRegNumber == null) return;

        try {
            Car carToReturn = rentalSystem.getCars().stream()
                    .filter(car -> car.getCarRegNumber().equals(carRegNumber) && !car.isAvailable())
                    .findFirst()
                    .orElseThrow(() -> new Exception("Car is not rented or invalid registration number."));
            
            double rentalCost = carToReturn.getRentalRate() * carToReturn.getRentedDays();
            rentalSystem.returnCar(carToReturn);

            JOptionPane.showMessageDialog(null, "Car returned successfully! Amount due: ₹" + rentalCost, "Return Car", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handlePayment() {
        String amountStr = JOptionPane.showInputDialog(null, "Enter payment amount:", "Payment", JOptionPane.QUESTION_MESSAGE);
        if (amountStr == null) return;

        try {
            double amount = Double.parseDouble(amountStr);
            JOptionPane.showMessageDialog(null, "Payment of ₹" + amount + " confirmed! Thank you for using our service.", "Payment Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarRentalSystemGUI());
    }
}
