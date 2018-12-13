import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
@WebServlet("/Show")
public class ShowDustbin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowDustbin() {
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
		Connection con=MyConnection.getConnection();
		PreparedStatement pr=con.prepareStatement("SELECT * FROM DUSTBIN");
		ResultSet rs=pr.executeQuery();
		ArrayList<Dustpojo> arr=new ArrayList<Dustpojo>();
		while(rs.next())
		{
			Dustpojo d= new Dustpojo();
			d.setId(rs.getString(1));
			d.setLabel(rs.getString(2));
			d.setDist(rs.getString(3));
			d.setLat(rs.getString(4));
			d.setLong(rs.getString(5));
			arr.add(d);
			
		}
		Gson gson= new Gson();
	    String json= gson.toJson(arr);
		
		//Dustpojo my= gson.fromJson(arr,Dustpojo.class);
		//my.getId();
		//my.getLabel();
		//my.getDist();
		//my.getLat();
		//my.getLong();
		response.getWriter().write(json);
		con.close();
	
		}catch(Exception e) {
			try{jsonresponse.put("exp", e.toString());}catch(Exception e1){}
			response.getWriter().write(jsonresponse.toString());
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
		