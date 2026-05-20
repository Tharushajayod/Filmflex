package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/adminDashboard.html", "/movieManagement.html", "/api/movies/*", "/api/movies"})
public class AdminAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String method = req.getMethod();

        // ── STEP 1: PUBLIC READ-ONLY ACCESS WORKFLOW HOOK ──
        boolean movieReadOnly = req.getServletPath().startsWith("/api/movies") && "GET".equalsIgnoreCase(method);

        if (movieReadOnly) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        boolean isAdmin = session != null && session.getAttribute("admin") != null;

        // ── STEP 2: AUTHORIZATION CONDITION EVALUATION MATRIX ──
        if (isAdmin) {
            chain.doFilter(request, response);
        } else if (req.getServletPath().endsWith(".html")) {
            res.sendRedirect("adminLogin.html");
        } else {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Admin login required");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void destroy() { }
}