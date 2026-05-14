package model;

import java.io.Serializable;

public class User implements Serializable {
    private String fullName;
    private String email;
    private String password;

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return String.join(",", safe(fullName), safe(email), safe(password));
    }

    public static User fromString(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(",", -1);
        if (parts.length < 3) return null;
        return new User(parts[0].trim(), parts[1].trim(), parts[2].trim());
    }

    private static String safe(String value) {
        return value == null ? "" : value.replace(",", " ").trim();
    }
}
