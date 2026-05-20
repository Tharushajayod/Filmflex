package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
    // Definitive schema filename string mappings for all relational domain assets rows
    private static final String USER_FILE = "users.txt";
    private static final String ADMIN_FILE = "admins.txt";
    private static final String PAYMENT_FILE = "payments.txt";
    private static final String TICKET_FILE = "tickets.txt";
    private static final String MOVIE_FILE = "movies.txt";
    private static final String REVIEW_FILE = "reviews.txt";

    // Mapped directly to an absolute safe OS directory path to ensure data cross-sharing is permanent across server reboots
    private static final Path DATA_DIR = resolveDataDirectory();

    static {
        try {
            initializeDataFiles();
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    // ── CENTRALIZED READ/WRITE ROUTING PIPELINES FOR DOMAIN MODELS ──
    public static List<String> readAllPaymentLines() throws IOException {
        return readAllLines(getPaymentsFilePath()); }
    public static void writeAllPaymentLines(List<String> lines) throws IOException {
        writeAllLines(getPaymentsFilePath(), lines); }
    public static List<String> readAllLines() throws IOException {
        return readAllLines(getFilePath()); }
    public static void writeAllLines(List<String> lines) throws IOException {
        writeAllLines(getFilePath(), lines); }
    public static List<String> readAllTicketLines() throws IOException {
        return readAllLines(getTicketsFilePath()); }
    public static void writeAllTicketLines(List<String> lines) throws IOException {
        writeAllLines(getTicketsFilePath(), lines); }
    public static List<String> readAllMovieLines() throws IOException {
        return readAllLines(getMoviesFilePath()); }
    public static void writeAllMovieLines(List<String> lines) throws IOException {
        writeAllLines(getMoviesFilePath(), lines); }
    public static List<String> readAllReviewLines() throws IOException {
        return readAllLines(getReviewsFilePath()); }
    public static void writeAllReviewLines(List<String> lines) throws IOException {
        writeAllLines(getReviewsFilePath(), lines); }

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

    /**
     * ADMINISTRATIVE BUSINESS RULES DATA SEEDER: Forces default credential allocation if admins logs are dropped.
     */
    public static void initializeAdminData() throws IOException {
        Path admin = DATA_DIR.resolve(ADMIN_FILE);
        ensureFile(admin, Arrays.asList(
                "admin,admin12,superadmin",
                "admin1,admin123,admin")
        );
    }

    // ── ABSOLUTE STRING STRATEGY PATH GETTERS RESOLVING LOGICAL RESOURCE LOOKUPS ──
    public static String getFilePath()       { return DATA_DIR.resolve(USER_FILE).toString(); }
    public static String getAdminFilePath()  { return DATA_DIR.resolve(ADMIN_FILE).toString(); }
    public static String getPaymentsFilePath() { return DATA_DIR.resolve(PAYMENT_FILE).toString(); }
    public static String getTicketsFilePath()  { return DATA_DIR.resolve(TICKET_FILE).toString(); }
    public static String getMoviesFilePath()   { return DATA_DIR.resolve(MOVIE_FILE).toString(); }
    public static String getReviewsFilePath()  { return DATA_DIR.resolve(REVIEW_FILE).toString(); }

    /**
     * SYSTEM DATA DIRECTORY LOCATION RESOLVER: Determines where file assets blocks reside permanently.
     * Evaluates dynamic system property arguments with a solid fallback to a hardcoded local Windows operating partition path.
     */
    private static Path resolveDataDirectory() {
        String configured = System.getProperty("filmflex.data.dir");
        if (configured != null && !configured.trim().isEmpty()) return Paths.get(configured.trim());

        // Hardcoding a permanent absolute layout partition directory pathing framework coordinate
        Path absolutePath = Paths.get("D:", "My Projects", "Filmflex 2.2", "Filmflex", "untitled", "src", "main", "resources", "data");
        return absolutePath;
    }

    /**
     * SYSTEM DATABASE INITIALIZATION WORKFLOW: Bootstraps complete application folders structures
     * and seeds initial demo database rows datasets elements matrices to allow the web platform to execute testing immediately.
     */
    private static void initializeDataFiles() throws IOException {
        Files.createDirectories(DATA_DIR); // Enforcing structural directories presence checks early

        // Seeding baseline registration parameters accounts rows profiles
        ensureFile(DATA_DIR.resolve(USER_FILE), Arrays.asList(
                "user1,user1@gmail.com,user1@123",
                "user2,user2@gmail.com,user2@123",
                "tharusha,tharushajayod@gmail.com,tharusha@123",
                "sanilka,sanilka@gmail.com,sanilka@123",
                "yohan,yohan@gmail.com,yohan@123",
                "hasandi,hasandi@gmail.com,hasandi@123",
                "viraj,viraj@gmail.com,viraj@123",
                "thathsara,thathsara@gmail.com,thathsara@123"
        ));
        ensureFile(DATA_DIR.resolve(ADMIN_FILE), Arrays.asList(
                "admin,admin12,superadmin",
                "admin1,admin123,admin"
        ));
        ensureFile(DATA_DIR.resolve(PAYMENT_FILE),
                new ArrayList<>()
        );
        ensureFile(DATA_DIR.resolve(TICKET_FILE),
                new ArrayList<>()
        );
        ensureFile(DATA_DIR.resolve(MOVIE_FILE), Arrays.asList(
                "1|The Witcher|2019|8.2|Fantasy|Fantasy movie|assets/image/default-poster.jpg|assets/Video/Trailer.mp4|assets/Video/Trailer.mp4",
                "2|Stranger Things|2016|8.7|Sci-Fi|Sci-fi mystery series|assets/image/1.jpg|assets/Video/Trailer2 .mp4|assets/Video/Trailer2 .mp4",
                "3|The Witcher|2012|7.2|Fantasy|Fantasy movie|assets/image/2.jpg|assets/Video/Trailer3.mp4|assets/Video/Trailer3.mp4",
                "4|The Witcher|2021|6.5|Fantasy|Fantasy movie|assets/image/3.jpg|assets/Video/Trailer.mp4|assets/Video/Trailer.mp4",
                "5|The Witcher|2025|8.9|Fantasy|Fantasy movie|assets/image/5.jpg|assets/Video/Trailer2.mp4|assets/Video/Trailer2.mp4",
                "6|The Witcher|2022|9.2|Fantasy|Fantasy movie|assets/image/8.jpg|assets/Video/Trailer3.mp4|assets/Video/Trailer3.mp4",
                "7|The Witcher|2018|7.6|Fantasy|Fantasy movie|assets/image/10.jpg|assets/Video/Trailer.mp4|assets/Video/Trailer.mp4",
                "8|The Witcher|2019|8.8|Fantasy|Fantasy movie|assets/image/12.jpg|assets/Video/Trailer2.mp4|assets/Video/Trailer2.mp4"
        ));
        ensureFile(DATA_DIR.resolve(REVIEW_FILE),
                new ArrayList<>()
        );
    }

    /**
     * Checks if a target file exists on physical storage.
     * If the file is missing, it dynamically manufactures it and injects standard seed collections metadata lines.
     */
    private static void ensureFile(Path path, List<String> defaultLines) throws IOException {
        Files.createDirectories(path.getParent());
        if (!Files.exists(path)) {
            // Manufacturing file entity and applying initial configuration seed text logs lines
            Files.write(path, defaultLines == null ? new ArrayList<>() : defaultLines, StandardCharsets.UTF_8);
        }
    }
}