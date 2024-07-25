import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATM extends JFrame {
    private BankAccount account;
    private JLabel balanceLabel;
    private JTextField amountField;
    private JTextArea outputArea;

    public ATM(BankAccount account) {
        this.account = account;
        createGUI();
    }

    private void createGUI() {
        setTitle("ATM Machine");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Balance display
        balanceLabel = new JLabel("Balance: $" + account.getBalance());
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Amount input
        amountField = new JTextField(10);

        // Buttons
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });

        // Output area
        outputArea = new JTextArea(5, 20);
        outputArea.setEditable(false);

        // Layout
        setLayout(new BorderLayout());
        add(balanceLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.add(new JLabel("Amount:"));
        centerPanel.add(amountField);
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(checkBalanceButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        add(buttonPanel, BorderLayout.SOUTH);

        add(new JScrollPane(outputArea), BorderLayout.EAST);

        setVisible(true);
    }

    private void checkBalance() {
        outputArea.append("Current balance: $" + account.getBalance() + "\n");
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0) {
                account.deposit(amount);
                balanceLabel.setText("Balance: $" + account.getBalance());
                outputArea.append("Deposited: $" + amount + "\n");
            } else {
                outputArea.append("Invalid deposit amount.\n");
            }
        } catch (NumberFormatException e) {
            outputArea.append("Invalid input. Please enter a valid number.\n");
        }
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (account.withdraw(amount)) {
                balanceLabel.setText("Balance: $" + account.getBalance());
                outputArea.append("Withdrew: $" + amount + "\n");
            } else {
                outputArea.append("Invalid withdrawal amount or insufficient balance.\n");
            }
        } catch (NumberFormatException e) {
            outputArea.append("Invalid input. Please enter a valid number.\n");
        }
    }

    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(1000.00); // Initial balance of $1000
        new ATM(userAccount);
    }
}
