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
@WebServlet("/Add")
public class AddDustbin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddDustbin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		JSONObject jsonresponse=new JSONObject();
		try {
		//String json= request.getParameter("example");
		//	String json=request.getAttribute("example").toString();
			StringBuilder sb=new StringBuilder();
		BufferedReader reader=request.getReader();
		String line;
		while((line=reader.readLine())!=null)
			sb.append(line);
		String json=sb.toString();
		reader.close();
		Gson g=new Gson();
		Dustpojo s=g.fromJson(json, Dustpojo.class);
		Connection con=MyConnection.getConnection();
		PreparedStatement pr=con.prepareStatement("INSERT INTO DUSTBIN VALUES(?,?,?,?,?)");
		pr.setString(1, s.getId());
		pr.setString(2, s.getLabel());
		pr.setString(3, s.getDist());
		pr.setString(4, s.getLat());
		pr.setString(5, s.getLong());
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
