package eventplanner;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class Registration {

    protected String useremail;
    protected String userpassword;
    protected String usertype;
    static final Logger logger = Logger.getLogger(Registration.class.getName());

    public Registration() {
        useremail = null;
        userpassword = null;
    }

    public void setData(String fname,String secN,String lastN,String useremail, String username,String password,String BD, String type) throws SQLException {
         ConnectDB conn = new ConnectDB();
        Connection conn1 = conn.getConnection();
       
       Statement stConn = conn1.createStatement();
       
       String sqlReg = "insert into "+type+" values(default,"+"'"+fname+"',"+"'"+secN+"',"+"'"+LastN+"',"+"'"+useremail+"','"+username+"','"+password+"','"+BD+"')";
      System.out.printf(""+sqlReg);
      
     int x = stConn.executeUpdate(sqlReg);
        

}
}
