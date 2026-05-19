package controller.movie; // Organized under the movie package container structure

import controller.ServletHelper;
import org.json.JSONObject;
import util.FileUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Controller servlet responsible for aggregating administrative revenue and ticket sales analytics.
 * Parses historical dataset strings to calculate overall cumulative totals and dynamic weekly transaction earnings.
 * Demonstrates complex data filtering using Java Date, Calendar computation, and strict input token splitting.
 */
@WebServlet("/get-ticket-sales")
public class TicketSalesServlet extends HttpServlet {

    /**
     * READ Operation: Intercepts HTTP GET requests to return financial metadata summaries for the admin dashboard.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Authorization Barrier Gate: Instantly terminates thread if request session lacks administrative privileges
        if (!ServletHelper.requireAdmin(request, response)) return;

        // Initializing primitive double value accumulators to compute revenue aggregations safely
        double total = 0.0;
        double weekly = 0.0;

        // Time Window Computation: Instantiating Calendar instance to execute dynamic date manipulation algorithms
        Calendar cal = Calendar.getInstance();
        // Moving calendar state exactly 7 days backward into historical timeline relative to active runtime clock
        cal.add(Calendar.DAY_OF_YEAR, -7);
        Date weekStart = cal.getTime(); // Setting the structural boundary threshold date marker

        // Conforming to standard structural time patterns to map raw string rows accurately
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Iterating across raw dataset lines fetched directly from tickets.txt persistence boundaries
        for (String line : FileUtil.readAllTicketLines()) {

            // Delimiter Token Splitting: Passing -1 limit preserves blank trailing strings to prevent structural mapping drops
            String[] p = line.split(",", -1);

            // Ensuring row metadata matches the required validation boundaries criteria density (Index 2 contains Price)
            if (p.length >= 3) {
                // Invoking local null-defensive parsing helper to catch number formatting conversions errors
                double price = parseDouble(p[2]);
                total += price; // Accumulating global cumulative life-time platform revenue

                // Evaluating structural index 3 which encapsulates the purchase timestamp string record
                if (p.length >= 4) {
                    try {
                        // Deserializing raw textual date string row back into a structured Java Date instance framework
                        Date purchased = sdf.parse(p[3]);

                        // Time Window Filtering Strategy: Accumulate price parameter ONLY if transaction falls within the past 7 days
                        if (!purchased.before(weekStart)) {
                            weekly += price;
                        }
                    } catch (Exception ignored) {
                        // Gracefully traps specific date parsing mismatches without halting global calculations loops
                    }
                }
            }
        }

        // Encapsulating computed double totals key-value pairs directly inside a transferrable JSONObject dictionary container
        String jsonResponse = new JSONObject()
                .put("totalSales", total)
                .put("weeklySales", weekly)
                .toString();

        // Dispatching the compiled visualization analytics payload structure back onto the administrative UI grid
        ServletHelper.json(response, jsonResponse);
    }

    /**
     * Null-defensive data converter helper safeguarding application loop states against formatting exceptions.
     * Prevents system crashes if price string attributes happen to hold invalid characters or empty streams.
     */
    private double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0.0; // Return zero default fallback to maintain continuous data aggregation metrics runs
        }
    }
}