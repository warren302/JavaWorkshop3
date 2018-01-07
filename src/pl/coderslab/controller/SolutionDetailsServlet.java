package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import pl.coderslab.dao.Solution;
import pl.coderslab.dao.SolutionDao;





@WebServlet("/SolutionDetailsServlet")
public class SolutionDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		int sId = 0;
		try {
			sId = Integer.parseInt(request.getParameter("sId"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("coś poszło nie tak");
		}
		Solution solution = SolutionDao.loadById(sId);
		request.setAttribute("solution", solution);
		getServletContext().getRequestDispatcher("/views/solutionDetails.jsp")
        .forward(request, response);

	}

}
