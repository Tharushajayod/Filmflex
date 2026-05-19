package util; // Placed inside the foundational utility management package layer

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Foundational Infrastructure Utility class serving as the core Flat-File Database Engine.
 * Manages physical disk input/output streams, implements defensive directory bootstrapping,
 * enforces uniform UTF-8 text encoding, and acts as the singular source of truth for file path coordinate mappings.
 */
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

    /**
     * STATIC INITIALIZATION BLOCK: Triggers automatically exactly once when the JVM loads this class into memory.
     * Guarantees that all required physical database text files exist before any service or servlet attempts a read/write operation.
     */
    static {
        try {
            // Bootstrapping the flat-file database system storage components boundaries instantly
            initializeDataFiles();
        } catch (IOException e) {
            // Trapping raw I/O initialization failures and escalating into a critical JVM initializer error stack
            throw new ExceptionInInitializerError(e);
        }
    }

    // ── CENTRALIZED READ/WRITE ROUTING PIPELINES FOR DOMAIN MODELS ──
    public static List<String> readAllPaymentLines() throws IOException { return readAllLines(getPaymentsFilePath()); }
    public static void writeAllPaymentLines(List<String> lines) throws IOException { writeAllLines(getPaymentsFilePath(), lines); }
    public static List<String> readAllLines() throws IOException { return readAllLines(getFilePath()); }
    public static void writeAllLines(List<String> lines) throws IOException { writeAllLines(getFilePath(), lines); }
    public static List<String> readAllTicketLines() throws IOException { return readAllLines(getTicketsFilePath()); }
    public static void writeAllTicketLines(List<String> lines) throws IOException { writeAllLines(getTicketsFilePath(), lines); }
    public static List<String> readAllMovieLines() throws IOException { return readAllLines(getMoviesFilePath()); }
    public static void writeAllMovieLines(List<String> lines) throws IOException { writeAllLines(getMoviesFilePath(), lines); }
    public static List<String> readAllReviewLines() throws IOException { return readAllLines(getReviewsFilePath()); }
    public static void writeAllReviewLines(List<String> lines) throws IOException { writeAllLines(getReviewsFilePath(), lines); }

    /**
     * CORE FILE READ TRANSACTION: Pulls raw text records from a target disk location.
     * Explicitly enforces StandardCharsets.UTF_8 validation rules to prevent string parsing errors.
     */
    public static List<String> readAllLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        // Defensively assert the file existence boundary before triggering streaming operations
        ensureFile(path, null);
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    /**
     * CORE FILE WRITE TRANSACTION: Overwrites or flushes a string lines list onto the local hard drive storage.
     * Automatically creates parent directories dynamically if they have been altered or pruned.
     */
    public static void writeAllLines(String filePath, List<String> lines) throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent()); // Guarantee path directories array layouts remain intact
        // Null-Defensive fallback allocation: If list points to a null pointer, safely commit a fresh empty collection array list
        Files.write(path, lines == null ? new ArrayList<>() : lines, StandardCharsets.UTF_8);
    }

    /**
     * ADMINISTRATIVE BUSINESS RULES DATA SEEDER: Forces default credential allocation if admins logs are dropped.
     */
    public static void initializeAdminData() throws IOException {
        Path admin = DATA_DIR.resolve(ADMIN_FILE);
        ensureFile(admin, Arrays.asList("admin,admin12,superadmin", "admin1,admin123,admin"));
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
        // This eliminates 404 resource drops completely regardless of dynamic deployment configurations context shifts
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
                "Karcer,karcer@gmail.com,23135rv@",
                "Rasith Viranga,rasith.viranga200@gmail.com,0412245720RV"
        ));
        ensureFile(DATA_DIR.resolve(ADMIN_FILE), Arrays.asList("admin,admin12,superadmin", "admin1,admin123,admin"));
        ensureFile(DATA_DIR.resolve(PAYMENT_FILE), new ArrayList<>());
        ensureFile(DATA_DIR.resolve(TICKET_FILE), new ArrayList<>());

        // Seeding operational pipe-delimited movie datasets traits attributes to populate landing page cards maps
        ensureFile(DATA_DIR.resolve(MOVIE_FILE), Arrays.asList(
                "1|The Witcher|2019|8.2|Fantasy|Fantasy movie|assets/image/default-poster.jpg|assets/Video/Trailer.mp4|assets/Video/Trailer.mp4",
                "2|Stranger Things|2016|8.7|Sci-Fi|Sci-fi mystery series|assets/image/default-poster.jpg|assets/Video/Trailer2 .mp4|assets/Video/Trailer2 .mp4"
        ));
        ensureFile(DATA_DIR.resolve(REVIEW_FILE), new ArrayList<>());
    }

    /**
     * DEFENSIVE PERSISTENCE GUARD: Checks if a target file exists on physical storage.
     * If the file is missing, it dynamically manufactures it and injects standard seed collections metadata lines.
     */
    private static void ensureFile(Path path, List<String> defaultLines) throws IOException {
        Files.createDirectories(path.getParent()); // Insulating parent directory layouts positions safely
        if (!Files.exists(path)) {
            // File not discovered: Manufacturing file entity and applying initial configuration seed text logs lines
            Files.write(path, defaultLines == null ? new ArrayList<>() : defaultLines, StandardCharsets.UTF_8);
        }
    }
}