package ui;

import java.util.Scanner;

public class UserIOImpl implements UserIO {
	
	private final Scanner myScanner  = new Scanner(System.in);
	
	public void print(String prompt) {
		System.out.println(prompt);
	}

	public String readString(String prompt) {
		System.out.println(prompt);
		return myScanner.nextLine();
	}

	public void close() {
		// TODO Auto-generated method stub
		myScanner.close();
	}
	
	public int readInt(String prompt, int max) {
		// TODO Auto-generated method stub
		boolean isValid = false;
		int choice = 0;
		do {
			try {
				System.out.println(prompt);
				String nextLine = myScanner.nextLine();
				choice = Integer.parseInt(nextLine);
				if (choice > 0 && choice <= max) {
					isValid = true;
				}else {
					System.out.println("You did not enter a number between 1 and "+max);
				}
			}catch(NumberFormatException e) {
				System.out.println("You did not enter a number between 1 and "+max);
			}
		}while(!isValid);
		return choice;
	}

	public int readInt(String prompt) {
		System.out.println(prompt);
		String nextLine = myScanner.nextLine();
		return Integer.parseInt(nextLine);
	}


}
