package controller;

import model.Payment;
import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentController {
    public static void savePayment(Payment payment) throws IOException {
        if (payment == null || isBlank(payment.getEmail())) throw new IllegalArgumentException("Email is required");
        List<String> lines = FileUtil.readAllPaymentLines();
        List<String> updated = new ArrayList<>();
        boolean replaced = false;
        for (String line : lines) {
            Payment existing = Payment.fromFileString(line);
            if (existing != null && existing.getEmail().equalsIgnoreCase(payment.getEmail())) {
                updated.add(payment.toFileString());
                replaced = true;
            } else if (existing != null) {
                updated.add(existing.toFileString());
            }
        }
        if (!replaced) updated.add(payment.toFileString());
        FileUtil.writeAllPaymentLines(updated);
    }

    public static Payment getPaymentByEmail(String email) throws IOException {
        if (isBlank(email)) return null;
        for (String line : FileUtil.readAllPaymentLines()) {
            Payment payment = Payment.fromFileString(line);
            if (payment != null && payment.getEmail().equalsIgnoreCase(email.trim())) return payment;
        }
        return null;
    }

    public static void deletePayment(String email) throws IOException {
        if (isBlank(email)) return;
        List<String> updated = new ArrayList<>();
        for (String line : FileUtil.readAllPaymentLines()) {
            Payment payment = Payment.fromFileString(line);
            if (payment != null && !payment.getEmail().equalsIgnoreCase(email.trim())) updated.add(payment.toFileString());
        }
        FileUtil.writeAllPaymentLines(updated);
    }

    private static boolean isBlank(String value) { return value == null || value.trim().isEmpty(); }
}
