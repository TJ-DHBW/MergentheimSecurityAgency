package msa.cql.cryptography.interfaces;

public interface IShiftCracker extends ICryptographyCracker {
    String decrypt(String encryptedMessage);
}
