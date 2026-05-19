package service;

import model.User;
import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public boolean registerUser(User user) throws IOException {
        if (user == null || isBlank(user.getFullName()) || isBlank(user.getEmail()) || isBlank(user.getPassword())) return false;
        List<User> users = getAllUsers();
        for (User existing : users) {
            if (existing.getEmail().equalsIgnoreCase(user.getEmail())) return false;
        }
        List<String> lines = FileUtil.readAllLines();
        lines.add(user.toString());
        FileUtil.writeAllLines(lines);
        return true;
    }

    public User authenticateUser(String email, String password) throws IOException {
        if (isBlank(email) || password == null) return null;
        for (User user : getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email.trim()) && user.getPassword().equals(password)) return user;
        }
        return null;
    }

    public boolean updateUser(String email, String fullName, String newPassword, String phone) throws IOException {
        if (isBlank(email) || isBlank(fullName)) return false;
        List<User> users = getAllUsers();
        boolean updated = false;
        List<String> lines = new ArrayList<>();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email.trim())) {
                user.setFullName(fullName.trim());
                user.setPhone(phone == null ? "" : phone.trim());
                if (!isBlank(newPassword)) user.setPassword(newPassword);
                updated = true;
            }
            lines.add(user.toString());
        }
        if (updated) FileUtil.writeAllLines(lines);
        return updated;
    }

    // ── FIXED: Assured exact signature mapping matching the controller segment ──
    public boolean deleteUser(String email) throws IOException {
        if (isBlank(email)) return false;
        List<User> users = getAllUsers();
        List<String> lines = new ArrayList<>();
        boolean deleted = false;
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email.trim())) {
                deleted = true;
            } else {
                lines.add(user.toString());
            }
        }
        if (deleted) FileUtil.writeAllLines(lines);
        return deleted;
    }

    public List<User> getAllUsers() throws IOException {
        List<User> users = new ArrayList<>();
        for (String line : FileUtil.readAllLines()) {
            User user = User.fromString(line);
            if (user != null) users.add(user);
        }
        return users;
    }

    public User findByEmail(String email) throws IOException {
        if (isBlank(email)) return null;
        for (User user : getAllUsers()) if (user.getEmail().equalsIgnoreCase(email.trim())) return user;
        return null;
    }

    private boolean isBlank(String value) { return value == null || value.trim().isEmpty(); }
}