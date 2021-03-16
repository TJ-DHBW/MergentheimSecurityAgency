package msa.cql.cryptography;

import msa.Configuration;
import msa.cql.cryptography.interfaces.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.*;

public class CryptographyService {

    public static String encrypt(String plainMessage, String algorithm, String keyFileName) {
        if (plainMessage == null || algorithm == null || keyFileName == null)
            throw new IllegalArgumentException("Parameters cant be null.");

        //TODO Test this
        File keyFile = new File(Configuration.instance.keyFileFolder + Configuration.instance.fileSeparator + keyFileName);
        ICryptographyAlgorithm cryptoAlgorithm = getAlgorithmImplementationByString(algorithm);

        switch (algorithm) {
            case "rsa":
                return ((IRSAAlgorithm) cryptoAlgorithm).encrypt(plainMessage, keyFile);
            case "shift":
                return ((IShiftAlgorithm) cryptoAlgorithm).encrypt(plainMessage, keyFile);
            default:
                throw new IllegalArgumentException("There is no implementation for the " + algorithm + " algorithm.");
        }
    }

    public static String decrypt(String encryptedMessage, String algorithm, String keyFileName) {
        if (encryptedMessage == null || algorithm == null || keyFileName == null)
            throw new IllegalArgumentException("Parameters cant be null.");

        //TODO Test this
        File keyFile = new File(Configuration.instance.keyFileFolder + Configuration.instance.fileSeparator + keyFileName);
        ICryptographyAlgorithm cryptoAlgorithm = getAlgorithmImplementationByString(algorithm);

        switch (algorithm) {
            case "rsa":
                return ((IRSAAlgorithm) cryptoAlgorithm).decrypt(encryptedMessage, keyFile);
            case "shift":
                return ((IShiftAlgorithm) cryptoAlgorithm).decrypt(encryptedMessage, keyFile);
            default:
                throw new IllegalArgumentException("There is no implementation for the " + algorithm + " algorithm.");
        }
    }

    private static String crack(String encryptedMessage, String algorithm, String keyFileName) {
        if (encryptedMessage == null || algorithm == null)
            throw new IllegalArgumentException("Parameters cant be null.");

        ICryptographyCracker cryptoCracker = getCrackerImplementationByString(algorithm);

        switch (algorithm) {
            case "rsa":
                if (keyFileName == null) throw new IllegalArgumentException("keyFileName can not be null for RSA.");
                File keyFile = new File(Configuration.instance.keyFileFolder + Configuration.instance.fileSeparator + keyFileName);
                return ((IRSACracker) cryptoCracker).decrypt(encryptedMessage, keyFile);
            case "shift":
                return ((IShiftCracker) cryptoCracker).decrypt(encryptedMessage);
            default:
                throw new IllegalArgumentException("There is no implementation for the " + algorithm + " cracker.");
        }
    }

    //TODO Test this
    public static String crack(String encryptedMessage, String algorithm, String keyFileName, int seconds) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> CryptographyService.crack(encryptedMessage, algorithm, keyFileName));
        try {
            return future.get(seconds, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Something in the cracker went wrong.");
        } finally {
            executor.shutdownNow();
        }
        return null;
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
        if (Configuration.instance.onlyLoadSignedJars && !verifyJar(jarLocation))
            throw new SecurityException("Jar " + jarLocation + " could not be verified!");

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
        if (Configuration.instance.onlyLoadSignedJars && !verifyJar(jarLocation))
            throw new SecurityException("Jar " + jarLocation + " could not be verified!");

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

    private static boolean verifyJar(String jarLocation) {
        File jarFile = new File(jarLocation);
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("C:\\Program Files\\Java\\jdk-15.0.1\\bin\\jarsigner", "-verify", jarFile.getAbsolutePath());
            Process process = processBuilder.start();
            process.waitFor();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("verified")) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        //String test = CryptographyService.encrypt("hallo", "shift", "shift_key.json");

        String test = CryptographyService.crack("helllo", "shift", null);
        System.out.println(test);
        String test2 = CryptographyService.encrypt("hallo", "rsa", "rsa_key_public.json");
        System.out.println(test2);
    }

    /*public static void main(String[] args) throws InterruptedException, IOException {
        // Execute this main, if you want to sign all used components!
        // The components have to exist first.
        String[] jarLocationsOfJarsToSign = new String[]{Configuration.instance.rsaJarLocation,
                Configuration.instance.shiftJarLocation,
                Configuration.instance.rsaCrackerJarLocation,
                Configuration.instance.shiftCrackerJarLocation};

        for (String jarLocation : jarLocationsOfJarsToSign) {
            File jarFile = new File(jarLocation);
            if (!jarFile.exists())
                throw new IllegalStateException("All the jars have to exist before signing. BUILD THEM!");

            ProcessBuilder processBuilder = new ProcessBuilder("C:\\Program Files\\Java\\jdk-15.0.1\\bin\\jarsigner",
                    "-keystore",
                    "signing\\keystore.jks",
                    "-storepass",
                    "dhbw2021",
                    jarFile.getAbsolutePath(),
                    "server");
            Process process = processBuilder.start();
            process.waitFor();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }*/
}
