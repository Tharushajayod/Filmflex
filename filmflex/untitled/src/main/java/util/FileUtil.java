package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    // Use your specific absolute path
    private static final String BASE_PATH = "E:\\SLIIT\\Year\\Year 01\\Semester 02\\Object Oriented Programming SE1020\\Assignment - Project\\03\\FIixGo\\untitled\\src\\main\\resources\\data\\";
    private static final String USERS_FILE = "users.txt";
    private static final String ADMINS_FILE = "admins.txt";
    private static final String PAYMENTS_FILE = "payments.txt";
    private static final String TICKETS_FILE = "tickets.txt";

    static {
        // Ensure data directory and files exist on class initialization
        try {
            File dir = new File(BASE_PATH);
            if (!dir.exists()) {
                boolean dirsCreated = dir.mkdirs();
                if (!dirsCreated) {
                    throw new IOException("Failed to create directories: " + BASE_PATH);
                }
            }

            // Initialize all data files if they don't exist
            createFileIfNotExists(PAYMENTS_FILE);
            createFileIfNotExists(USERS_FILE);
            createFileIfNotExists(ADMINS_FILE);
            createFileIfNotExists(TICKETS_FILE);

            // Initialize admin data
            initializeAdminData();
        } catch (IOException e) {
            System.err.println("Error initializing data directory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createFileIfNotExists(String filename) throws IOException {
        File file = new File(BASE_PATH + filename);
        if (!file.exists()) {
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) {
                throw new IOException("Failed to create file: " + filename);
            }
        }
    }

    // Payment data methods
    public static List<String> readAllPaymentLines() throws IOException {
        return readAllLines(PAYMENTS_FILE);
    }

    public static void writeAllPaymentLines(List<String> lines) throws IOException {
        writeAllLines(PAYMENTS_FILE, lines);
    }

    // User data methods
    public static List<String> readAllLines() throws IOException {
        return readAllLines(USERS_FILE);
    }

    public static void writeAllLines(List<String> lines) throws IOException {
        writeAllLines(USERS_FILE, lines);
    }

    // Add these methods to handle ticket data
    public static List<String> readAllTicketLines() throws IOException {
        return readAllLines(TICKETS_FILE);
    }

    public static void writeAllTicketLines(List<String> lines) throws IOException {
        writeAllLines(TICKETS_FILE, lines);
    }

    // Generic file methods
    public static List<String> readAllLines(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        File file = new File(BASE_PATH + filename);

        if (!file.exists()) {
            return lines; // Return empty list for non-existent file
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    public static void writeAllLines(String filename, List<String> lines) throws IOException {
        File file = new File(BASE_PATH + filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    // Admin specific methods
    public static void initializeAdminData() throws IOException {
        File file = new File(BASE_PATH + ADMINS_FILE);
        List<String> adminLines = readAllLines(ADMINS_FILE);

        if (adminLines.isEmpty()) {
            // Create default admin account only if file is empty
            List<String> defaultAdmins = new ArrayList<>();
            defaultAdmins.add("admin,admin123,superadmin"); // username,password,role
            writeAllLines(ADMINS_FILE, defaultAdmins);
        }
    }

    public static String getFilePath() {
        return BASE_PATH + USERS_FILE;
    }

    public static String getAdminFilePath() {
        return BASE_PATH + ADMINS_FILE;
    }

    public static String getPaymentsFilePath() {
        return BASE_PATH + PAYMENTS_FILE;
    }

    public static String getTicketsFilePath() {
        return BASE_PATH + TICKETS_FILE;
    }
}