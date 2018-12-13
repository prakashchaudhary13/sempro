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
import java.net.URL;
import java.util.HashMap;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
 
import javax.net.ssl.HttpsURLConnection;
 
//import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
//import org.omg.CORBA.portable.OutputStream;
import org.json.JSONObject;

import com.google.gson.Gson;
/**
 * Servlet implementation class AddScreen
 */
@WebServlet("/AddDataM")
public class AddDataM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddDataM() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("appication/json");
		String id,label,dist,lat,lon;
		id=request.getParameter("id");
		label=request.getParameter("label");
		dist=request.getParameter("dist");
		lat=request.getParameter("lat");
		lon=request.getParameter("lon");
		Connection con;
		
		JSONObject jsonresponse=new JSONObject();
	    final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
	    final String FCM_SERVER_API_KEY    = "AAAAY7V7HZs:APA91bENdFm578tDwrWi66lJ8VeFzhvltgi6zclcsXNj71lyIh8Qk3unvKJosB-9XlUCWcnbmR1-6xcJjbYE9SRxoMxBEiRZvESe4z79_lO-POJ2Tmiv9mcbzWHHeDioUy9_xlwIfbi1";
	    final ArrayList<String> deviceRegistrationId;
		
		try {
	
			int dist1=Integer.parseInt(dist);
			ArrayList<String> arr=new ArrayList<String>();
			int responseCode = -1;
	        String responseBody = null;
			
			con=MyConnection.getConnection();
			if(dist1<7)
			{
				PreparedStatement pr=con.prepareStatement("SELECT DISTINCT(TOKEN) FROM tokenmaster");
				ResultSet rs=pr.executeQuery();
				while(rs.next())
				{
					
				arr.add(rs.getString(1));
				}
				try
				{
				deviceRegistrationId=arr;
				System.out.println("prakash"+deviceRegistrationId);
				System.out.println("Sending FCM request");
	            byte[] postData = getPostData(deviceRegistrationId);
	            
	            URL url = new URL(FCM_URL);
	            HttpsURLConnection httpURLConnection = (HttpsURLConnection)url.openConnection();
	 
	            //set timeputs to 10 seconds
	            httpURLConnection.setConnectTimeout(10000);
	            httpURLConnection.setReadTimeout(10000);
	 
	            httpURLConnection.setDoOutput(true);
	            httpURLConnection.setUseCaches(false);
	            httpURLConnection.setRequestMethod("POST");
	            httpURLConnection.setRequestProperty("Content-Type", "application/json");
	            httpURLConnection.setRequestProperty("Content-Length", Integer.toString(postData.length));
	            httpURLConnection.setRequestProperty("Authorization", "key="+FCM_SERVER_API_KEY);
	 
	             
	 
	            OutputStream out = httpURLConnection.getOutputStream();
	            out.write(postData);
	            out.close();
	            responseCode = httpURLConnection.getResponseCode();
	            //success
	            if (responseCode == 200)
	            {
	                responseBody = convertStreamToString(httpURLConnection.getInputStream());
	                System.out.println("FCM message sent : " + responseBody);
	            }
	            //failure
	            else
	            {
	                responseBody = convertStreamToString(httpURLConnection.getErrorStream());
	                System.out.println("Sending FCM request failed for regId: " + deviceRegistrationId + " response: " + responseBody);
	            }
			
		}
	        catch (IOException ioe)
	        {
	            System.out.println("IO Exception in sending FCM request. regId: ");
	            ioe.printStackTrace();
	        }
	        catch (Exception e)
	        {
	            System.out.println("Unknown exception in sending FCM request. regId: ");
	            e.printStackTrace();
	        }
	    }
	     
	
	    
	     
	    
			
		/*
		PreparedStatement pr11=con.prepareStatement("DELETE FROM DUSTBIN WHERE id=?");
		pr11.setString(1, id);
		pr11.executeUpdate();

		*/
			PreparedStatement pr=con.prepareStatement("UPDATE DUSTBIN SET dist=? WHERE ID=?");
		pr.setString(1, dist);
		pr.setString(2, id);
		//pr.setString(3, dist);
		//pr.setString(4, lat);
		//pr.setString(5, lon);
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
		
	
	public static byte[] getPostData(ArrayList<String> registrationIds) throws JSONException {
        HashMap<String, String> dataMap = new HashMap<>();
        JSONObject payloadObject = new JSONObject();
 
        dataMap.put("name", "SmartBins");
        dataMap.put("country", "Empty Your Dustbin");
         
        JSONObject data = new JSONObject(dataMap);;
        payloadObject.put("data", data);
        payloadObject.put("registration_ids", registrationIds);
 
        return payloadObject.toString().getBytes();
    }
	public static String convertStreamToString (InputStream inStream) throws Exception
    {
        InputStreamReader inputStream = new InputStreamReader(inStream);
        BufferedReader bReader = new BufferedReader(inputStream);
 
        StringBuilder sb = new StringBuilder();
        String line = null;
        while((line = bReader.readLine()) != null)
        {
            sb.append(line);
        }
 
        return sb.toString();
    }
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
