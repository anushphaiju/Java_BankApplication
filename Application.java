package bankapplication;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Customer {
	private String firstName;
	private String lastname;
	
	public Customer(String firstName, String lastname) {
		this.firstName = firstName;
		this.lastname = lastname;
	}
	public String getfullName() {
		return firstName + " " + lastname;
	}
}

class Account extends Customer {
	private String accountNumber;
	private double balance;
	
	public Account(String firstName, String lastname, String accountNumber, double balance) {
		super(firstName, lastname);
		this.accountNumber = accountNumber;
		this.balance = balance;
	}
	public String getaccountNumber() {
		return accountNumber;
	}
	public double getBalance() {
		return balance;
	}
	public void DisplayInfo() {
		System.out.println("Account User " + getfullName());
		System.out.println("Account Number " + accountNumber);
		System.out.println("Balance " + balance);
	}
	public void deposit(double amount) {
		if (amount > 0) {
			balance += amount;
			System.out.println("The amount of " + amount + " is deposited to the Account");
		} else {
			System.out.println("Try Again!!!");
		}
	}
	public boolean withdraw(double amount) {
		if (amount > 0 && balance >= amount) {
			balance -= amount;
			System.out.println("Amount withdraw " + amount + " from your Bank Account");
			return true;
		} else {
			System.out.println("Insufficient Balance");
			return false;
		}
	}
	public void printBalance() {
		System.out.println(" " + accountNumber + " " + balance);
	}
}

class Transaction {
	public void Transfer(Account sender, Account receiver, double amount) {
		System.out.println(sender.getfullName() + " is sending you amount of " + amount);
		if (sender.withdraw(amount)) {
			receiver.deposit(amount);
			System.out.println("Amount Transferred Successfully");		
		} else {
			System.out.println("Error: Transaction failed");
		}
	}
}

public class Application {

	public static void main(String[] args) {
		List<Account> bankAccounts = new ArrayList<>();
		Transaction t = new Transaction();
		try {
			File f = new File("D:\\JavaFile\\BankApllication\\src\\account.csv");
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] data = line.split(",");
				String fName = data[0];
				String lName = data[1];
				String accNum = data[2];
				double balance = Double.parseDouble(data[2]);
				Account Ac = new Account(fName, lName, accNum, balance);
				bankAccounts.add(Ac);
			}
			sc.close();
			System.out.println("Successfully transferred " + bankAccounts.size() + " account from CSV");
		}
		catch (Exception e) {
			System.out.println("Error while reading the CSV file");
			e.printStackTrace();
		}
		
		if (bankAccounts.size() >= 2) {
			Account a1 = bankAccounts.get(0);
			Account a2 = bankAccounts.get(1);
			t.Transfer(a1, a2, 100.0);
			System.out.println("Balance");
			a1.printBalance();
			a2.printBalance();
		}
		
		GUIBank.launchGUI();
	}
}