package eventplanner;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Registration {

    protected String user_email;
    protected String user_password;
    protected String user_type;
    static final Logger logger = Logger.getLogger(Registration.class.getName());

    public Registration() {
        user_email = null;
        user_password = null;
    }

    public void setData(String user_email, String user_password, String type) {
        connectDB conn = new connectDB();
        conn.testConn();

        // Check if type is null; set it to a default value if needed
        if (type == null) {
            type = "default_type"; // Replace "default_type" with an appropriate default value
        }
        String sql = "INSERT INTO systemusers (user_email, user_password, user_type) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.getConnection().prepareStatement(sql)) {
            stmt.setString(1, user_email);
            stmt.setString(2, user_password);
            stmt.setString(3, type);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.log(Level.INFO, "Congratulations! Your account has been successfully created.");
            } else {
                logger.log(Level.WARNING, "Account creation failed. No rows were affected.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occurred while setting user data.", e);
        }
    }
}
