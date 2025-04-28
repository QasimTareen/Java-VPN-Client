package vpn;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VPNServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(VPNConfig.SERVER_PORT)) {
            System.out.println("VPN Server started on port " + VPNConfig.SERVER_PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (Socket socket = clientSocket;
             InputStream input = socket.getInputStream();
             OutputStream output = socket.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                byte[] decrypted = Utils.decrypt(buffer, bytesRead);
                System.out.println("Received: " + new String(decrypted));
                byte[] response = Utils.encrypt(("ACK: " + new String(decrypted)).getBytes());
                output.write(response);
                output.flush();
            }
        } catch (Exception e) {
            System.out.println("Client disconnected.");
        }
    }
}
