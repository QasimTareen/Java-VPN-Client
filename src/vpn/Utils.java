package vpn;

public class Utils {

    private  static final byte XOR_KEY = 0x5A;

    public  static byte[] encrypt(byte[] data)
    {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i]^XOR_KEY);
        }
        return result;
    }

    public static byte[] decrypt(byte[] data , int length)
    {
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = (byte)(data[i]^XOR_KEY);
        }
        return result;
    }
}
