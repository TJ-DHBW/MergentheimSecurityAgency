package msa.cpl.cryptography.interfaces;

import java.io.File;

public interface IRSAAlgorithm extends ICryptographyAlgorithm {
    String encrypt(String plainMessage, File keyFile);

    String decrypt(String encryptedMessage, File keyFile);
}
