package msa;

public enum Configuration {
    instance;

    // Toggles detailed console output on or off.
    public final boolean verbose = true;


    // Location of the key-files.
    public final String keyFileFolder = "keys";


    // For loading the various Algorithms.
    public final String fileSeparator = System.getProperty("file.separator");
    public final String componentsFolder = "components";
    public final String jarSubfolder = "build" + fileSeparator + "libs";

    public final String rsaVariant = "1";
    public final String rsaJarLocation = componentsFolder + fileSeparator + "rsa" + rsaVariant + fileSeparator + jarSubfolder + fileSeparator + "rsa.jar";
    public final String rsaCrackerJarLocation = componentsFolder + fileSeparator + "rsa_cracker" + fileSeparator + jarSubfolder + fileSeparator + "rsa_cracker.jar";
    public final String shiftJarLocation = componentsFolder + fileSeparator + "shift" + fileSeparator + jarSubfolder + fileSeparator + "shift.jar";
    public final String shiftCrackerJarLocation = componentsFolder + fileSeparator + "shift_cracker" + fileSeparator + jarSubfolder + fileSeparator + "shift_cracker.jar";

    //TODO insert the fully qualified classname of the components.
    public final String rsaClassName = "";
    public final String rsaCrackerClassName = "";
    public final String shiftClassName = "";
    public final String shiftCrackerClassName = "";

}
