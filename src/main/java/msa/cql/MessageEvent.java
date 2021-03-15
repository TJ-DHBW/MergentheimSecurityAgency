package msa.cql;

public class MessageEvent {
    private final String encryptedMessage;
    private final String algorithm;
    private final String keyFileName;
    private final String senderName;
    private final String receiverName;

    public MessageEvent(String encryptedMessage, String algorithm, String keyFileName, String senderName, String receiverName) {
        this.encryptedMessage = encryptedMessage;
        this.algorithm = algorithm;
        this.keyFileName = keyFileName;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getKeyFileName() {
        return keyFileName;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }
}
