import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

 class RegistrationForm extends JFrame {

    private JTextField firstNameField, lastNameField, emailField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private JButton registerButton, viewAllButton, updateButton, deleteButton ;
   private JTextField rollNoField;

    public RegistrationForm() {
        setTitle("Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(35, 100, 98));
        panel.setLayout(null);
        add(panel);

        JLabel titleLabel = new JLabel("Registration Form");
        titleLabel.setForeground(new Color(235, 208, 206));
        titleLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBounds(100, 10, 200, 30);
        panel.add(titleLabel);

        // First Name
        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setForeground(new Color(149, 151, 166));
        firstNameLabel.setBounds(20, 50, 100, 30);
        panel.add(firstNameLabel);

        firstNameField = new JTextField();
        firstNameField.setBounds(20, 75, 150, 30);
        panel.add(firstNameField);

        // Last Name
        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setForeground(new Color(149, 151, 166));
        lastNameLabel.setBounds(200, 50, 100, 20);
        panel.add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(200, 75, 150, 30);
        panel.add(lastNameField);

        // Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(new Color(149, 151, 166));
        emailLabel.setBounds(20, 110, 100, 20);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(20, 135, 330, 30);
        panel.add(emailField);

        // Gender
        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setForeground(new Color(149, 151, 166));
        genderLabel.setBounds(20, 170, 100, 20);
        panel.add(genderLabel);

        JLabel rollNoLabel = new JLabel("Roll No");
        rollNoLabel.setForeground(new Color(149, 151, 166));
        rollNoLabel.setBounds(20, 320, 100, 30);
        panel.add(rollNoLabel);

        rollNoField = new JTextField();
        rollNoField.setBounds(20, 355, 150, 30);
        panel.add(rollNoField);

        maleRadioButton = new JRadioButton("Male");
        maleRadioButton.setBackground(Color.yellow);
        femaleRadioButton = new JRadioButton("Female");
        femaleRadioButton.setBackground(Color.yellow);
        maleRadioButton.setBounds(100, 185, 70, 20);
        femaleRadioButton.setBounds(200, 185, 80, 20);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        panel.add(maleRadioButton);
        panel.add(femaleRadioButton);

        // Register Button
        registerButton = new JButton("Register Now");
        registerButton.setBounds(20, 230, 150, 30);
        panel.add(registerButton);

        // View All Button
        viewAllButton = new JButton("View All");
        viewAllButton.setBounds(190, 230, 150, 30);
        panel.add(viewAllButton);

        // Update Button
        updateButton = new JButton("Update");
        updateButton.setBounds(20, 280, 150, 30);
        panel.add(updateButton);

        // Delete Button
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(190, 280, 150, 30);
        panel.add(deleteButton);

        // Action Listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                register();
            }
        });

        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAll();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });

        setVisible(true);
    }



     private void register() {
        // Perform registration operation and save data to the database
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String gender = maleRadioButton.isSelected() ? "Male" : "Female";
        int roll_no = Integer.parseInt(rollNoField.getText());

         // Perform database insertion here
         try {
             Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university", "root", "password");
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM university.registrationform");
             ResultSet rs = ps.executeQuery();

             ps = connection.prepareStatement("INSERT INTO university.registrationform VALUES(?,?,?,?,?);");
             ps.setString(1,firstName);
             ps.setString(2,lastName);
             ps.setString(3,email);
             ps.setString(4,gender);
             ps.setInt(5,roll_no);

             ps.execute();
             JOptionPane.showMessageDialog(this, "Registration Successful!");
             connection.close();
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
         }
     }

    private void viewAll() {
        // Retrieve all records from the database and display them in the console
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university", "root", "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM registrationForm");

            System.out.println("All Records:");
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                int roll_no = resultSet.getInt("roll_no");

                System.out.println("Name: " + firstName + " " + lastName + ", Email: " + email + ", Gender: " + gender + ", Roll No: " + roll_no);
            }
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }


    private void update() {
        // Perform update operation on a selected record
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String gender = maleRadioButton.isSelected() ? "Male" : "Female";
        int rollno = Integer.parseInt(rollNoField.getText());


        // You can modify this query based on your database schema
        String updateQuery = "UPDATE registrationForm SET first_name=?, last_name=?, email=?, gender=? WHERE roll_no=?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university", "root", "password");
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, gender);
            preparedStatement.setInt(5, rollno);

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Record updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No record found with the specified roll number.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    private void delete() {
        // Perform delete operation on a selected record
        String rollno = rollNoField.getText();

        // You can modify this query based on your database schema
        String deleteQuery = "DELETE FROM registrationForm WHERE roll_no=?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university", "root", "password");
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            // Set the roll number parameter for the prepared statement
            preparedStatement.setString(1, rollno);

            // Execute the delete query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Record deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No record found with the specified roll number.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {

        RegistrationForm form = new RegistrationForm();
}
}