package monProjet.fromScratch.service.secureKey;

public class RandomSecureKey {
    private static String randomSecureKey;

    public static String getRandomSecureKey() {
        if(randomSecureKey == null){ setRandomSecureKey(); }
        return randomSecureKey; }

    public static void setRandomSecureKey(){
        int firstNumber = (int) (1 * Math.random() * 10);
        int secondNumber = (int) (1 * Math.random() * 10);
        int thirdNumber = (int) (1 * Math.random() * 10);

        // character between 91 and 121

        int firstIntCaracter = (91 + (int) (Math.random() * 10 * 3));
        int secondIntCaracter = (91 + (int) (Math.random() * 10 * 3));
        int thirdIntCaracter = (91 + (int) (Math.random() * 10 * 3));
        int forthIntCaracter = (91 + (int) (Math.random() * 10 * 3));

        char firstCharCaracter = (char) firstIntCaracter;
        char secondCharCaracter = (char) secondIntCaracter;
        char thirdCharCaracter = (char) thirdIntCaracter;
        char forthCharCaracter = (char) forthIntCaracter;

        int forthNumber = (int) (1 * Math.random() * 10);
        int fifthNumber = (int) (1 * Math.random() * 10);
        int sixthNumber = (int) (1 * Math.random() * 10);

        String s = firstNumber + "" + secondNumber + "" + thirdNumber + firstCharCaracter + secondCharCaracter
                + thirdCharCaracter + forthCharCaracter + forthNumber + "" + fifthNumber + "" + sixthNumber;

        randomSecureKey = s;
    }
}
