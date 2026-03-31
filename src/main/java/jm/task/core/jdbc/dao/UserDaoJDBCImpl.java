package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    //создание таблицы пользователя
    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(45) NOT NULL,
                    lastName VARCHAR(45) NOT NULL,
                    age TINYINT UNSIGNED
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
                """;
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {

            statement.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    //удаление пользователя из таблицы
    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    //сохранение пользователя
    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO users (`name`, `lastName`, `age`) VALUES (?,?,?)";

        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    //удаление пользователя по id
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    //получить доступ ко всем пользователям
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return userList;
    }

    @Override
    //очистить таблицу пользователей
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
