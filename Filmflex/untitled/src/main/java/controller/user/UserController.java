package controller.user; // Organized under the dedicated user management package container

import service.UserService;
import java.io.IOException;

/**
 * Utility Controller class acting as a processing intermediary for User domain operations.
 * Coordinates user-specific business actions by safely delegating transactional logic
 * down to the core Service Layer execution node.
 * Demonstrates loose coupling, stateless architectural design, and clear Separation of Concerns (SoC).
 */
public class UserController {

    // Dependency Injection: Instantiating a single immutable Service instance to handle core user algorithms
    // Enforcing thread-safe resource utilization rules by applying private static final modification constraints
    private static final UserService userService = new UserService();

    /**
     * DELETE Operation Router: Maps account deletion requests directly onto the underlying persistence services.
     * Acts as an abstraction barrier protecting underlying service operations signatures from outer boundaries exposure.
     * @param email Target identifier string token used to find and drop a user record row
     * @return boolean true if the truncation operation maps and executes successfully inside users.txt
     * @throws IOException Propagates flat-file I/O stream interruptions back to caller servlet handlers for error trapping
     */
    public static boolean deleteUser(String email) throws IOException {

        // Loose Coupling Abstraction Rule: Delegating structural delete tasks down to the Service Layer instance safely
        return userService.deleteUser(email);
    }
}