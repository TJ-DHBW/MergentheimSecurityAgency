import java.io.File;

public interface IShiftAlgorithm extends ICryptographyAlgorithm {
    String encrypt(String plainMessage, File keyFile);

    String decrypt(String encryptedMessage, File keyFile);
}
