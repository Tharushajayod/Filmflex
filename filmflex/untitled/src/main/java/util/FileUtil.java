package util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
    private static final String USER_FILE = "users.txt";
    private static final String ADMIN_FILE = "admins.txt";
    private static final String PAYMENT_FILE = "payments.txt";
    private static final String TICKET_FILE = "tickets.txt";
    private static final String MOVIE_FILE = "movies.txt";

    private static final Path DATA_DIR = resolveDataDirectory();

    static {
        try {
            initializeDataFiles();
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static List<String> readAllPaymentLines() throws IOException { return readAllLines(getPaymentsFilePath()); }
    public static void writeAllPaymentLines(List<String> lines) throws IOException { writeAllLines(getPaymentsFilePath(), lines); }
    public static List<String> readAllLines() throws IOException { return readAllLines(getFilePath()); }
    public static void writeAllLines(List<String> lines) throws IOException { writeAllLines(getFilePath(), lines); }
    public static List<String> readAllTicketLines() throws IOException { return readAllLines(getTicketsFilePath()); }
    public static void writeAllTicketLines(List<String> lines) throws IOException { writeAllLines(getTicketsFilePath(), lines); }
    public static List<String> readAllMovieLines() throws IOException { return readAllLines(getMoviesFilePath()); }
    public static void writeAllMovieLines(List<String> lines) throws IOException { writeAllLines(getMoviesFilePath(), lines); }

    public static List<String> readAllLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        ensureFile(path, null);
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    public static void writeAllLines(String filePath, List<String> lines) throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, lines == null ? new ArrayList<>() : lines, StandardCharsets.UTF_8);
    }

    public static void initializeAdminData() throws IOException {
        Path admin = DATA_DIR.resolve(ADMIN_FILE);
        ensureFile(admin, Arrays.asList("admin,admin12,superadmin", "admin1,admin123,admin"));
    }

    public static String getFilePath() { return DATA_DIR.resolve(USER_FILE).toString(); }
    public static String getAdminFilePath() { return DATA_DIR.resolve(ADMIN_FILE).toString(); }
    public static String getPaymentsFilePath() { return DATA_DIR.resolve(PAYMENT_FILE).toString(); }
    public static String getTicketsFilePath() { return DATA_DIR.resolve(TICKET_FILE).toString(); }
    public static String getMoviesFilePath() { return DATA_DIR.resolve(MOVIE_FILE).toString(); }

    private static Path resolveDataDirectory() {
        String configured = System.getProperty("filmflex.data.dir");
        if (configured != null && !configured.trim().isEmpty()) return Paths.get(configured.trim());

        URL usersResource = FileUtil.class.getClassLoader().getResource("data/" + USER_FILE);
        if (usersResource != null && "file".equalsIgnoreCase(usersResource.getProtocol())) {
            try {
                URI uri = usersResource.toURI();
                Path resourceFile = Paths.get(uri);
                if (resourceFile.getParent() != null) return resourceFile.getParent();
            } catch (URISyntaxException ignored) { }
        }

        return Paths.get(System.getProperty("user.home"), ".filmflex-data");
    }

    private static void initializeDataFiles() throws IOException {
        Files.createDirectories(DATA_DIR);
        ensureFile(DATA_DIR.resolve(USER_FILE), Arrays.asList(
                "Karcer,karcer@gmail.com,23135rv@",
                "Rasith Viranga,rasith.viranga200@gmail.com,0412245720RV"
        ));
        ensureFile(DATA_DIR.resolve(ADMIN_FILE), Arrays.asList("admin,admin12,superadmin", "admin1,admin123,admin"));
        ensureFile(DATA_DIR.resolve(PAYMENT_FILE), new ArrayList<>());
        ensureFile(DATA_DIR.resolve(TICKET_FILE), new ArrayList<>());
        ensureFile(DATA_DIR.resolve(MOVIE_FILE), Arrays.asList(
                "1|The Witcher|2019|8.2|Fantasy|Fantasy movie|assets/image/default-poster.jpg|assets/Video/Trailer.mp4|assets/Video/Trailer.mp4",
                "2|Stranger Things|2016|8.7|Sci-Fi|Sci-fi mystery series|assets/image/default-poster.jpg|assets/Video/Trailer2 .mp4|assets/Video/Trailer2 .mp4"
        ));
    }

    private static void ensureFile(Path path, List<String> defaultLines) throws IOException {
        Files.createDirectories(path.getParent());
        if (!Files.exists(path)) {
            Files.write(path, defaultLines == null ? new ArrayList<>() : defaultLines, StandardCharsets.UTF_8);
        }
    }
}
