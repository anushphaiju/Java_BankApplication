package bankapplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class GUIBank extends JFrame {

    private Transaction transferObject = new Transaction(); 
    private StringBuilder sbAllData;
    private LinkedList<Account> globalAccounts;

    private JLabel showAllData;
    private JButton showAllButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transferButton;

    private JTextField accDeposit;
    private JTextField accWithdraw;
    private JTextField acc1Transfer;
    private JTextField acc2Transfer;
    private JTextField depositInput;
    private JTextField withdrawInput;
    private JTextField transferAmount;

    private JLabel lblDepositTitle, lblWithdrawTitle, lblTransferTitle;
    private JLabel lblAccDep, lblAmtDep, lblAccWith, lblAmtWith, lblAcc1, lblAcc2, lblAmtTrans;

    public GUIBank(LinkedList<Account> accounts) {
        super("Banking System");
        setLayout(null);

        this.globalAccounts = accounts;

        sbAllData = new StringBuilder("<html><h3>Account Directory:</h3>");
        for (Account acc : globalAccounts) {
            sbAllData.append("Name: ").append(acc.getfullName())
                     .append(" | Acc: ").append(acc.getaccountNumber())
                     .append(" | Balance: Rs ").append(acc.getBalance()).append("<br>");
        }
        sbAllData.append("</html>");

        showAllButton = new JButton("Show All");
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        transferButton = new JButton("Transfer Money");

        accDeposit = new JTextField();
        depositInput = new JTextField();
        accWithdraw = new JTextField();
        withdrawInput = new JTextField();
        acc1Transfer = new JTextField();
        acc2Transfer = new JTextField();
        transferAmount = new JTextField();

        showAllData = new JLabel("Click 'Show All' to view data.");
        
        lblDepositTitle = new JLabel("--- DEPOSIT ---");
        lblAccDep = new JLabel("Acc No:");
        lblAmtDep = new JLabel("Amount:");

        lblWithdrawTitle = new JLabel("--- WITHDRAW ---");
        lblAccWith = new JLabel("Acc No:");
        lblAmtWith = new JLabel("Amount:");

        lblTransferTitle = new JLabel("--- TRANSFER ---");
        lblAcc1 = new JLabel("From Acc:");
        lblAcc2 = new JLabel("To Acc:");
        lblAmtTrans = new JLabel("Amount:");

        lblDepositTitle.setBounds(20, 20, 150, 20);
        lblAccDep.setBounds(20, 45, 60, 20);
        accDeposit.setBounds(85, 45, 100, 20);
        lblAmtDep.setBounds(20, 70, 60, 20);
        depositInput.setBounds(85, 70, 100, 20);
        depositButton.setBounds(20, 95, 165, 25);

        lblWithdrawTitle.setBounds(20, 140, 150, 20);
        lblAccWith.setBounds(20, 165, 60, 20);
        accWithdraw.setBounds(85, 165, 100, 20);
        lblAmtWith.setBounds(20, 190, 60, 20);
        withdrawInput.setBounds(85, 190, 100, 20);
        withdrawButton.setBounds(20, 215, 165, 25);

        lblTransferTitle.setBounds(20, 260, 150, 20);
        lblAcc1.setBounds(20, 285, 70, 20);
        acc1Transfer.setBounds(95, 285, 90, 20);
        lblAcc2.setBounds(20, 310, 70, 20);
        acc2Transfer.setBounds(95, 310, 90, 20);
        lblAmtTrans.setBounds(20, 335, 70, 20);
        transferAmount.setBounds(95, 335, 90, 20);
        transferButton.setBounds(20, 360, 165, 25);

        showAllButton.setBounds(220, 20, 120, 25);
        showAllData.setBounds(220, 55, 300, 330);
        showAllData.setVerticalAlignment(SwingConstants.TOP);

        add(lblDepositTitle); add(lblAccDep); add(accDeposit); add(lblAmtDep); add(depositInput); add(depositButton);
        add(lblWithdrawTitle); add(lblAccWith); add(accWithdraw); add(lblAmtWith); add(withdrawInput); add(withdrawButton);
        add(lblTransferTitle); add(lblAcc1); add(acc1Transfer); add(lblAcc2); add(acc2Transfer); add(lblAmtTrans); add(transferAmount); add(transferButton);
        add(showAllButton); add(showAllData);

        HandlerClass handler = new HandlerClass();
        showAllButton.addActionListener(handler);
        depositButton.addActionListener(handler);
        withdrawButton.addActionListener(handler);
        transferButton.addActionListener(handler);

        setSize(560, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class HandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            if (e.getSource() == showAllButton) {
                showAllData.setText(sbAllData.toString());
            } 
            
            else if (e.getSource() == depositButton) {
                String targetAccNum = accDeposit.getText().trim();
                String amtText = depositInput.getText().trim();

                if (targetAccNum.isEmpty() || amtText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill both Deposit fields!");
                    return;
                }

                try {
                    double amount = Double.parseDouble(amtText);
                    Account targetAccount = findAccount(targetAccNum);

                    if (targetAccount != null) {
                        targetAccount.deposit(amount);
                        updateStringBuilder();
                        JOptionPane.showMessageDialog(null, "Successfully deposited Rs " + amount);
                        accDeposit.setText("");
                        depositInput.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Account Number Not Found!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid numeric amount.");
                }
            } 
            
            else if (e.getSource() == withdrawButton) {
                String targetAccNum = accWithdraw.getText().trim();
                String amtText = withdrawInput.getText().trim();

                if (targetAccNum.isEmpty() || amtText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill both Withdraw fields!");
                    return;
                }

                try {
                    double amount = Double.parseDouble(amtText);
                    Account targetAccount = findAccount(targetAccNum);

                    if (targetAccount != null) {
                        if (targetAccount.withdraw(amount)) {
                            updateStringBuilder();
                            JOptionPane.showMessageDialog(null, "Successfully withdrawn Rs " + amount);
                            accWithdraw.setText("");
                            withdrawInput.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Withdrawal failed! Check balance.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Account Number Not Found!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error processing withdrawal request.");
                }
            } 
            
            else if (e.getSource() == transferButton) {
                String sourceAccNum = acc1Transfer.getText().trim();
                String destAccNum = acc2Transfer.getText().trim();
                String amtText = transferAmount.getText().trim();

                if (sourceAccNum.isEmpty() || destAccNum.isEmpty() || amtText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all Transfer fields!");
                    return;
                }

                try {
                    double amount = Double.parseDouble(amtText);
                    Account sourceAccount = findAccount(sourceAccNum);
                    Account destAccount = findAccount(destAccNum);

                    if (sourceAccount == null || destAccount == null) {
                        JOptionPane.showMessageDialog(null, "One or both Account Numbers were not found!");
                        return;
                    }

                    if (sourceAccount.getBalance() < amount) {
                        JOptionPane.showMessageDialog(null, "Transfer failed: Insufficient funds in source account.");
                        return;
                    }

                    transferObject.Transfer(sourceAccount, destAccount, amount);
                    updateStringBuilder();
                    JOptionPane.showMessageDialog(null, "Transferred Rs " + amount + " from " + sourceAccNum + " to " + destAccNum);
                    
                    acc1Transfer.setText("");
                    acc2Transfer.setText("");
                    transferAmount.setText("");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Transfer processing failed.");
                }
            }
        }

        private Account findAccount(String accNum) {
            for (Account acc : globalAccounts) {
                if (acc.getaccountNumber().equals(accNum)) {
                    return acc;
                }
            }
            return null;
        }

        private void updateStringBuilder() {
            sbAllData = new StringBuilder("<html><h3>Account Directory:</h3>");
            for (Account acc : globalAccounts) {
                sbAllData.append("Name: ").append(acc.getfullName())
                         .append(" | Acc: ").append(acc.getaccountNumber())
                         .append(" | Balance: Rs ").append(acc.getBalance()).append("<br>");
            }
            sbAllData.append("</html>");
            if (!showAllData.getText().equals("Click 'Show All' to view data.")) {
                showAllData.setText(sbAllData.toString());
            }
        }
    }
}
