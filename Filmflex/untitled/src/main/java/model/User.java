package model;

import java.io.Serializable;

public class User implements Serializable {
    private String fullName;
    private String email;
    private String password;
    private String phone; // Added the phone field structure

    public User(String fullName, String email, String password, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone == null ? "" : phone.trim();
    }

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        // Appends the phone sequence safely dynamically into text stream lines
        return String.join(",", safe(fullName), safe(email), safe(password), safe(phone));
    }

    public static User fromString(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(",", -1);
        if (parts.length < 3) return null;

        // If phone chunk doesn't exist on older user lines, fallback to empty string safely
        String phoneChunk = parts.length >= 4 ? parts[3].trim() : "";
        return new User(parts[0].trim(), parts[1].trim(), parts[2].trim(), phoneChunk);
    }

    private static String safe(String value) {
        return value == null ? "" : value.replace(",", " ").trim();
    }
}