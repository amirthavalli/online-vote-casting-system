import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

//import java.io.BufferedReader;

import java.io.IOException;

//import java.io.InputStreamReader;

import java.io.PrintWriter;

//import java.net.HttpURLConnection;

import java.net.URL;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
/**
 * Servlet implementation class OTP
 */
@WebServlet("/OTP")
public class OTP extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	Connection conn = null; 

	PreparedStatement ps = null; 

	ResultSet rs = null;
	int genvote(int a)
	{
		int otp=a;
		otp = (otp*otp) +4;
		return otp;
	}
		
		
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");  
         PrintWriter out = response.getWriter();
         int otp;

		 try 

		{ 

		Class.forName("com.mysql.jdbc.Driver");
		String URL ="jdbc:mysql://localhost:3306/voting";
		conn = 	DriverManager.getConnection(URL, "root", "admin");
		ps = conn.prepareStatement("select * from voters_details where voterid=?"); 
		HttpSession session = request.getSession(true); 
		String voterid= (String)session.getAttribute("id");
		
		ps.setString(1,voterid); 
		
		rs = ps.executeQuery();
		
		if(rs.next()) 
		{ 
			//out.println("HI");
			String phno = rs.getString("phno");
			String last2 = phno.substring(8);
			int last22 = Integer.parseInt(last2);
			String	user = new String("USER");

			 String pass =	new String("password");

			 String name =	new String("admin");

			 String to =	"91"+rs.getString("phno");

				//out.println("value of to " + to); 
			 otp = genvote(last22);
			 session.setAttribute("otp", otp);
				        
	        URL url = new 	URL("http://bulksms.mysmsmantra.com:8080/WebSMS/SMSAPI.jsp?" + 

				"username=" + user + 

				"&password=" + pass + 

				"&sendername=" + name +

				"&mobileno=" + to + 

				"&message=" + "Your OTP for casting your vote is: " + otp); 
	        
	        out.println("<a href='"+url+"'>"+"SEND OTP"+"</a><br/> <br/>");
				 
	       RequestDispatcher rd = request.getRequestDispatcher("./OTP.html");
			rd.include(request, response);
		}
				
		 }
		 catch(Exception e)
		 {
			// out.println(e);
			 
		 }
	}

}

