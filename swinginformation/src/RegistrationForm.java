import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RegistrationForm extends JFrame {
    private final JTextField idField;
    private final JTextField nameField;
    private final JTextField dobField;
    private final JTextField mobileField;
    private final JTextField addressField;
    private final JRadioButton newUserRadio;
    private final JRadioButton existingUserRadio;
    private final JComboBox<String> existingUsersCombo;
    private final JRadioButton maleRadio;
    private final JRadioButton femaleRadio;
    private final JRadioButton otherRadio;
    private final JCheckBox termsAndConditionsCheckBox;
    private final ButtonGroup genderGroup;

    private String selectedUserId = null; // Store the ID of the selected user

    public RegistrationForm() {
        setTitle("Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel idLabel = new JLabel("Identity Number:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(idLabel, gbc);

        idField = new JTextField();
        idField.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPane.add(idField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(nameLabel, gbc);

        nameField = new JTextField();
        nameField.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPane.add(nameField, gbc);

        JLabel dobLabel = new JLabel("Date of Birth:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(dobLabel, gbc);

        dobField = new JTextField();
        dobField.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPane.add(dobField, gbc);

        JLabel mobileLabel = new JLabel("Mobile Number:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(mobileLabel, gbc);

        mobileField = new JTextField();
        mobileField.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        contentPane.add(mobileField, gbc);

        JLabel addressLabel = new JLabel("Address:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPane.add(addressLabel, gbc);

        addressField = new JTextField();
        addressField.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        contentPane.add(addressField, gbc);

        JLabel genderLabel = new JLabel("Gender:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPane.add(genderLabel, gbc);

        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        otherRadio = new JRadioButton("Other");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);
        gbc.gridx = 1;
        gbc.gridy = 5;
        contentPane.add(maleRadio, gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        contentPane.add(femaleRadio, gbc);
        gbc.gridx = 3;
        gbc.gridy = 5;
        contentPane.add(otherRadio, gbc);

        newUserRadio = new JRadioButton("New User");
        existingUserRadio = new JRadioButton("Existing User");
        ButtonGroup group = new ButtonGroup();
        group.add(newUserRadio);
        group.add(existingUserRadio);
        gbc.gridx = 0;
        gbc.gridy = 8;
        contentPane.add(newUserRadio, gbc);
        gbc.gridx = 1;
        gbc.gridy = 8;
        contentPane.add(existingUserRadio, gbc);

        existingUsersCombo = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 9;
        contentPane.add(existingUsersCombo, gbc);

        termsAndConditionsCheckBox = new JCheckBox("I agree to the terms and conditions");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2; // Span two columns for the checkbox
        contentPane.add(termsAndConditionsCheckBox, gbc);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> registerUser());
        gbc.gridx = 1;
        gbc.gridy = 11;
        contentPane.add(registerButton, gbc);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetForm());
        gbc.gridx = 0;
        gbc.gridy = 11;
        contentPane.add(resetButton, gbc);

        JButton viewDetailsButton = new JButton("View User Details");
        viewDetailsButton.addActionListener(e -> displayUserDetails());
        gbc.gridx = 1;
        gbc.gridy = 12;
        contentPane.add(viewDetailsButton, gbc);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);   });
        gbc.gridx = 2; 
        gbc.gridy = 11;
        contentPane.add(exitButton, gbc);

        createDatabaseTable();
        populateExistingUsers();
    }

    private void registerUser() {
        if (!termsAndConditionsCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please accept the terms and conditions.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = idField.getText();
        String name = nameField.getText();
        String dob = dobField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();
        String gender;
        if (maleRadio.isSelected()) {
            gender = "Male";
        } else if (femaleRadio.isSelected()) {
            gender = "Female";
        } else {
            gender = "Other";
        }
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_system", "root", "4228")) {
            if (newUserRadio.isSelected()) {
                String sql = "INSERT INTO users (id, name, dob, mobile, address, gender) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, id);
                stmt.setString(2, name);
                stmt.setString(3, dob);
                stmt.setString(4, mobile);
                stmt.setString(5, address);
                stmt.setString(6, gender);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                // Update the existing users combo box after registration
                populateExistingUsers(); 
            } else if (existingUserRadio.isSelected()) {

                String selectedUser = (String) existingUsersCombo.getSelectedItem();
                String[] parts = selectedUser.split(" - ");
                String existingId = parts[0];
                String sql = "UPDATE users SET name = ?, dob = ?, mobile = ?, address = ?, gender = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, dob);
                stmt.setString(3, mobile);
                stmt.setString(4, address);
                stmt.setString(5, gender);
                stmt.setString(6, existingId);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "User updated successfully!");
                // Update the existing users combo box after update
                populateExistingUsers(); 
            } else {
                JOptionPane.showMessageDialog(this, "Please select an option: New User or Existing User.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error registering user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        idField.setText("");
        nameField.setText("");
        dobField.setText("");
        mobileField.setText("");
        addressField.setText("");
        genderGroup.clearSelection(); // Clear gender selection
        newUserRadio.setSelected(true); // Default to New User
        existingUsersCombo.setSelectedIndex(0); // Reset combo box
        termsAndConditionsCheckBox.setSelected(false); // Uncheck terms and conditions
    }

    private void populateExistingUsers() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_system", "root", "4228")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM users");
            existingUsersCombo.removeAllItems(); // Clear existing items
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                existingUsersCombo.addItem(id + " - " + name);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading existing users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Add an item listener to the combo box to capture user selection
        existingUsersCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) existingUsersCombo.getSelectedItem();
                    selectedUserId = selectedItem.split(" - ")[0]; // Extract ID from selected item
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_system", "sosten", "4228")) {
                    conn.createStatement().execute("CREATE TABLE IF NOT EXISTS users (id VARCHAR(255), name VARCHAR(255), dob VARCHAR(255), mobile VARCHAR(255), address VARCHAR(255), gender VARCHAR(255))");
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace(); // Print stack trace for debugging
            }
            
            RegistrationForm frame = new RegistrationForm();
            frame.setVisible(true);
        });
    }

    private void createDatabaseTable() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_system", "root", "4228")) {
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS users (id VARCHAR(255), name VARCHAR(255), dob VARCHAR(255), mobile VARCHAR(255), address VARCHAR(255), gender VARCHAR(255))");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error creating database table: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayUserDetails() {
        if (selectedUserId == null) {
            JOptionPane.showMessageDialog(this, "Please select a user from the list.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame detailsFrame = new JFrame("User Details");
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailsFrame.setBounds(100, 100, 500, 300);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        detailsFrame.setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(6, 2, 10, 10)); // 6 rows, 2 columns

        JLabel idLabel = new JLabel("ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel dobLabel = new JLabel("Date of Birth:");
        JLabel mobileLabel = new JLabel("Mobile Number:");
        JLabel addressLabel = new JLabel("Address:");
        JLabel genderLabel = new JLabel("Gender:");

        JLabel idValue = new JLabel();
        JLabel nameValue = new JLabel();
        JLabel dobValue = new JLabel();
        JLabel mobileValue = new JLabel();
        JLabel addressValue = new JLabel();
        JLabel genderValue = new JLabel();

        contentPane.add(idLabel);
        contentPane.add(idValue);
        contentPane.add(nameLabel);
        contentPane.add(nameValue);
        contentPane.add(dobLabel);
        contentPane.add(dobValue);
        contentPane.add(mobileLabel);
        contentPane.add(mobileValue);
        contentPane.add(addressLabel);
        contentPane.add(addressValue);
        contentPane.add(genderLabel);
        contentPane.add(genderValue);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_system", "root", "4228")) {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, selectedUserId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idValue.setText(rs.getString("id"));
                nameValue.setText(rs.getString("name"));
                dobValue.setText(rs.getString("dob"));
                mobileValue.setText(rs.getString("mobile"));
                addressValue.setText(rs.getString("address"));
                genderValue.setText(rs.getString("gender"));
            } else {
                JOptionPane.showMessageDialog(detailsFrame, "No user data found for the selected ID.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(detailsFrame, "Error loading user details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        detailsFrame.setVisible(true);
    }
}
