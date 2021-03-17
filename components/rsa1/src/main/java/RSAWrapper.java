import msa.cql.cryptography.interfaces.IRSAAlgorithm;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;

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
        byte[] encryption = rsaCipher.encrypt(plainMessage, key);
        return Base64.getEncoder().encodeToString(encryption);
    }

    public String innerDecrypt(String encryptedMessage, File privateKeyFile) {
        Key key = extractKeyFromKeyFile(privateKeyFile, true);
        if (key == null) throw new IllegalStateException("Problem with the keyFile");

        Cipher rsaCipher = new Cipher();
        byte[] decryption = Base64.getDecoder().decode(encryptedMessage);
        return rsaCipher.decrypt(decryption, key);
    }

    private Key extractKeyFromKeyFile(File keyFile, boolean privateKey) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(keyFile));
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
            JSONObject keyFileJonObject = new JSONObject(fileContent.toString());
            if (privateKey) {
                return new Key(new BigInteger(String.valueOf(keyFileJonObject.getBigInteger("n"))),
                        new BigInteger(String.valueOf(keyFileJonObject.getBigInteger("d"))));
            } else {
                return new Key(new BigInteger(String.valueOf(keyFileJonObject.getBigInteger("n"))),
                        new BigInteger(String.valueOf(keyFileJonObject.getBigInteger("e"))));
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
