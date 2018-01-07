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




@WebServlet("/AdmExercises")
public class AdmExercises extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Exercise[] exercises = ExerciseDao.loadAll();
		request.setAttribute("exercises", exercises);
		getServletContext().getRequestDispatcher("/views/admExercises.jsp")
		.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		HttpSession session = request.getSession();
		Exercise exercise = (Exercise) session.getAttribute("exercise");
		exercise.setTitle(title);
		exercise.setDescription(desc);
		ExerciseDao.saveToDB(exercise);
		doGet(request, response);
	}

}
