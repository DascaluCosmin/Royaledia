package socialnetwork.repository.database;

import socialnetwork.domain.Entity;
import socialnetwork.domain.User;
import socialnetwork.domain.UserCredentials;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserCredentialsDBRepository implements Repository<Long, UserCredentials> {
    private String url;
    private String username;
    private String password;
    private Validator<UserCredentials> validator;

    public UserCredentialsDBRepository(String url, String username, String password, Validator<UserCredentials> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    public UserCredentials findOne(String username) {
        try (Connection connection = DriverManager.getConnection(url, this.username, password)) {
            String command = "SELECT * FROM \"userCredentials\" WHERE \"username\" = '" + username + "';";
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long idUser = resultSet.getLong("idUser");
                String password = resultSet.getString("password");
                UserCredentials userCredentials = new UserCredentials(username, password);
                userCredentials.setId(idUser);
                return userCredentials;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public UserCredentials findOne(Long aLong) {
        try (Connection connection = DriverManager.getConnection(url, this.username, password)) {
            String command = "SELECT * FROM \"userCredentials\" WHERE \"idUser\" = " + aLong;
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                UserCredentials userCredentials = new UserCredentials(username, password);
                userCredentials.setId(aLong);
                return userCredentials;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<UserCredentials> findAll() {
        List<UserCredentials> userCredentialsList = new ArrayList();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"userCredentials\"");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long idUser = resultSet.getLong("idUser");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                UserCredentials userCredentials = new UserCredentials(username, password);
                userCredentials.setId(idUser);
                userCredentialsList.add(userCredentials);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userCredentialsList;
    }

    @Override
    public UserCredentials save(UserCredentials entity) {
        return null;
    }

    @Override
    public UserCredentials delete(Long aLong) {
        return null;
    }

    @Override
    public UserCredentials update(UserCredentials entity) {
        return null;
    }
}
