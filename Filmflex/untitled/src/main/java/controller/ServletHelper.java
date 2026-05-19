package controller; // Placed inside the core controller package layer

import model.Admin;
import model.User;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Shared Architectural Utility class separating core web concerns from dynamic controller endpoints.
 * Centralizes re-usable processing logic workflows such as input JSON payload streaming,
 * standardized HTTP web response rendering, and dynamic session token authorization validations.
 */
public class ServletHelper {

    /**
     * READ Operation Helper: Ingests raw characters streams from the inbound client request buffer
     * and compiles it securely into an instanced JSONObject map layout dictionary.
     * Demonstrates safe try-with-resources structural automatic connection closing.
     */
    public static JSONObject readJson(HttpServletRequest request) throws IOException {
        StringBuilder body = new StringBuilder();

        // Java 7 Try-With-Resources: Automatically invokes .close() on BufferedReader to prevent server memory leak defects
        try (BufferedReader reader = request.getReader()) {
            String line;
            // Interrogating the input data stream buffer sequentially line by line
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        }

        // Defensive Operational Fallback Strategy: If stream length evaluates empty, return blank dictionary instead of throwing exception
        return body.length() == 0 ? new JSONObject() : new JSONObject(body.toString());
    }

    /**
     * presentation RESPONSE Helper: Standardizes HTTP transport configurations across all servlet endpoints.
     * Enforces explicit Content-Type header boundaries before flushing payloads back onto the client web browser grid.
     */
    public static void json(HttpServletResponse response, Object payload) throws IOException {
        // Applying strict JSON MIME structural communication standards parameters layouts settings
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Writing the explicit string data representation parameter back into the downstream HTTP response loop
        response.getWriter().write(String.valueOf(payload));
    }

    /**
     * Centralized Error Handling Rule Interceptor: Generates uniform serialized API exception responses blocks.
     * Maps error messages parameters directly to client validation components while modifying response status codes safely.
     */
    public static void error(HttpServletResponse response, int status, String message) throws IOException {
        // Controlling systemic execution status codes maps (e.g., mapping HTTP 401, 400, or 404 boundaries traits)
        response.setStatus(status);

        JSONObject obj = new JSONObject();
        obj.put("error", message);
        obj.put("message", message); // Mapping mirrored keys structures to secure high cross-platform client compatibility

        // Invoking the native json utility channel to transmit exception descriptors seamlessly
        json(response, obj.toString());
    }

    /**
     * Session Authentication Extractor: Queries the server state persistence memories lookup index maps
     * to safely confirm user validation contexts structures without exposing background dependencies.
     */
    public static User currentUser(HttpServletRequest request) {
        // Looking up active tracking cookies sessions blocks passing false to avoid instantiating clean blank instances
        HttpSession session = request.getSession(false);
        Object user = session == null ? null : session.getAttribute("user");

        // Polymorphic Safe Downcasting: Verifying runtime structural object types integrity barriers via instanceof check
        return user instanceof User ? (User) user : null;
    }

    /**
     * Administrative Authentication Extractor: Extracts the active server session context
     * to verify the operational administrative model identity instance maps density variables.
     */
    public static Admin currentAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object admin = session == null ? null : session.getAttribute("admin");

        // Asserting underlying model data type architecture structures before invoking explicit conversions casting
        return admin instanceof Admin ? (Admin) admin : null;
    }

    /**
     * Access Control Security Barrier: Protects customer endpoints workflows early from general guest traffic.
     * Generates standardized RESTful unauthorized parameter drops instantly upon validation failures.
     * @return boolean true if validation traits check verifies user identity successfully
     */
    public static boolean requireUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (currentUser(request) == null) {
            // Guard clause interception: Terminates flow control using standard HTTP 401 Status configurations parameters
            error(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return false; // Tells calling servlet node execution loop to abort further processing immediately
        }
        return true;
    }

    /**
     * Systemic Operator Security Barrier: Restricts administrative structural dashboards mutations operations
     * from cross-access exposures by enforcing early credential verification checks layout rules.
     */
    public static boolean requireAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (currentAdmin(request) == null) {
            // Terminating insecure external hits attempts mapping custom system logging strings metrics responses
            error(response, HttpServletResponse.SC_UNAUTHORIZED, "Admin login required");
            return false;
        }
        return true;
    }
}