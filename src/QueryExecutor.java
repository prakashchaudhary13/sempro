import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/QueryExecutor")
public class QueryExecutor extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public QueryExecutor()
	{
	}
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String sql=request.getParameter("query");
		try{
		Connection conn=null;
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		StringBuffer msg=new StringBuffer("");
		if(sql!=null)
		{
			 String dbName = System.getProperty("RDS_DB_NAME");
			String userName = System.getProperty("RDS_USERNAME");
			String password = System.getProperty("RDS_PASSWORD");
			String hostname = System.getProperty("RDS_HOSTNAME");
			String port = System.getProperty("RDS_PORT");
			String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
			port + "/" + dbName + "?user=" + userName + "&password=" + password;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl);
			if(sql.startsWith("SELECT")||sql.startsWith("SHOW"))
			{
					PreparedStatement pt=conn.prepareStatement(sql);
					ResultSet rs=pt.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnsNumber = rsmd.getColumnCount();
					out.println("<html><head></head><body>");
					out.println("<table style=\"width:100%\"><tr>");
					for(int i=1;i<=columnsNumber;i++)
					out.println("<td><b>"+rsmd.getColumnName(i)+"</b></td>");
					out.println("</tr>");
					while(rs.next())
					{
						out.println("<tr>");
						for(int i=1;i<=columnsNumber;i++){
							out.println("<td>");
							 int type = rsmd.getColumnType(i);
							if (type == Types.VARCHAR || type == Types.CHAR)
								out.println(rs.getString(i));
							else if(type==Types.INTEGER)
								out.println(rs.getLong(i));
							out.println("</td>");
						}
						out.println("</tr>");
					}
					out.println("</table>");
					out.println("<a href=\"dataopr.jsp\">Execute More</a>");
					out.println("</body></html>");
			}
			
			else{
				PreparedStatement pt=conn.prepareStatement(sql);
				pt.executeUpdate();
				conn.close();
				response.sendRedirect("dataopr.jsp?msg=Query Success&sql="+sql);
			}
		}
	}catch(Exception e)
	{
		response.sendRedirect("dataopr.jsp?msg="+e.getMessage()+"&sql="+sql);
	}
}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		doGet(request,response);
	}
}