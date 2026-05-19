package model;

public class Ticket {
    private String email;
    private String plan;
    private double price;
    private String timestamp;

    public Ticket(String email, String plan, double price, String timestamp) {
        this.email = email;
        this.plan = plan;
        this.price = price;
        this.timestamp = timestamp;
    }

    public static Ticket fromFileString(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(","); // මෙතන | වෙනුවට , දාන්න
        if (parts.length < 4) return null;
        try {
            // parts[0]=email, parts[1]=plan/id, parts[2]=amount, parts[3]=date
            return new Ticket(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3]);
        } catch (Exception e) {
            return null;
        }
    }

    public String getEmail() { return email; }
    public String getPlan() { return plan; }
    public double getPrice() { return price; }
    public String getTimestamp() { return timestamp; }
}