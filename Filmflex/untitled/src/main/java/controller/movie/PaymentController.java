package controller.movie; // Organized under the movie package container structure

import model.Payment;
import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility Controller class responsible for handling business logic transactions for Payments.
 * Manages atomic read, write, update, and delete mutations directly over payment flat files.
 * Employs clean static processing design patterns without requiring object state initialization.
 */
public class PaymentController {

    /**
     * CREATE / UPDATE Operation: Saves a payment record into the tracking persistence store (payments.txt).
     * If the email identifier already exists, it updates the record; otherwise, it appends a new row.
     * Demonstrates strict data validation and row-replacement handling patterns.
     */
    public static void savePayment(Payment payment) throws IOException {
        // Defensive Input Validation Rule: Guarding against null data objects or empty email constraints
        if (payment == null || isBlank(payment.getEmail())) {
            throw new IllegalArgumentException("Email is required"); // Explicit runtime error propagation block
        }

        // Reading current text data matrices layers directly from the shared file-handling utility node
        List<String> lines = FileUtil.readAllPaymentLines();
        List<String> updated = new ArrayList<>();
        boolean replaced = false;

        // Data Sync Overwrites Loop: Iterating through rows to find matching identity constraints
        for (String line : lines) {
            Payment existing = Payment.fromFileString(line); // Deserializing raw string row into structured entity instance

            if (existing != null && existing.getEmail().equalsIgnoreCase(payment.getEmail())) {
                // Key identifier matched: Intercepting old record layout and replacing it with the newly updated state serialization
                updated.add(payment.toFileString());
                replaced = true;
            } else if (existing != null) {
                // Retaining non-matching data rows untouched inside the operational collection arrays cache
                updated.add(existing.toFileString());
            }
        }

        // If no matching transaction identity record wrapper was found, append the new entry as a fresh dataset entry row
        if (!replaced) {
            updated.add(payment.toFileString());
        }

        // Persisting the finalized structural data back down into the local hard drive storage matrix block
        FileUtil.writeAllPaymentLines(updated);
    }

    /**
     * READ Operation: Searches for a specific payment entity transaction using a unique email address token.
     * @return Payment object model mapping or null if target row index does not overlap flat file records boundaries
     */
    public static Payment getPaymentByEmail(String email) throws IOException {
        // Safe Guard Clause checking for null or malicious space-only identifier parameters drops
        if (isBlank(email)) return null;

        // Iterating across raw dataset data lines fetched from the text persistence boundaries
        for (String line : FileUtil.readAllPaymentLines()) {
            Payment payment = Payment.fromFileString(line);

            // Checking identity strings ignoring character case sensitivity structures parameters matching rules
            if (payment != null && payment.getEmail().equalsIgnoreCase(email.trim())) {
                return payment; // Object context mapping successfully extracted
            }
        }
        return null; // Return null default if identity string is absent from data rows index maps
    }

    /**
     * DELETE Operation: Removes a unique payment dataset transaction mapping row using email reference markers.
     */
    public static void deletePayment(String email) throws IOException {
        // Guard clause checking to avoid redundant loop runs if specified input keys are blank
        if (isBlank(email)) return;

        List<String> updated = new ArrayList<>();

        // Truncation Processing Logic: Filtering out the matching identifier row record layout segment
        for (String line : FileUtil.readAllPaymentLines()) {
            Payment payment = Payment.fromFileString(line);

            // Retain row metadata inside memory collection arrays ONLY if it does NOT match the delete identity key token
            if (payment != null && !payment.getEmail().equalsIgnoreCase(email.trim())) {
                updated.add(payment.toFileString());
            }
        }

        // Committing the newly pruned record matrices directly onto permanent file storage systems layers
        FileUtil.writeAllPaymentLines(updated);
    }

    /**
     * Null-defensive helper validation block analyzing input parameter density states.
     */
    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}