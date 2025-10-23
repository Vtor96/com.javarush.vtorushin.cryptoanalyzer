import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public final class FileManager {
    private FileManager() {}

    public static String readAll(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                content.append(buffer, 0, read);
            }
        }
        return content.toString();
    }

    public static void writeAll(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {
            writer.write(content == null ? "" : content);
        }
    }

    public static void writeBruteforceOutputs(String outputDir, String baseName, Map<Integer, String> keyToText) throws IOException {
        Path dir = Paths.get(outputDir);
        Files.createDirectories(dir);
        for (Map.Entry<Integer, String> entry : keyToText.entrySet()) {
            int key = entry.getKey();
            String content = entry.getValue();
            String fileName = String.format("%s.key%02d.txt", baseName, key);
            Path outPath = dir.resolve(fileName);
            try (BufferedWriter writer = Files.newBufferedWriter(
                    outPath,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                writer.write(content == null ? "" : content);
            }
        }
    }

    public static String baseNameWithoutExtension(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int dot = fileName.lastIndexOf('.');
        return dot == -1 ? fileName : fileName.substring(0, dot);
    }
}


