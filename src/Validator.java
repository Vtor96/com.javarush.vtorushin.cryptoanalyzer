import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Validator {
    private Validator() {}

    public static void ensureFileReadable(String filePath) {
        Path p = Paths.get(filePath);
        if (!Files.exists(p)) {
            throw new IllegalArgumentException("Файл не существует: " + filePath);
        }
        if (!Files.isRegularFile(p)) {
            throw new IllegalArgumentException("Не является обычным файлом: " + filePath);
        }
        if (!Files.isReadable(p)) {
            throw new IllegalArgumentException("Файл недоступен для чтения: " + filePath);
        }
    }

    public static void ensureDirectoryExists(String dirPath) throws IOException {
        Path p = Paths.get(dirPath);
        if (Files.exists(p) && !Files.isDirectory(p)) {
            throw new IllegalArgumentException("Не является директорией: " + dirPath);
        }
        Files.createDirectories(p);
    }

    public static void validateMode(int mode) {
        if (mode < 1 || mode > 3) {
            throw new IllegalArgumentException("Неизвестный режим: " + mode);
        }
    }
}


