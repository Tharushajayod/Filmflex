package controller;

import model.Admin;
import model.User;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

class ServletHelper {
    static JSONObject readJson(HttpServletRequest request) throws IOException {
        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) body.append(line);
        }
        return body.length() == 0 ? new JSONObject() : new JSONObject(body.toString());
    }

    static void json(HttpServletResponse response, Object payload) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(payload));
    }

    static void error(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        JSONObject obj = new JSONObject();
        obj.put("error", message);
        obj.put("message", message);
        json(response, obj.toString());
    }

    static User currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object user = session == null ? null : session.getAttribute("user");
        return user instanceof User ? (User) user : null;
    }

    static Admin currentAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object admin = session == null ? null : session.getAttribute("admin");
        return admin instanceof Admin ? (Admin) admin : null;
    }

    static boolean requireUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (currentUser(request) == null) {
            error(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return false;
        }
        return true;
    }

    static boolean requireAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (currentAdmin(request) == null) {
            error(response, HttpServletResponse.SC_UNAUTHORIZED, "Admin login required");
            return false;
        }
        return true;
    }
}
