package bankapplication;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
public class GUIBank {
    static String file = "D:\\JavaFile\\BankApllication\\src\\account.csv";
    static String currentName = "";
    static String currentAcc = "";
    static double currentBalance = 0.0;
    public static void launchGUI() {
        JFrame frame1 = new JFrame("Anush Phaiju Bank Portal");
        frame1.setSize(350, 200);
        frame1.setLayout(new GridLayout(4, 2, 10, 10)); // Fixed grid layout
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Exactly 2 clear text fields
        JTextField txtName = new JTextField();
        JTextField txtAcc = new JTextField();

        JButton btnRegister = new JButton("Register New User");
        JButton btnLogin = new JButton("Login Existing User");
        frame1.add(new JLabel(" Your Name (Register only):")); 
        frame1.add(txtName);
        frame1.add(new JLabel(" Account Number:")); 
        frame1.add(txtAcc);
        frame1.add(btnRegister);
        frame1.add(btnLogin);
        JFrame frame2 = new JFrame("Dashboard");
        frame2.setSize(350, 220);
        frame2.setLayout(new GridLayout(5, 2, 10, 10));
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel lblName = new JLabel(" Name: ");
        JLabel lblBal = new JLabel(" Balance: Rs0.0");
        JTextField txtAmount = new JTextField();
        JButton btnDeposit = new JButton("Deposit");
        JButton btnWithdraw = new JButton("Withdraw");
        JButton btnLogout = new JButton("Logout");
        frame2.add(lblName);                  frame2.add(new JLabel(""));
        frame2.add(lblBal);                   frame2.add(new JLabel(""));
        frame2.add(new JLabel(" Amount (Rs):")); frame2.add(txtAmount);
        frame2.add(btnDeposit);               frame2.add(btnWithdraw);
        frame2.add(btnLogout);
        btnRegister.addActionListener(e -> {
            String name = txtName.getText().trim();
            String acc = txtAcc.getText().trim();
            if (name.isEmpty() || acc.isEmpty()) {
                JOptionPane.showMessageDialog(frame1, "Please fill both fields!");
                return;
            }
            try {
                FileWriter fw = new FileWriter(file, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(name + "," + acc + ",0.0");
                pw.close();
                JOptionPane.showMessageDialog(frame1, "Account Registered Successfully!");
                txtName.setText(""); 
                txtAcc.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame1, "Database File Error!");
            }
        });
        btnLogin.addActionListener(e -> {
            String searchAcc = txtAcc.getText().trim();
            if (searchAcc.isEmpty()) {
                JOptionPane.showMessageDialog(frame1, "Please enter an Account Number!");
                return;
            }
            boolean found = false;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[1].equals(searchAcc)) {
                        currentName = data[0];
                        currentAcc = data[1];
                        currentBalance = Double.parseDouble(data[2]);
                        found = true;
                        break;
                    }
                }
                br.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame1, "No accounts found yet. Register first!");
                return;
            }

            if (found) {
                lblName.setText(" Welcome: " + currentName);
                lblBal.setText(" Balance: Rs" + currentBalance);
                txtName.setText("");
                txtAcc.setText("");
                
                frame1.setVisible(false);
                frame2.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame1, "Account number not found!");
            }
        });
        btnDeposit.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(txtAmount.getText().trim());
                currentBalance += amt;
                saveBalance();
                lblBal.setText(" Balance: RS" + currentBalance);
                txtAmount.setText("");
                JOptionPane.showMessageDialog(frame2, "Deposited successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame2, "Please enter a valid number.");
            }
        });
        btnWithdraw.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(txtAmount.getText().trim());
                if (amt > currentBalance) {
                    JOptionPane.showMessageDialog(frame2, "Insufficient Balance!");
                } else {
                    currentBalance -= amt;
                    saveBalance();
                    lblBal.setText(" Balance: Rs" + currentBalance);
                    txtAmount.setText("");
                    JOptionPane.showMessageDialog(frame2, "Withdrawn successfully!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame2, "Please enter a valid number.");
            }
        });
        btnLogout.addActionListener(e -> {
            frame2.setVisible(false);
            frame1.setVisible(true);
        });

        frame1.setLocationRelativeTo(null);
        frame2.setLocationRelativeTo(null);
        frame1.setVisible(true);
    }
    public static void saveBalance() {
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(currentAcc)) {
                    line = data[0] + "," + data[1] + "," + currentBalance;
                }
                list.add(line);
            }
            br.close();

            PrintWriter pw = new PrintWriter(new FileWriter(file, false));
            for (String s : list) {
                pw.println(s);
            }
            pw.close();
        } catch (Exception ex) {
            System.out.println("Error while saving balance changes.");
        }
    }
}