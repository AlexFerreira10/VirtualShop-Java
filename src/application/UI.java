package application;

import model.enums.ProductColors;
import model.enums.ProductsSold;
import model.exceptions.DomainException;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class UI {

	public static void mainMenu() {		
		StringBuilder sb = new StringBuilder();
		sb.append("-------- Menu -------- \n");
		sb.append("[ 1 ] Edit client data\n");
		sb.append("[ 2 ] Verificate order\n");
		sb.append("[ 3 ] Add order item\n");
		sb.append("[ 4 ] Remove order item\n");
		sb.append("[ 5 ] Make a payment\n");
		sb.append("[ 6 ] Print every products\n");
		sb.append("[ 7 ] exit\n");
		sb.append("Option: ");
		System.out.println(sb);
	}
	
	private static void orderData() {
		StringBuilder sb = new StringBuilder();
		sb.append("-------- Order data: -------- \n");
		sb.append("What product do you choose? \n");
		sb.append("- Shorts \n");
		sb.append("- Shirt \n");
		sb.append("- Shoes \n");
		sb.append("Option: ");
		System.out.println(sb);
	}

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static String readCPF(Scanner sc) {
		String cpf;
		boolean validInput;
		do {
			System.out.print("Enter your cpf (only numbers): ");
			cpf = sc.nextLine();
			validInput = cpf.matches("\\d{11}");
			if (!validInput) {
				System.out.println("Invalid CPF: CPF must have 11 digits");
				System.out.println("Enter ok for continue... ");
				sc.next();
			}
		} while (!validInput);
		return cpf;
	}

	public static LocalDate readBirthDate(Scanner sc, DateTimeFormatter dtf) {
		LocalDate birthDate = null;
		boolean validInput = false;
		do {
			System.out.print("Enter your birth date (dd/MM/yyyy): ");
			String birthDateInput = sc.nextLine();
			try {
				birthDate = LocalDate.parse(birthDateInput, dtf);
				validInput = true;
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please enter the date in the format dd/MM/yyyy.");
			}
		} while (!validInput);
		return birthDate;
	}

	public static ProductColors readProductColors(Scanner sc){
		ProductColors productColors = null;
		boolean validInput = false;
		do{
			System.out.print("Enter with the color: ");
			String productColorsInput = sc.nextLine().toUpperCase();
			try {
				productColors = ProductColors.valueOf(productColorsInput);
				validInput = true;
			}  catch (IllegalArgumentException e) {
				System.out.println("Invalid product sold. Please try again.");
			}
		} while (!validInput);
		return productColors;
	}

	public static ProductsSold readProductSold(Scanner sc) {
		ProductsSold productsSold = null;
		boolean validInput = false;
		do {
			UI.orderData();
			String productsSoldInput = sc.nextLine().toUpperCase();
			try {
				productsSold = ProductsSold.valueOf(productsSoldInput);
				validInput = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid product sold. Please try again.");
			}
		} while (!validInput);

		return productsSold;
	}
}
