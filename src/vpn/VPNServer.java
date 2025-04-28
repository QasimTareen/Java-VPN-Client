package vpn;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

public class VPNServer {
    public void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(VPNConfig.SERVER_PORT))
        {
            System.out.println("VPN Server started on port "+VPNConfig.SERVER_PORT);
            while (true)
            {
                Socket client_socket = serverSocket.accept();
                System.out.println("Client connected : "+client_socket.getInetAddress());
                new Thread(()-> handleClient(client_socket)).start();
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try(  InputStream input = clientSocket.getInputStream();
              OutputStream output = clientSocket.getOutputStream();)
        {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while((bytesRead = input.read(buffer)) != -1 )
            {
                byte[] decrypted = Utils.decrypt(buffer,bytesRead);
                System.out.println("Received : "+new String(decrypted));
                byte[] reponse = Utils.encrypt(("ACK : "+new String(decrypted)).getBytes());
                output.write(reponse);
                output.flush();
            }
        }catch (Exception e )
        {
            System.out.println("Client disconnected : ");
        }
    }
}
