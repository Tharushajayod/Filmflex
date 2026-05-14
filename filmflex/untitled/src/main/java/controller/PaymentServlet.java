package controller;

import model.Payment;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/payment-methods")
public class PaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireUser(request, response)) return;
        String email = request.getParameter("email");
        Payment payment = PaymentController.getPaymentByEmail(email);
        if (payment == null) {
            ServletHelper.json(response, new JSONObject().toString());
            return;
        }
        JSONObject obj = new JSONObject();
        obj.put("email", payment.getEmail());
        obj.put("cardNumber", payment.getCardNumber());
        obj.put("expiryDate", payment.getExpiryDate());
        obj.put("cardHolder", payment.getCardHolder());
        ServletHelper.json(response, obj.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireUser(request, response)) return;
        try {
            String email = request.getParameter("email");
            String cardNumber = request.getParameter("cardNumber");
            String expiryDate = request.getParameter("expiryDate");
            String cvv = request.getParameter("cvv");
            String cardHolder = request.getParameter("cardHolder");
            if (isBlank(email) || isBlank(cardNumber) || isBlank(expiryDate) || isBlank(cvv) || isBlank(cardHolder)) {
                ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, "All card fields are required");
                return;
            }
            PaymentController.savePayment(new Payment(email, cardNumber.replaceAll("\\s+", ""), expiryDate, cvv, cardHolder));
            ServletHelper.json(response, new JSONObject().put("message", "Payment method saved successfully").toString());
        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireUser(request, response)) return;
        PaymentController.deletePayment(request.getParameter("email"));
        ServletHelper.json(response, new JSONObject().put("message", "Payment method removed successfully").toString());
    }

    private boolean isBlank(String value) { return value == null || value.trim().isEmpty(); }
}
