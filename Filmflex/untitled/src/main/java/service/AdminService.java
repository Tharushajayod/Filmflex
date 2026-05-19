package service;

import model.Admin;
import util.FileUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminService {
    public AdminService() {
        try { FileUtil.initializeAdminData(); } catch (IOException ignored) { }
    }

    public Admin authenticateAdmin(String username, String password) {
        if (isBlank(username) || password == null) return null;
        try {
            for (Admin admin : getAllAdmins()) {
                if (admin.getUsername().equalsIgnoreCase(username.trim()) && admin.getPassword().equals(password)) {
                    admin.setLastLogin(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    updateAdmin(admin.getUsername(), admin);
                    return admin;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading admin database during login: " + e.getMessage());
        }
        return null;
    }

    public List<Admin> getAllAdmins() throws IOException {
        List<Admin> admins = new ArrayList<>();
        for (String line : FileUtil.readAllLines(FileUtil.getAdminFilePath())) {
            Admin admin = Admin.fromString(line);
            if (admin != null) admins.add(admin);
        }
        return admins;
    }

    public boolean addAdmin(Admin admin) throws IOException {
        if (admin == null || isBlank(admin.getUsername()) || isBlank(admin.getPassword())) return false;
        for (Admin existing : getAllAdmins()) {
            if (existing.getUsername().equalsIgnoreCase(admin.getUsername())) return false;
        }
        List<String> lines = FileUtil.readAllLines(FileUtil.getAdminFilePath());
        lines.add(admin.toFileString());
        FileUtil.writeAllLines(FileUtil.getAdminFilePath(), lines);
        return true;
    }

    public boolean updateAdmin(String originalUsername, Admin updatedAdmin) throws IOException {
        if (isBlank(originalUsername) || updatedAdmin == null || isBlank(updatedAdmin.getUsername())) return false;
        List<Admin> admins = getAllAdmins();
        for (Admin admin : admins) {
            if (!admin.getUsername().equalsIgnoreCase(originalUsername)
                    && admin.getUsername().equalsIgnoreCase(updatedAdmin.getUsername())) return false;
        }
        boolean updated = false;
        List<String> lines = new ArrayList<>();
        for (Admin admin : admins) {
            if (admin.getUsername().equalsIgnoreCase(originalUsername)) {
                if (isBlank(updatedAdmin.getPassword())) updatedAdmin.setPassword(admin.getPassword());
                if (isBlank(updatedAdmin.getLastLogin())) updatedAdmin.setLastLogin(admin.getLastLogin());
                lines.add(updatedAdmin.toFileString());
                updated = true;
            } else {
                lines.add(admin.toFileString());
            }
        }
        if (updated) FileUtil.writeAllLines(FileUtil.getAdminFilePath(), lines);
        return updated;
    }

    public boolean deleteAdmin(String username) throws IOException {
        if (isBlank(username)) return false;
        List<String> lines = new ArrayList<>();
        boolean deleted = false;
        for (Admin admin : getAllAdmins()) {
            if (admin.getUsername().equalsIgnoreCase(username.trim())) deleted = true;
            else lines.add(admin.toFileString());
        }
        if (deleted) FileUtil.writeAllLines(FileUtil.getAdminFilePath(), lines);
        return deleted;
    }

    private boolean isBlank(String value) { return value == null || value.trim().isEmpty(); }
}