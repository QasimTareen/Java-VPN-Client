package vpn;

public class VPNServer {
    private String ip_address;

    public VPNServer(String ip_address)
    {
        this.ip_address=ip_address;
    }

    public void start()
    {
        System.out.println("The Vpn is running at : "+ip_address);
    }
}
