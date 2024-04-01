import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginPage {
    private boolean adminLoggedIn;
    private boolean userLoggedIn;
    private boolean supervisorLoggedIn;
    private boolean loginFlag;
    private String lastLoginType;
    private String userEmail;
    private int userIdFromDB;

    public UserLoginPage() {
        adminLoggedIn = false;
        userLoggedIn = false;
        supervisorLoggedIn = false;
        loginFlag = false;
        userEmail = null;
    }

    public boolean isValidCredentials(String userEmail, String userPassword, String userType) {
        try (Connection connection = connectDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + userType + " WHERE email = ? AND password = ?")) {

            statement.setString(1, userEmail);
            statement.setString(2, userPassword);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userIdFromDB = resultSet.getInt(1);
                lastLoginType = userType;
                loginFlag = true;

                if (userType.equals("admin")) {
                    adminLoggedIn = true;
                } else if (userType.equals("users")) {
                    userLoggedIn = true;
                } else {
                    supervisorLoggedIn = true;
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUserLoggedIn() {
        return userLoggedIn;
    }

    public boolean isAdminLoggedIn() {
        return adminLoggedIn;
    }

    public boolean isSupervisorLoggedIn() {
        return supervisorLoggedIn;
    }

    public boolean isAnyUserLoggedIn() {
        return loginFlag;
    }

    public boolean logout() {
        adminLoggedIn = false;
        userLoggedIn = false;
        supervisorLoggedIn = false;
        loginFlag = false;
        userEmail = null;
        return true;
    }

    public String getLastLoginType() {
        return lastLoginType;
    }

    public int getUserIdFromDB() {
        return userIdFromDB;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
