package model;

public class Payment {
    private String email;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardHolder;

    public Payment(String email, String cardNumber, String expiryDate, String cvv, String cardHolder) {
        this.email = email;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
    }

    public String getEmail() { return email; }
    public String getCardNumber() { return cardNumber; }
    public String getExpiryDate() { return expiryDate; }
    public String getCvv() { return cvv; }
    public String getCardHolder() { return cardHolder; }

    public String toFileString() {
        return String.join(",", safe(email), safe(cardNumber), safe(expiryDate), safe(cvv), safe(cardHolder));
    }

    public static Payment fromFileString(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(",", -1);
        if (parts.length < 5) return null;
        return new Payment(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim());
    }

    private static String safe(String value) {
        return value == null ? "" : value.replace(",", " ").trim();
    }
}
