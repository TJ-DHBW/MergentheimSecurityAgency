import msa.cql.cryptography.interfaces.IShiftCracker;

public class ShiftCrackerWrapper
{
    private static final ShiftCrackerWrapper instance = new ShiftCrackerWrapper();
    public Port port = new Port();

    public static ShiftCrackerWrapper getInstance() {
        return instance;
    }
    //TODO check if actually works and if output ist readable at all
    public String innerDecrypt(String encryptedMessage) {
        ShiftCracker shiftCracker = new ShiftCracker();
        return shiftCracker.crackMessage(encryptedMessage);
    }

    public class Port implements IShiftCracker{

        @Override
        public String decrypt(String encryptedMessage) {
            return innerDecrypt(encryptedMessage);
        }
    }
}
