import java.util.Locale;

public final class Cipher {
    public static final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,“”:-!? ";
    private static final int ALPHABET_SIZE = ALPHABET.length();

    private Cipher() {}

    public static String encrypt(String text, int key) {
        if (text == null || text.isEmpty()) return "";
        StringBuilder result = new StringBuilder(text.length());
        for (char c : text.toLowerCase(Locale.ROOT).toCharArray()) {
            int idx = ALPHABET.indexOf(c);
            if (idx != -1) {
                int newIdx = (idx + key) % ALPHABET_SIZE;
                if (newIdx < 0) newIdx += ALPHABET_SIZE;
                result.append(ALPHABET.charAt(newIdx));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, int key) {
        return encrypt(text, -key);
    }
}
