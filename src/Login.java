
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	Connection conn = null;

	PreparedStatement ps = null; 

	ResultSet rs = null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String URL 	="jdbc:mysql://localhost:3306/voting";

			conn = DriverManager.getConnection(URL,"root","admin");

			ps = conn.prepareStatement("select * from voters_details where voterid=?");
			HttpSession session = request.getSession(true); 


				String voterid = request.getParameter("id"); 

				String pinno = request.getParameter("pin"); 
				ps.setString(1,voterid);
				rs = ps.executeQuery();
				
				while(rs.next())
				{
					if((rs.getInt("voted"))==0)
					{
						if(pinno.equals(rs.getString("pinno")))
						{
							//RequestDispatcher rd = request.getRequestDispatcher("./OTP");
						out.println("<body background='online-voting-system.jpg' style='background-size:100%;'>");
					    out.println("<center> <h2>YOU HAVE LOGED IN SUCCESSFULLY!!</h2></center><br>");
					    out.println("<center><h2>PLEASE CLICK THE BELOW LINK TO PROCEED<h2></center><br>");
					    out.println("<center>"+"<a href='OTP'>"+"GENERATE OTP"+"</a>"+"</center>");
					    out.println("</body>");
						session.setAttribute("id", voterid);
						session.setAttribute("name", rs.getString("name"));
						session.setAttribute("consti", rs.getString("constituency"));
						
						//rd.include(request,response);
					}
					else
					{
						RequestDispatcher rd = request.getRequestDispatcher("./LOGIN.html");
						rd.include(request, response);
						out.println("<center>INVALID ID OR PIN!!</center>");
						//out.println("PLEASE TRY AGAIN!!");
					}
					}
						else {

							RequestDispatcher rd = request.getRequestDispatcher("./LOGIN.html");
							rd.include(request, response);
							out.println("<center>ALREADY CASTED VOTE!! CANNOT LOG U IN AGAIN!</center>");		
						}
				}

				rs.close();
				conn.close();
				ps.close();

					
		}
		catch(Exception e)
		{
			//out.println(e);
		}
	}

}