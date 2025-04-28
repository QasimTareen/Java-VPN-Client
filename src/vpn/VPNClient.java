package vpn;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class VPNClient {
    private Socket soc;
    private InputStream in;
    private OutputStream ou;

    public void connect(String serverIP, int serverPort) throws Exception {
        soc = new Socket(serverIP, serverPort);
        in = soc.getInputStream();
        ou = soc.getOutputStream();
        VPNLogger.log("Connected to server");

        new Thread(this::receiveData).start();
    }

    public void receiveData() {
        try {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                byte[] decrypted = Utils.decrypt(buffer, bytesRead);
                VPNLogger.log("Received: " + new String(decrypted));
            }
        } catch (Exception e) {
            VPNLogger.log("Connection closed");
        } finally {
            disconnect();
        }
    }

    public void sendData(String message) throws Exception {
        if (ou == null) throw new Exception("Not connected to server");
        byte[] encrypted = Utils.encrypt(message.getBytes());
        ou.write(encrypted);
        ou.flush();
    }

    public void disconnect() {
        try {
            if (soc != null && !soc.isClosed()) soc.close();
            if (in != null) in.close();
            if (ou != null) ou.close();
            VPNLogger.log("Disconnected from server.");
        } catch (Exception e) {
            VPNLogger.log("Error during disconnect: " + e.getMessage());
        }
    }
}
