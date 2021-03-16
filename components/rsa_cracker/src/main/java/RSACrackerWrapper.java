import msa.cql.cryptography.interfaces.IRSACracker;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;

//TODO Test this
public class RSACrackerWrapper {
    private static final RSACrackerWrapper instance = new RSACrackerWrapper();
    public Port port = new Port();

    public static RSACrackerWrapper getInstance() {
        return instance;
    }

    public String innerDecrypt(String encryptedMessage, File publicKeyFile) {
        BigInteger[] key = extractKeyFromKeyFile(publicKeyFile);
        if (key == null) throw new IllegalStateException("Problem with the keyFile");

        RSACracker rsaCracker = new RSACracker(key[0], key[1], new BigInteger(encryptedMessage));

        try {
            BigInteger cypher = rsaCracker.execute();
            return cypher.toString();
        } catch (RSACrackerException e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO test
    private BigInteger[] extractKeyFromKeyFile(File keyFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(keyFile));
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
            JSONObject keyFileJonObject = new JSONObject(fileContent.toString());
            return new BigInteger[]{new BigInteger(String.valueOf(keyFileJonObject.getInt("n"))), new BigInteger(String.valueOf(keyFileJonObject.getInt("e")))};

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class Port implements IRSACracker {

        @Override
        public String decrypt(String encryptedMessage, File publicKeyFile) {
            return innerDecrypt(encryptedMessage, publicKeyFile);
        }
    }
}
