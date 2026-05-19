package controller.user; // Organized under the dedicated user management sub-package structure

import util.FileUtil;
import org.json.JSONObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;

/**
 * Servlet controller handling the permanent deletion of a user's own account.
 * Demonstrates HTTP POST request processing, session validation, and data stream parsing.
 */
@WebServlet("/delete-own-account")
public class DeleteAccountServlet extends HttpServlet {

    /**
     * Overridden HTTP POST handler managing secure profile deletion streams.
     * Restricts destructive mutations exclusively to verified active session owners.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Enforcing standard JSON content type responses for frontend asynchronous communication
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        // Extracting existing HTTP session context without initializing a new one
        HttpSession session = req.getSession(false);

        // Security Gatekeeping: Session Verification & Authentication Check
        if (session == null || session.getAttribute("user") == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"status\":\"error\",\"message\":\"Unauthorized access. Please log in.\"}");
            return;
        }

        try {
            // Ingesting the JSON payload streams directly from the Request Reader Buffer
            BufferedReader reader = req.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // Parsing the accumulated payload text string into a JSON Object framework
            JSONObject json = new JSONObject(sb.toString());
            String emailToDelete = json.getString("email").trim();

            // File Handling Logic: Reading current text records stored inside users.txt database file
            List<String> allUsers = FileUtil.readAllLines();
            List<String> updatedUsers = new ArrayList<>();
            boolean userFoundAndDeleted = false;

            // Iterating through file records to locate and remove target entity data stream segment
            for (String userLine : allUsers) {
                // Expected internal dataset delimiter structure layout notation style: FullName,email,password
                String[] parts = userLine.split(",");
                if (parts.length >= 2 && parts[1].trim().equalsIgnoreCase(emailToDelete)) {
                    // Entity payload matched: Skip addition to updated collections to fulfill deletion mechanics
                    userFoundAndDeleted = true;
                } else {
                    // Unmatched payload elements are retained inside the safe structural memory collection array
                    updatedUsers.add(userLine);
                }
            }

            if (userFoundAndDeleted) {
                // Rewriting the optimized updated collections payload back onto permanent disk space partition
                FileUtil.writeAllLines(updatedUsers);

                // Security Cleanup: Destructing the state session context variables to clear system cache
                session.invalidate();

                res.setStatus(HttpServletResponse.SC_OK);
                res.getWriter().write("{\"status\":\"success\",\"message\":\"Account successfully deleted.\"}");
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                res.getWriter().write("{\"status\":\"error\",\"message\":\"User email not found in records.\"}");
            }

        } catch (Exception e) {
            // Gracefully trapping serialization format mismatches or stream data parsing crashes
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("{\"status\":\"error\",\"message\":\"Invalid request structure.\"}");
        }
    }
}