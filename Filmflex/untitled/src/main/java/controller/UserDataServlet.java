package controller;

import model.User;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user-data")
public class UserDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = ServletHelper.currentUser(request);
        if (user == null) {
            ServletHelper.error(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }
        JSONObject obj = new JSONObject();
        obj.put("fullName", user.getFullName());
        obj.put("email", user.getEmail());
        obj.put("phone", user.getPhone()); // Fixed: Sends active phone token parameters directly
        ServletHelper.json(response, obj.toString());
    }
}