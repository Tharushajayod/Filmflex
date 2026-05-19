package controller.movie; // Organized under the movie package container structure

import controller.ServletHelper;
import model.Payment;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller servlet handling user payment method configuration profiles.
 * Implements RESTful architecture constraints by utilizing HTTP GET (Read),
 * POST (Create/Update), and DELETE (Remove) operations on a single unified resource path.
 */
@WebServlet("/payment-methods")
public class PaymentServlet extends HttpServlet {

    /**
     * READ Operation: Intercepts HTTP GET requests to fetch a user's stored card profile parameters.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Access Control Gatekeeper: Verifies if the active request contains a valid customer session token context
        if (!ServletHelper.requireUser(request, response)) return;

        // Extracting target user email identifier from the request parameter attributes string
        String email = request.getParameter("email");

        // Interacting with the business logic layer to pull custom parsed payment records fields
        Payment payment = PaymentController.getPaymentByEmail(email);

        // If no matching profile exists in flat records, respond immediately with an empty JSON object layout string
        if (payment == null) {
            ServletHelper.json(response, new JSONObject().toString());
            return;
        }

        // Serializing the domain model entity state attributes directly into a structured key-value data dictionary
        JSONObject obj = new JSONObject();
        obj.put("email", payment.getEmail());
        obj.put("cardNumber", payment.getCardNumber());
        obj.put("expiryDate", payment.getExpiryDate());
        obj.put("cardHolder", payment.getCardHolder());

        // Dispatching the structural data stream back onto the asynchronous user dashboard framework
        ServletHelper.json(response, obj.toString());
    }

    /**
     * CREATE / UPDATE Operation: Intercepts HTTP POST requests to save or overwrite user payment profiles.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Authentication Barrier Check: Terminate request processing early if customer state token is missing
        if (!ServletHelper.requireUser(request, response)) return;

        try {
            // Isolating parameter tokens wrapped from incoming form payload submissions
            String email = request.getParameter("email");
            String cardNumber = request.getParameter("cardNumber");
            String expiryDate = request.getParameter("expiryDate");
            String cvv = request.getParameter("cvv");
            String cardHolder = request.getParameter("cardHolder");

            // Defensive Programming: Enforcing data integrity by checking all critical criteria are present
            if (isBlank(email) || isBlank(cardNumber) || isBlank(expiryDate) || isBlank(cvv) || isBlank(cardHolder)) {
                // Return standard HTTP 400 Bad Request error status if any string attribute evaluates blank
                ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, "All card fields are required");
                return;
            }

            // Regular Expression Sanitization: Stripping out all dynamic white space gaps (e.g., "1234 5678" -> "12345678")
            String sanitizedCardNumber = cardNumber.replaceAll("\\s+", "");

            // Packaging fields data models payload and delegating the file writing sync transaction down onto core service layers
            PaymentController.savePayment(new Payment(email, sanitizedCardNumber, expiryDate, cvv, cardHolder));

            // Writing back a standard transaction completion status verification response
            ServletHelper.json(response, new JSONObject().put("message", "Payment method saved successfully").toString());
        } catch (Exception e) {
            // Error trapping: Handling formatting mismatches or parsing layer drop sequences gracefully (HTTP 500)
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * DELETE Operation: Intercepts HTTP DELETE requests to remove a user's card data records.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Authorization Guard Clause Check: Restricting access context profiles exclusively to verified active users
        if (!ServletHelper.requireUser(request, response)) return;

        // Triggering target row structural truncation operations using unique identifying email query strings
        PaymentController.deletePayment(request.getParameter("email"));

        ServletHelper.json(response, new JSONObject().put("message", "Payment method removed successfully").toString());
    }

    /**
     * Null-defensive utility validation checking for blank strings inputs parameters.
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}