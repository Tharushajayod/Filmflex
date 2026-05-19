package controller;

import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/delete-account")
public class DeleteAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User currentUser = ServletHelper.currentUser(request);
        if (currentUser == null) {
            ServletHelper.error(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        // Trigger account clearing pipeline loop safely
        boolean performanceFlush = UserController.deleteUser(currentUser.getEmail());

        if (performanceFlush) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // Clear login token sequence blocks
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Account drop operation failed");
        }
    }
}