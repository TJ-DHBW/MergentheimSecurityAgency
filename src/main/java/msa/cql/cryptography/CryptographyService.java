package msa.cql.cryptography;

import msa.Configuration;
import msa.cql.cryptography.interfaces.*;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class CryptographyService {

    public static String encrypt(String message, String algorithm, String keyFileName) {
        if (message == null || algorithm == null || keyFileName == null)
            throw new IllegalArgumentException("Parameters cant be null.");

        //TODO Test this
        File file = new File(Configuration.instance.keyFileFolder + Configuration.instance.fileSeparator + keyFileName);
        ICryptographyAlgorithm cryptoAlgorithm = getAlgorithmImplementationByString(algorithm);

        switch (algorithm) {
            case "rsa":
                return ((IRSAAlgorithm) cryptoAlgorithm).encrypt(message, file);
            case "shift":
                return ((IShiftAlgorithm) cryptoAlgorithm).encrypt(message, file);
            default:
                throw new IllegalArgumentException("There is no implementation for the " + algorithm + " algorithm.");
        }
    }

    public static String decrypt(String message, String algorithm, String keyFileName) {
        if (message == null || algorithm == null || keyFileName == null)
            throw new IllegalArgumentException("Parameters cant be null.");

        //TODO Test this
        File file = new File(Configuration.instance.keyFileFolder + Configuration.instance.fileSeparator + keyFileName);
        ICryptographyAlgorithm cryptoAlgorithm = getAlgorithmImplementationByString(algorithm);

        switch (algorithm) {
            case "rsa":
                return ((IRSAAlgorithm) cryptoAlgorithm).decrypt(message, file);
            case "shift":
                return ((IShiftAlgorithm) cryptoAlgorithm).decrypt(message, file);
            default:
                throw new IllegalArgumentException("There is no implementation for the " + algorithm + " algorithm.");
        }
    }

    //TODO Test this
    public static String crack(String message, String algorithm, String keyFileName) {
        if (message == null || algorithm == null) throw new IllegalArgumentException("Parameters cant be null.");

        ICryptographyCracker cryptoCracker = getCrackerImplementationByString(algorithm);

        switch (algorithm) {
            case "rsa":
                if (keyFileName == null) throw new IllegalArgumentException("keyFileName can not be null for RSA.");
                File file = new File(Configuration.instance.keyFileFolder + Configuration.instance.fileSeparator + keyFileName);
                return ((IRSACracker) cryptoCracker).decrypt(message, file);
            case "shift":
                return ((IShiftCracker) cryptoCracker).decrypt(message);
            default:
                throw new IllegalArgumentException("There is no implementation for the " + algorithm + " cracker.");
        }
    }

    //TODO Test this
    private static ICryptographyAlgorithm getAlgorithmImplementationByString(String algorithm) {
        String jarLocation, className;

        switch (algorithm) {
            case "rsa":
                jarLocation = Configuration.instance.rsaJarLocation;
                className = Configuration.instance.rsaClassName;
                break;
            case "shift":
                jarLocation = Configuration.instance.shiftJarLocation;
                className = Configuration.instance.shiftClassName;
                break;
            default:
                throw new IllegalArgumentException("There is no implementation for the " + algorithm + " algorithm.");
        }

        ICryptographyAlgorithm componentPort = null;
        try {
            URL[] urls = {new File(jarLocation).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, CryptographyService.class.getClassLoader());
            Class algorithmClass = Class.forName(className, true, urlClassLoader);
            Object algorithmInstance = algorithmClass.getMethod("getInstance").invoke(null);
            componentPort = (ICryptographyAlgorithm) algorithmClass.getDeclaredField("port").get(algorithmInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return componentPort;
    }

    //TODO Test this
    private static ICryptographyCracker getCrackerImplementationByString(String algorithm) {
        String jarLocation, className;

        switch (algorithm) {
            case "rsa":
                jarLocation = Configuration.instance.rsaCrackerJarLocation;
                className = Configuration.instance.rsaCrackerClassName;
                break;
            case "shift":
                jarLocation = Configuration.instance.shiftCrackerJarLocation;
                className = Configuration.instance.shiftCrackerClassName;
                break;
            default:
                throw new IllegalArgumentException("There is no implementation for the " + algorithm + " cracker.");
        }

        ICryptographyCracker componentPort = null;
        try {
            URL[] urls = {new File(jarLocation).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, CryptographyService.class.getClassLoader());
            Class crackerClass = Class.forName(className, true, urlClassLoader);
            Object crackerInstance = crackerClass.getMethod("getInstance").invoke(null);
            componentPort = (ICryptographyCracker) crackerClass.getDeclaredField("port").get(crackerInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return componentPort;
    }
}
