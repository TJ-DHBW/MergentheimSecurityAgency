import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//TODO Test this
public class Shift {
    private static final Shift instance = new Shift();
    public Port port;

    public String innerEncrypt(String plainMessage, File keyFile) {
        String keyString = extractKeyFromKeyFile(keyFile);
        if (keyString == null) throw new IllegalStateException("Problem with the keyFile");

        int key = Integer.parseInt(keyString);
        CaesarCipher caesarCipher = new CaesarCipher(key);

        return caesarCipher.encrypt(plainMessage);
    }

    public String innerDecrypt(String encryptedMessage, File keyFile) {
        String keyString = extractKeyFromKeyFile(keyFile);
        if (keyString == null) throw new IllegalStateException("Problem with the keyFile");

        int key = Integer.parseInt(keyString);
        CaesarCipher caesarCipher = new CaesarCipher(key);

        return caesarCipher.decrypt(encryptedMessage);
    }

    //TODO make this actually good.
    private String extractKeyFromKeyFile(File keyFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(keyFile));
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
            if (fileContent.indexOf("\"") != fileContent.lastIndexOf("\"") && fileContent.lastIndexOf("\"") != -1) {
                return fileContent.substring(fileContent.indexOf("\"") + 1, fileContent.lastIndexOf("\""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class Port implements IShiftAlgorithm {

        @Override
        public String encrypt(String plainMessage, File keyFile) {
            return innerEncrypt(plainMessage, keyFile);
        }

        @Override
        public String decrypt(String encryptedMessage, File keyFile) {
            return innerDecrypt(encryptedMessage, keyFile);
        }
    }
}
