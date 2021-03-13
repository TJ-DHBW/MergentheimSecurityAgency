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
    public final String rsaCrackerClassName = "RSACrackerWrapper";
    public final String shiftClassName = "Shift";
    public final String shiftCrackerClassName = "";

    public final String[] simulationQueries = new String[]{"register participant branch_hkg with type normal",
            "register participant branch_cpt with type normal",
            "register participant branch_sfo with type normal",
            "register participant branch_syd with type normal",
            "register participant branch_wuh with type normal",
            "register participant msa with type intruder",
            "create channel hgk_wuh from branch_hkg to branch_wuh",
            "create channel hgk_cpt from branch_hkg to branch_cpt",
            "create channel cpt_syd from branch_cpt to branch_syd",
            "create channel syd_sfo from branch_syd to branch_sfo"};
}
