package vpn;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class VPNClient {
    private Socket soc;
    private InputStream in;
    private OutputStream ou;

    public void connect (String serverIP , int serverPort) throws Exception
    {
        soc = new Socket(serverIP,serverPort);
        in=soc.getInputStream();
        ou=soc.getOutputStream();
        VPNLogger.log("Connected to server ");

        new Thread(this::receiveData).start();
    }
    public void receiveData()
    {
        try
        {
            byte[] buffer = new byte[4096];
            int bytes_read;
            while ((bytes_read = in.read(buffer)) != -1)
            {
                byte[] decrypted = Utils.decrypt(buffer,bytes_read);
                VPNLogger.log("Received : "+new String(decrypted));
            }
        } catch (Exception e) {
           VPNLogger.log("Connection closed ");
        }
    }

    public void sendData(String message) throws Exception
    {
        byte[] encrypted = Utils.encrypt(message.getBytes());
        ou.write(encrypted);
        ou.flush();
    }
}
