package vpn;

public class VPNClient {
    private String server_address;

    public VPNClient(String server_address)
    {
        this.server_address=server_address;
    }

    public void connect()
    {
        System.out.println("Connecting to the server at : "+server_address);
        // Placeholder for the real server logical connection
    }
    public void disconnect()
    {
        System.out.println("Disconnected from vpn :( ");
    }
}
