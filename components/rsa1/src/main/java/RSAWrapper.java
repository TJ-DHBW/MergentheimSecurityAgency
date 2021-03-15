import java.io.*;
import java.math.BigInteger;
import java.util.Base64;

import msa.cpl.cryptography.interfaces.IRSAAlgorithm;
import org.json.JSONObject;

public class RSAWrapper {
    private static final RSAWrapper instance = new RSAWrapper();
    public Port port = new Port();

    public static RSAWrapper getInstance() {
        return instance;
    }

    public String innerEncrypt(String plainMessage, File publicKeyFile) {
        Key key = extractKeyFromKeyFile(publicKeyFile, false);
        if (key == null) throw new IllegalStateException("Problem with the keyFile");

        Cipher rsaCipher = new Cipher();

        return Base64.getEncoder().encodeToString(rsaCipher.encrypt(plainMessage, key));
    }

    public String innerDecrypt(String encryptedMessage, File privateKeyFile) {
        Key key = extractKeyFromKeyFile(privateKeyFile, true);
        if (key == null) throw new IllegalStateException("Problem with the keyFile");

        Cipher rsaCipher = new Cipher();

        return rsaCipher.decrypt(encryptedMessage.getBytes(), key);
    }

    //TODO check if works, implement in other algorithms
    private Key extractKeyFromKeyFile(File keyFile, boolean privateKey) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(keyFile));
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
            JSONObject keyFileJonObject = new JSONObject(fileContent.toString());
            if(privateKey){
                return new Key(new BigInteger(String.valueOf(keyFileJonObject.getJSONObject("n"))),
                        new BigInteger(String.valueOf(keyFileJonObject.getJSONObject("d"))));
            }
            else{
                return new Key(new BigInteger(String.valueOf(keyFileJonObject.getJSONObject("n"))),
                        new BigInteger(String.valueOf(keyFileJonObject.getJSONObject("e"))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class Port implements IRSAAlgorithm {

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
