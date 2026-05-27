package bankapplication;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class BankCSV {
	public static void main(String[] args) {
		List<Account> bankAccounts= new ArrayList<>();
		try {
			File f=new File("D:\\JavaFile\\BankApllication\\src\\account.csv");
			Scanner sc= new Scanner(f);
			while (sc.hasNextLine()) {
				String line=sc.nextLine();
				String[] data = line.split(",");
				String fName=data[0];
				String lName=data[1];
				String accNum=data[2];
			double balance=Double.parseDouble(data[2]);
			Account Ac=new Account(fName,lName,accNum,balance);
			bankAccounts.add(Ac);
			}
			sc.close();
			System.out.println("Successfully transferred"+bankAccounts.size()+"account from CSv");
			}
		catch(Exception e) {
			System.out.println("Error while reading the CSV file");
			e.printStackTrace();
		}
		for (Account Ac: bankAccounts) {
			System.out.println("Balance"+Ac.getBalance()+"Acc:"+Ac.getaccountNumber()+"Name:"+Ac.getfullName());
		}
		
	}
		// TODO Auto-generated method stub

	}


