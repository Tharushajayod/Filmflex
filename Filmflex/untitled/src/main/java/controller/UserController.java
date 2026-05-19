package controller;

import service.UserService;
import java.io.IOException;

public class UserController {

    // Initializing the active business logic layer cleanly
    private static final UserService userService = new UserService();

    public static boolean deleteUser(String email) throws IOException {
        // Intercepts and maps to the correct instance inside your UserService.java
        return userService.deleteUser(email);
    }
}