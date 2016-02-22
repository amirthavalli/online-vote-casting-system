

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
 * Servlet implementation class Constituency
 */
@WebServlet("/Constituency")
public class Constituency extends HttpServlet {
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

			ps = conn.prepareStatement("select * from candidate_details where constituency=?");
			HttpSession session = request.getSession(true); 


				String voterid = (String)session.getAttribute("id"); 

				String name = (String)session.getAttribute("name"); 
				String consti = (String)session.getAttribute("consti");
				ps.setString(1, consti);
				rs = ps.executeQuery();
				out.println("<body background='online-voting-system.jpg' style='background-size:100%;'>");
				out.println("<center><h1>" + "WELCOME " + name + " YOUR VOTER ID IS " + voterid +"</h1></center>");
				out.println("<form method='post' action='result.html'>");
				out.println("<center><table>");
				out.println("<tr>");
				out.println("<th>" + "Candidate ID" + "</th>");
				out.println("<th>" + "Candidate name" + "</th>");
				out.println("<th>" + "Political party" + "</th>");
				out.println("</tr>");
				
				while(rs.next())
				{
					out.println("<tr>");
					out.println("<td>" + rs.getString("candidate_id") + "</td>");
					out.println("<td>" + rs.getString("candi_name") + "</td>");
					out.println("<td>" + rs.getString("political_party") + "</td>");
					out.println("<td>" + "<input type='radio' name='constituency'>" + "</td>");
					out.println("</tr>");
				}
				out.println("<tr> <td>"+"<input type='submit' value='CAST VOTE'>"+" </td> </tr>");
				out.println("</table></center>");
				out.println("</form>");
				out.println("</body>");
				  RequestDispatcher rd = request.getRequestDispatcher("./Result");
					rd.include(request, response);
				

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

