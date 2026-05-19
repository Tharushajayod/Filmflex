package filter; // Organized under the dedicated infrastructure filter package layer

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Security Interceptor Filter enforcing Role-Based Access Control (RBAC) for administrative resources.
 * Intercepts incoming target resource streams, evaluates session credential authorization scopes,
 * and handles dynamic traffic redirection or early request termination layout rules.
 */
@WebFilter(urlPatterns = {"/adminDashboard.html", "/movieManagement.html", "/api/movies/*", "/api/movies"})
public class AdminAuthFilter implements Filter {

    /**
     * Intercepts, inspects, and filters incoming traffic requests before they hit matching target servlets or HTML files.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Polymorphic Downcasting: Converting generic protocol-independent Servlet tokens
        // directly into explicit HTTP-specific structures to safely extract web routing parameters fields
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Extracting the active HTTP request execution verb parameters layer (e.g., GET, POST, PUT, DELETE)
        String method = req.getMethod();

        // ── STEP 1: PUBLIC READ-ONLY ACCESS WORKFLOW HOOK ──
        // Optimization Rule: Creating an explicit bypass condition allowing guest traffic to retrieve movie data
        boolean movieReadOnly = req.getServletPath().startsWith("/api/movies") && "GET".equalsIgnoreCase(method);

        if (movieReadOnly) {
            // Decoupling Interception: Immediately passing control down to the next node inside the structural server pipeline
            chain.doFilter(request, response);
            return; // Terminate execution wrapper loop early to exit the filter boundary layer securely
        }

        // Looking up active tracking cookies sessions blocks passing false to avoid instantiating blank memory zones
        HttpSession session = req.getSession(false);

        // Scope Verification: Confirming if the active memory container profile contains a non-null Admin model reference
        boolean isAdmin = session != null && session.getAttribute("admin") != null;

        // ── STEP 2: AUTHORIZATION CONDITION EVALUATION MATRIX ──
        if (isAdmin) {
            // Identity Authenticated: Forwarding the execution loop onto the targeted secure controller servlet or file resource
            chain.doFilter(request, response);
        } else if (req.getServletPath().endsWith(".html")) {
            // UI Security Boundary Protection: If an unauthenticated web guest attempts to access admin web layout interfaces,
            // issue a client-side HTTP 302 Redirection forcing the browser back onto the administrative portal login grid
            res.sendRedirect("adminLogin.html");
        } else {
            // API Security Boundary Protection: If access attempt targets structured backend RESTful operations parameters,
            // drop the connection thread by transmitting an explicit HTTP 401 Unauthorized status error payload parameter
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Admin login required");
        }
    }

    /**
     * Lifecycle initialization routine executed once by the server engine container upon deployment instantiation.
     */
    @Override
    public void init(FilterConfig filterConfig) { }

    /**
     * Lifecycle destruction cleanup routine triggered prior to application server container shutdown states.
     */
    @Override
    public void destroy() { }
}