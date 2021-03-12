package msa.cql.cryptography.interfaces;

import java.io.File;

public interface IRSACracker extends ICryptographyCracker {
    String decrypt(String encryptedMessage, File publicKeyFile);
}
