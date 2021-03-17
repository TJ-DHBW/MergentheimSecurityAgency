import java.io.*;
import java.math.BigInteger;
import java.util.Base64;

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
        return Base64.getEncoder().encodeToString((plainMessageAsNumber.modPow(key.getE(), key.getN()).toByteArray()));
    }

    public String innerDecrypt(String encryptedMessage, File keyFile) {
        Key key = extractKeyFromKeyFile(keyFile, true);
        if (key == null) throw new IllegalStateException("Problem with the keyFile");

        BigInteger encryptedMessageAsNumber = new BigInteger(Base64.getDecoder().decode(encryptedMessage));
        BigInteger decryptedMessageAsNumber = encryptedMessageAsNumber.modPow(key.getE(), key.getN());
        return Utility.numberToString(decryptedMessageAsNumber);
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
            if(privateKey){
                return new Key(new BigInteger(String.valueOf(keyFileJonObject.getBigInteger("n"))),
                        new BigInteger(String.valueOf(keyFileJonObject.getBigInteger("d"))));
            }
            else{
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
