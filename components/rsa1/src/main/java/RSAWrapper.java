import java.io.*;
import java.math.BigInteger;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.
import org.json.parser.JSONParser;
import org.json.parser.ParseException;

public class RSAWrapper {
    private static final RSAWrapper instance = new RSAWrapper();
    public Port port = new Port();

    public static RSAWrapper getInstance() {
        return instance;
    }

    public String innerEncrypt(String plainMessage, File keyFile) {
        Key key = extractKeyFromKeyFile(keyFile);
        if (key == null) throw new IllegalStateException("Problem with the keyFile");

        Cipher rsaCipher = new Cipher();

        return Base64.getEncoder().encodeToString(rsaCipher.encrypt(plainMessage, key));
    }

    public String innerDecrypt(String encryptedMessage, File keyFile) {
        String keyString = extractKeyFromKeyFile(keyFile);
        if (keyString == null) throw new IllegalStateException("Problem with the keyFile");

        int key = Integer.parseInt(keyString);
        CaesarCipher caesarCipher = new CaesarCipher(key);

        return caesarCipher.decrypt(encryptedMessage);
    }

    //TODO check if works, implement in other algorithms
    private Key extractKeyFromKeyFile(File keyFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(keyFile));
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
            JSONObject keyFileJonObject = new JSONObject(fileContent.toString());
            return  new Key(new BigInteger(String.valueOf(keyFileJonObject.getJSONObject("n"))), new BigInteger(String.valueOf(keyFileJonObject.getJSONObject("e"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class Port implements IRSAWrapperAlgorithm {

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
