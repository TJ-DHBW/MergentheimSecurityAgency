package msa.cql.cryptography.interfaces;

import java.io.File;

public interface IRSAAlgorithm extends ICryptographyAlgorithm {
    String encrypt(String plainMessage, File publicKeyFile);

    String decrypt(String encryptedMessage, File privateKeyFile);
}
