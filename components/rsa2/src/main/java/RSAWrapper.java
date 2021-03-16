import java.io.*;
import java.math.BigInteger;

import msa.cql.cryptography.interfaces.IRSAAlgorithm;
import org.json.JSONObject;

public class RSAWrapper {
    private static final RSAWrapper instance = new RSAWrapper();
    public Port port = new Port();

    public static RSAWrapper getInstance() {
        return instance;
    }

    public String innerEncrypt(String plainMessage, File keyFile) {
        Key key = extractKeyFromKeyFile(keyFile, false);

        BigInteger plainMessageAsNumber = Utility.stringToNumber(plainMessage);

        return String.valueOf(plainMessageAsNumber.modPow(key.getE(), key.getN()));
    }

    public String innerDecrypt(String encryptedMessage, File keyFile) {
        Key key = extractKeyFromKeyFile(keyFile, true);
        if (key == null) throw new IllegalStateException("Problem with the keyFile");

        BigInteger encryptedMessageAsNumber = new BigInteger(encryptedMessage);

        return String.valueOf(encryptedMessageAsNumber.modPow(key.getE(), key.getN()));
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
