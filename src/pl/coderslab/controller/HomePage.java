package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.Solution;
import pl.coderslab.dao.SolutionDao;



@WebServlet("/HomePage")
public class HomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		int numberOfSolutions = 0;
		try {
			numberOfSolutions = Integer.parseInt(request.getServletContext().getInitParameter("number-solutions"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("coś poszło nie tak");
		}
		Solution[] solutions = null;
		if (numberOfSolutions > 0) {
			solutions = SolutionDao.loadAll(numberOfSolutions);
		}
		request.setAttribute("solutions", solutions);
		getServletContext().getRequestDispatcher("/views/index.jsp")
        .forward(request, response);
	}

}
