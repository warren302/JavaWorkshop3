package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.Exercise;
import pl.coderslab.dao.ExerciseDao;



@WebServlet("/ExerciseEdit")
public class ExerciseEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int eId = 0;
		try {
			eId = Integer.parseInt(request.getParameter("eId"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("cos poszlo nie tak");
		}
		Exercise exercise = ExerciseDao.loadById(eId);
		if (exercise == null) {
			exercise = new Exercise("wstaw tytuł","wstaw opis");
		}
		request.setAttribute("exercise", exercise);
		HttpSession session = request.getSession();
		session.setAttribute("exercise",  exercise);
		getServletContext().getRequestDispatcher("/views/exerciseEdit.jsp")
		.forward(request, response);
	}



}
