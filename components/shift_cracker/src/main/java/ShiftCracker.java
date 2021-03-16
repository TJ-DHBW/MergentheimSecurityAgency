import java.text.DecimalFormat;
import java.util.Scanner;

public class ShiftCracker {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#0.00000");

    public String crackMessage(String encodedMessage){
        Scanner scanner = new Scanner(encodedMessage);
        String source = scanner.nextLine().trim().toUpperCase();

        if (source.equals("")) {
            System.exit(0);
        }

        char[] sourceText = new char[source.length()];
        int[] unicode = new int[source.length()];
        int[] unicodeCopy = new int[source.length()];

        for (int count = 0; count < source.length(); count++) {
            sourceText[count] = source.charAt(count);
        }

        String hex;
        int dec;

        for (int count = 0; count < sourceText.length; count++) {
            hex = Integer.toHexString(sourceText[count]);
            dec = Integer.parseInt(hex, 16);
            unicode[count] = dec;
            unicodeCopy[count] = dec;
        }
        StringBuilder stringBuilder = new StringBuilder();

        for (int shift = 1; shift <= 25; shift++) {
            stringBuilder.append(smartShift(shift, unicode, unicodeCopy));
        }
        return stringBuilder.toString();
    }




    private String smartShift(int shift, int[] unicode, int[] unicodeCopy) {
        for (int x = 0; x <= unicode.length - 1; x++) {
            unicodeCopy[x] = unicode[x];

            if (unicode[x] >= 65 && unicode[x] <= 90) {
                unicodeCopy[x] += shift;
                if (unicodeCopy[x] > 90) {
                    unicodeCopy[x] -= 26;
                }
            }
        }

        String[] processed = new String[unicode.length];
        char[] finalProcess = new char[unicode.length];

        for (int count = 0; count < processed.length; count++) {
            processed[count] = Integer.toHexString(unicodeCopy[count]);
            int hexToInt = Integer.parseInt(processed[count], 16);
            char intToChar = (char) hexToInt;
            finalProcess[count] = intToChar;
        }

        double frequency = 0;
        double aFrequency = 0;
        double eFrequency = 0;
        double iFrequency = 0;
        double oFrequency = 0;
        double uFrequency = 0;

        for (char c : finalProcess) {
            frequency++;

            switch (c) {
                case 'A':
                    aFrequency++;
                    break;
                case 'E':
                    eFrequency++;
                    break;
                case 'I':
                    iFrequency++;
                    break;
                case 'O':
                    oFrequency++;
                    break;
                case 'U':
                    uFrequency++;
                    break;
                default:
                    break;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (char character : finalProcess) {
            stringBuilder.append(character);
        }
        stringBuilder.append("\n");

        /*if (eFrequency / frequency >= 0.05 || aFrequency / frequency >= 0.05 || iFrequency / frequency >= 0.05 || oFrequency / frequency >= 0.05 || uFrequency / frequency >= 0.05) {
            stringBuilder.append("\t\tA : ").append(decimalFormat.format(aFrequency / frequency));
            stringBuilder.append("\t\tE : ").append(decimalFormat.format(eFrequency / frequency));
            stringBuilder.append("\t\tI : ").append(decimalFormat.format(iFrequency / frequency));
            stringBuilder.append("\t\tO : ").append(decimalFormat.format(oFrequency / frequency));
            stringBuilder.append("\t\tU : ").append(decimalFormat.format(uFrequency / frequency));

            stringBuilder.append("\n");
        }*/
        return stringBuilder.toString();
    }
}
