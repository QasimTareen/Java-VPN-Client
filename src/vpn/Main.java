package vpn;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        VPNClient client = new VPNClient("192.168.1.1");
        VPNServer server = new VPNServer("192.168.1.1");
        Connection connection = new Connection(client, server);

        while (true) {
            System.out.println("\n_____ VPN CLIENT MENU ______");
            System.out.println("1. Connect to the VPN");
            System.out.println("2. Disconnection");
            System.out.println("3. Encrypt Message (Demo)");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice)
            {
                case 1 :
                    connection.initiate();
                    break;
                case 2:
                    connection.terminate();
                    break;
                case 3:
                    System.out.println("Enter the message : ");
                    String in = input.nextLine();
                    String encrypted = Crypto.encrypt(in);
                    System.out.println("Encryption : "+encrypted);
                    break;
                case 0:
                    System.out.println("Existing >>>> ");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid ! Option try again :) ");
            }
        }
    }
}