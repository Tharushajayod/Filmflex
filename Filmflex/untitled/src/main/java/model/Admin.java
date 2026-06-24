package model;

import java.io.Serializable;

public class Admin implements Serializable {

    private String username;
    private String password;
    private String role;
    private String lastLogin;

    public Admin(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role == null || role.trim().isEmpty() ? "admin" : role.trim();
    }

    // ── Encapsulation Interface Layer Access Methods (Getters & Setters) ──
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getLastLogin() { return lastLogin; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }

    public String toFileString() {
        return String.join(",", safe(username), safe(password), safe(role), safe(lastLogin));
    }

    public static Admin fromString(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(",", -1);

        if (parts.length < 3) return null;

        Admin admin = new Admin(parts[0].trim(), parts[1].trim(), parts[2].trim());

        if (parts.length > 3) {
            admin.setLastLogin(parts[3].trim());
        }

        return admin;
    }

    private static String safe(String value) {
        return value == null ? "" : value.replace(",", " ").trim();
    }
}