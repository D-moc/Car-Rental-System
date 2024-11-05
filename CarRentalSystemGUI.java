import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarRentalSystemGUI extends JFrame {
    private CarRentalSystem rentalSystem;
    private JTextField nameField, regNumberField, daysField;
    private JComboBox<String> carTypeComboBox;
    private JTextArea messageBox;

    public CarRentalSystemGUI() {
        rentalSystem = new CarRentalSystem();
        rentalSystem.addCar(new Hatchback("H123", "Toyota", "Yaris", 300.0));
        rentalSystem.addCar(new Sedan("S456", "Honda", "Civic", 500.0));
        rentalSystem.addCar(new SUV("U789", "Ford", "Explorer", 700.0));

        setTitle("Car Rental System");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JLabel welcomeLabel = new JLabel("Welcome to Car Rental System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 25));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Car Registration Number:"));
        regNumberField = new JTextField();
        inputPanel.add(regNumberField);

        inputPanel.add(new JLabel("Rental Days:"));
        daysField = new JTextField();
        inputPanel.add(daysField);

        inputPanel.add(new JLabel("Car Type:"));
        carTypeComboBox = new JComboBox<>(new String[]{"Hatchback", "Sedan", "SUV"});
        inputPanel.add(carTypeComboBox);

        
        messageBox = new JTextArea();
        messageBox.setEditable(false);
        messageBox.setPreferredSize(new Dimension(350, 80)); 
        messageBox.setLineWrap(true);
        messageBox.setWrapStyleWord(true);
        messageBox.setBorder(BorderFactory.createTitledBorder("Message")); 


        messageBox.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(messageBox); 

        add(inputPanel, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton rentButton = new JButton("Rent Car");
        rentButton.addActionListener(new RentCarAction());
        buttonPanel.add(rentButton);

        JButton returnButton = new JButton("Return Car");
        returnButton.addActionListener(new ReturnCarAction());
        buttonPanel.add(returnButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class RentCarAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String customerName = nameField.getText();
            String carRegNumber = regNumberField.getText();
            int days = Integer.parseInt(daysField.getText());
            String carType = (String) carTypeComboBox.getSelectedItem();

            try {
                rentalSystem.displayCarsByType(carType);
                Car selectedCar = rentalSystem.getCars().stream()
                        .filter(car -> car.getCarRegNumber().equalsIgnoreCase(carRegNumber))
                        .findFirst()
                        .orElseThrow(() -> new Exception("Car not found."));

                Customer customer = new Customer(String.valueOf(rentalSystem.getCustomers().size() + 1), customerName);
                rentalSystem.addCustomer(customer);
                rentalSystem.rentCar(selectedCar, customer, days);

                // Display success message in the message box
                messageBox.setText("Car rented successfully: " + selectedCar.getCarRegNumber());
            } catch (Exception ex) {
                // Display error message in the message box
                messageBox.setText("Error: " + ex.getMessage());
            }
        }
    }

    private class ReturnCarAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String carRegNumber = regNumberField.getText();

            try {
                Car car = rentalSystem.getCars().stream()
                        .filter(c -> c.getCarRegNumber().equalsIgnoreCase(carRegNumber))
                        .findFirst()
                        .orElseThrow(() -> new Exception("Car not found."));
                rentalSystem.returnCar(car);
                // Display success message in the message box
                messageBox.setText("Car returned successfully: " + car.getCarRegNumber());
            } catch (Exception ex) {
                // Display error message in the message box
                messageBox.setText("Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CarRentalSystemGUI gui = new CarRentalSystemGUI();
            gui.setVisible(true);
        });
    }
}
