package msa.cql;

import msa.Configuration;

import java.io.*;
import java.time.Instant;

public class Logger {
    //TODO Test this
    //TODO maybe make it more detailed?
    public static void logEncryption(String plainMessage, String encryptedMessage, String algorithm, String keyFile) {
        String key = readWholeFile(new File(Configuration.instance.keyFileFolder + Configuration.instance.fileSeparator + keyFile));
        key = key.replace("\n", "");

        String fileName = "encrypt_" + algorithm + "_" + Instant.now().getEpochSecond() + ".txt";
        String content = "--Encryption--\n" +
                "- Algorithm: " + algorithm + "\n" +
                "- Plain text: " + encryptedMessage + "\n" +
                "- Key: " + key + "\n" +
                "\n" +
                "- Result: " + plainMessage;

        saveInLogFile(content, fileName);
    }

    //TODO Test this
    //TODO maybe make it more detailed?
    public static void logDecryption(String encryptedMessage, String plainMessage, String algorithm, String keyFile) {
        String key = readWholeFile(new File(Configuration.instance.keyFileFolder + Configuration.instance.fileSeparator + keyFile));
        key = key.replace("\n", "");

        String fileName = "decrypt_" + algorithm + "_" + Instant.now().getEpochSecond() + ".txt";
        String content = "--Decryption--\n" +
                "- Algorithm: " + algorithm + "\n" +
                "- Cypher: " + encryptedMessage + "\n" +
                "- Key: " + key + "\n" +
                "\n" +
                "- Result: " + plainMessage;

        saveInLogFile(content, fileName);
    }

    //TODO Test this
    private static void saveInLogFile(String content, String fileName) {
        File logFile = new File(Configuration.instance.logFileFolder + Configuration.instance.fileSeparator + fileName);
        if (logFile.exists()) //noinspection ResultOfMethodCallIgnored
            logFile.delete();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readWholeFile(File fileToRead) {
        StringBuilder ret = new StringBuilder();
        String line;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileToRead));
            while ((line = reader.readLine()) != null) {
                ret.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret.toString();
    }
}
