package controller;

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

@WebServlet("/get-ticket-sales")
public class TicketSalesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireAdmin(request, response)) return;
        double total = 0.0;
        double weekly = 0.0;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        Date weekStart = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String line : FileUtil.readAllTicketLines()) {
            String[] p = line.split(",", -1);
            if (p.length >= 3) {
                double price = parseDouble(p[2]);
                total += price;
                if (p.length >= 4) {
                    try {
                        Date purchased = sdf.parse(p[3]);
                        if (!purchased.before(weekStart)) weekly += price;
                    } catch (Exception ignored) { }
                }
            }
        }
        ServletHelper.json(response, new JSONObject().put("totalSales", total).put("weeklySales", weekly).toString());
    }

    private double parseDouble(String s) { try { return Double.parseDouble(s); } catch (Exception e) { return 0.0; } }
}
