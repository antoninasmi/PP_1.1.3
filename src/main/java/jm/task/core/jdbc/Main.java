package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Tony", "Gemar", (byte) 22);
        userService.saveUser("Adam", "Gemar", (byte) 32);
        userService.saveUser("Kate", "Roberts", (byte) 18);
        userService.saveUser("Jane", "Brown", (byte) 56);

        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
