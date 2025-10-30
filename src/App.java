import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

public final class App {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8)) {
            System.out.println("Выберите режим: \n1 - шифрование\n2 - дешифрование\n3 - brute force");
            int mode = Integer.parseInt(sc.nextLine().trim());
            Validator.validateMode(mode);

            System.out.print("Введите путь к файлу: ");
            String fileIn = sc.nextLine().trim();
            Validator.ensureFileReadable(fileIn);

            String text = FileManager.readAll(fileIn).toLowerCase();

            if (mode == 1 || mode == 2) {
                System.out.print("Введите ключ (целое число): ");
                int key = Integer.parseInt(sc.nextLine().trim());
                String result = (mode == 1) ? Cipher.encrypt(text, key) : Cipher.decrypt(text, key);

                System.out.print("Введите путь для сохранения результата: ");
                String fileOut = sc.nextLine().trim();
                FileManager.writeAll(fileOut, result);
                System.out.println("Готово! Результат сохранён в " + fileOut);
            } else {
                System.out.print("Введите директорию для сохранения результатов по всем ключам: ");
                String outDir = sc.nextLine().trim();
                Validator.ensureDirectoryExists(outDir);

                Map<Integer, String> allAttempts = BruteForce.decryptAllKeys(text);
                String baseName = FileManager.baseNameWithoutExtension(fileIn);
                FileManager.writeBruteforceOutputs(outDir, baseName, allAttempts);

                Map.Entry<Integer, String> best = BruteForce.findBest(allAttempts);
                if (best != null) {
                    Path bestPath = Paths.get(outDir).resolve(String.format("%s.key%02d.txt", baseName, best.getKey()));
                    Files.writeString(bestPath, best.getValue(), StandardCharsets.UTF_8);
                    System.out.println("Вероятный ключ: " + best.getKey());
                    System.out.println("Лучший результат обновлён: " + bestPath);
                }
                System.out.println("Готово! Сохранены файлы для всех ключей в " + outDir);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}
