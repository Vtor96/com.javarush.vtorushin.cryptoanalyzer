import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public final class BruteForce {
    private BruteForce() {}

    public static Map<Integer, String> decryptAllKeys(String cipherText) {
        Map<Integer, String> results = new HashMap<>();
        int alphabetSize = Cipher.ALPHABET.length();
        for (int k = 1; k < alphabetSize; k++) {
            String attempt = Cipher.decrypt(cipherText, k);
            results.put(k, attempt);
        }
        return results;
    }

    public static Map.Entry<Integer, String> findBest(Map<Integer, String> keyToText) {
        int bestKey = -1;
        int maxScore = Integer.MIN_VALUE;
        String bestText = null;
        for (Map.Entry<Integer, String> e : keyToText.entrySet()) {
            int score = scoreText(e.getValue());
            if (score > maxScore) {
                maxScore = score;
                bestKey = e.getKey();
                bestText = e.getValue();
            }
        }
        return bestKey == -1 ? null : new AbstractMap.SimpleEntry<>(bestKey, bestText);
    }

    private static int scoreText(String text) {
        if (text == null || text.isEmpty()) return 0;
        int score = 0;
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) == ' ' && Character.isLetter(text.charAt(i - 1))) score += 3;
            char ch = text.charAt(i);
            if (",.:-!?".indexOf(ch) != -1) score += 1;
        }
        String[] commonBigrams = {"ст", "но", "ен", "то", "на", "ов", "ни", "ро", "ко", "ра"};
        for (String bigram : commonBigrams) {
            score += countOccurrences(text, bigram) * 2;
        }
        return score;
    }

    private static int countOccurrences(String haystack, String needle) {
        int count = 0;
        int idx = 0;
        while ((idx = haystack.indexOf(needle, idx)) != -1) {
            count++;
            idx += needle.length();
        }
        return count;
    }
}


