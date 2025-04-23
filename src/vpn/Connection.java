package vpn;

public class Connection {
    private VPNServer server;
    private VPNClient client;

    public  Connection(VPNClient client , VPNServer server)
    {
        this.client=client;
        this.server=server;
    }

    public void initiate()
    {
        server.start();
        client.connect();
    }

    public void terminate()
    {
        client.disconnect();
    }
}
