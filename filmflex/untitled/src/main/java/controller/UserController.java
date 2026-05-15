package controller;

import service.UserService;
import java.io.IOException;

public class UserController {
    public static boolean deleteUser(String email) throws IOException {
        return new UserService().deleteUser(email);
    }
}
