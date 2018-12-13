import java.sql.*;
public class MyConnection {

	public static Connection getConnection()throws Exception {
		 String dbName = System.getProperty("RDS_DB_NAME");
			String userName = System.getProperty("RDS_USERNAME");
			String password = System.getProperty("RDS_PASSWORD");
			String hostname = System.getProperty("RDS_HOSTNAME");
			String port = System.getProperty("RDS_PORT");
			if(userName!=null||password!=null)
			{
				String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
						port + "/" + dbName + "?user=" + userName + "&password=" + password;
						Class.forName("com.mysql.jdbc.Driver");
						Connection conn = DriverManager.getConnection(jdbcUrl);
						return conn;
			}
			else
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/socialWeb","root","root");
					return con;
			}
		
	}
	
}
