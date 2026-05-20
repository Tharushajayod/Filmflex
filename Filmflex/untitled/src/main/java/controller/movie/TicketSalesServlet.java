package controller.movie;

import org.json.JSONObject;
import controller.ServletHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@WebServlet("/get-ticket-sales")
public class TicketSalesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Enforcing that only signed admins can touch the dashboard metrics analytics pipeline
        if (!ServletHelper.requireAdmin(request, response)) return;

        double totalSales = 0.0;
        double weeklySales = 0.0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();

            // Dynamic boundary timeline tracking setups for weekly filtering operations
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.DAY_OF_MONTH, -7);
            Date sevenDaysAgo = cal.getTime();

            // Fetching active raw tracking lines directly via storage utilities structures layers
            java.util.List<String> lines = util.FileUtil.readAllTicketLines();

            for (String line : lines) {
                if (line == null || line.trim().isEmpty()) continue;

                String[] p = line.split(",", -1);

                // Expected dynamic elements allocation format layout mapping fields:
                // p[0]=email, p[1]=ticketId, p[2]=plan, p[3]=price, p[4]=date
                if (p.length >= 4) {
                    try {
                        // Extracting price string data cleanly from index array node 3
                        String priceStr = p[3].trim().replaceAll("[^\\d.]", "");
                        double priceVal = Double.parseDouble(priceStr);

                        totalSales += priceVal;

                        // Weekly aggregation mapping checks layer logic inside dates boundaries
                        if (p.length >= 5) {
                            try {
                                Date purchaseDate = sdf.parse(p[4].trim());
                                if (purchaseDate.after(sevenDaysAgo)) {
                                    weeklySales += priceVal;
                                }
                            } catch (Exception dateEx) {
                                // Fallback: If timestamp is unparseable, increment weekly metrics to retain data flow integrity
                                weeklySales += priceVal;
                            }
                        }
                    } catch (NumberFormatException nfe) {
                        // Suppressing unparseable row structures parameters safely to prevent server breakdowns
                        System.err.println("Skipping malformed transaction price metrics row row node: " + nfe.getMessage());
                    }
                }
            }

            // Preparing clean key mappings serialization targets matching JS expectation configurations
            JSONObject responseJson = new JSONObject();
            responseJson.put("totalSales", totalSales);
            responseJson.put("weeklySales", weeklySales);

            ServletHelper.json(response, responseJson.toString());

        } catch (Exception e) {
            e.printStackTrace();
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error computing metrics data matrix tracking elements");
        }
    }
}