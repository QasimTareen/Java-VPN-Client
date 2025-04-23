package vpn;

public class Crypto {

    public static String encrypt(String input) {
        return "ENCRYPTED<" + input + ">";
    }

    public static String decrypt(String input) {
        return input.replace("ENCRYPTED<", "").replace(">", "");
    }
}
