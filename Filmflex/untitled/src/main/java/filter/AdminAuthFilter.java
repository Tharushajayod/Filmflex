package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {
        "/adminDashboard.html",
        "/movieManagement.html",
        "/adminManagement.html",
        "/api/movies/*",
        "/api/movies",
        "/api/admin/*"
})
public class AdminAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String method = req.getMethod();
        String path = req.getServletPath();

        // ── STEP 1: PUBLIC READ-ONLY ACCESS WORKFLOW HOOK ──
        boolean movieReadOnly = path.startsWith("/api/movies") && "GET".equalsIgnoreCase(method);

        if (movieReadOnly) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        model.Admin adminObj = (session != null) ? (model.Admin) session.getAttribute("admin") : null;
        boolean isAdmin = adminObj != null;

        // ── STEP 2: MULTI-LEVEL ROLE-BASED ACCESS CONTROL (RBAC) MATRIX ──
        if (isAdmin) {

            boolean targetingAdminManagement = path.contains("adminManagement") || path.contains("admin-management") || path.startsWith("/api/admin");

            if (targetingAdminManagement) {
                if (!"superadmin".equalsIgnoreCase(adminObj.getRole())) {
                    if (path.endsWith(".html")) {
                        res.sendRedirect("adminDashboard.html");
                    } else {
                        res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: Requires Super Admin privileges");
                    }
                    return;
                }
            }

            chain.doFilter(request, response);

        } else if (path.endsWith(".html")) {
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