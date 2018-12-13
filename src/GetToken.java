
import java.io.BufferedReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
/**
 * Servlet implementation class AddScreen
 */
@WebServlet("/GetToken")
public class GetToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetToken() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String token1;
		token1=request.getParameter("token");
		JSONObject jsonresponse=new JSONObject();
		try {
		
		Connection con=MyConnection.getConnection();
	
		PreparedStatement pr=con.prepareStatement("INSERT INTO tokenmaster VALUES(?)");
		pr.setString(1, token1);
		
	
		int m=pr.executeUpdate();
		if(m>0)
			jsonresponse.put("status", 1);
		else
			jsonresponse.put("status", 0);
		response.getWriter().write(jsonresponse.toString());
		con.close();
		}catch(Exception e) {
			try{jsonresponse.put("exp", e.toString());}catch(Exception e1){}
			response.getWriter().write(jsonresponse.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
