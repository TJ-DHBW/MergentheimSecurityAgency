import msa.cql.cryptography.interfaces.IRSACracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

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

    //TODO make this actually good.
    private BigInteger[] extractKeyFromKeyFile(File keyFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(keyFile));
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }

            String[] splitFile = fileContent.toString().split(",");
            splitFile[0] = splitFile[0].split(":")[1];
            splitFile[1] = splitFile[1].split(":")[1];

            BigInteger[] ret = new BigInteger[2];

            if (splitFile[0].indexOf("\"") != splitFile[0].lastIndexOf("\"") && splitFile[0].lastIndexOf("\"") != -1) {
                ret[0] = new BigInteger(splitFile[0].substring(splitFile[0].indexOf("\"") + 1, splitFile[0].lastIndexOf("\"")));
                if (splitFile[1].indexOf("\"") != splitFile[1].lastIndexOf("\"") && splitFile[1].lastIndexOf("\"") != -1) {
                    ret[1] = new BigInteger(splitFile[1].substring(splitFile[1].indexOf("\"") + 1, splitFile[1].lastIndexOf("\"")));
                    return ret;
                }
            }
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
