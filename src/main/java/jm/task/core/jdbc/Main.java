package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Иван", "Иванов", (byte) 25);
        userService.saveUser("Макс", "Максов", (byte) 22);
        userService.saveUser("Волк", "Волков", (byte) 20);
        userService.saveUser("Заяц", "Зайцев", (byte) 18);

        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);

        //userService.cleanUsersTable();
        //userService.dropUsersTable();
    }
}
