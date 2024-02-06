package application;

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
	
	public static void orderData() {
		StringBuilder sb = new StringBuilder();
		sb.append("-------- Order data: -------- \n");
		sb.append("What product do you choose? \n");
		sb.append("- Shorts \n");
		sb.append("- Shirt \n");
		sb.append("- Shoes \n");
		sb.append("Option: ");
		System.out.println(sb);
	}
}
