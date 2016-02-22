

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
 * Servlet implementation class Result
 */
@WebServlet("/Result")
public class Result extends HttpServlet {
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
			int r=0;
			conn = DriverManager.getConnection(URL,"root","admin");

			ps = conn.prepareStatement("update voters_details set voted=1 where voterid=?");
			HttpSession session = request.getSession(true); 


				String voterid = (String)session.getAttribute("id"); 

				
				ps.setString(1, voterid);
				r = ps.executeUpdate();
				if(r==0)
					out.println("UPDATION FAILED!!");
				else
				{
					RequestDispatcher rd = request.getRequestDispatcher("./Result.html");
					rd.include(request, response);
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
