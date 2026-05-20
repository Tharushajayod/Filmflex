package controller.auth;

import controller.ServletHelper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // State Persistence Management: Interrogating active server memory for pre-existing session tokens
        // Passing 'false' prevents the container from initializing an unnecessary blank session instance
        if (request.getSession(false) != null) {

            // Session Invalidation: Destructing the active session context and unbinding all cached attributes
            request.getSession(false).invalidate();
        }

        // Compiling a standard serialized JSON string token to notify the frontend AJAX script of a successful exit
        String jsonResponse = new org.json.JSONObject()
                .put("message", "Logged out successfully")
                .toString();

        // Transmitting the structured transaction completion parameters back to the browser interface layer
        ServletHelper.json(response, jsonResponse);
    }
}