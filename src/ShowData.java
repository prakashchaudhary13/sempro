
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
@WebServlet("/ShowD")
public class ShowData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowData() {
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
			StringBuilder sb=new StringBuilder();
			BufferedReader reader=request.getReader();
			String line;
			while((line=reader.readLine())!=null)
				sb.append(line);
			String json=sb.toString();
			reader.close();
			Gson g=new Gson();
		Connection con=MyConnection.getConnection();
		Dustpojo s=g.fromJson(json, Dustpojo.class);
		
		//Connection con=MyConnection.getConnection();
		PreparedStatement pr11=con.prepareStatement("SELECT * FROM DUSTBIN WHERE id=?");
		pr11.setString(1, s.getId());
		//pr11.executeUpdate();
		//ArrayList<Dustpojo> arr=new ArrayList<Dustpojo>();
		Dustpojo d= new Dustpojo();
		ResultSet rs=pr11.executeQuery();
		
		while(rs.next())
		{
			
			d.setId(rs.getString(1));
			d.setLabel(rs.getString(2));
			d.setDist(rs.getString(3));
			d.setLat(rs.getString(4));
			d.setLong(rs.getString(5));
			//arr.add(d);
			
		}
		String abc =d.getDist();
		//jsonresponse.put(abc);
		Gson gson= new Gson();
	    json= gson.toJson(d);
	   // jsonresponse.put(abc);
		
		//Dustpojo my= gson.fromJson(arr,Dustpojo.class);
		//my.getId();
		//my.getLabel();
		//my.getDist();
		//my.getLat();
		//my.getLong();
		response.getWriter().write(abc);
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
		