package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.Solution;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.User;
import pl.coderslab.dao.UserDao;




@WebServlet("/UserDetails")
public class UserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int uId = 0;
		try {
			uId = Integer.parseInt(request.getParameter("uId"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("coś poszło nie tak");
		}
		User user = UserDao.loadById(uId);
		request.setAttribute("user", user);
		if (user != null) {
			Solution[] solutions = SolutionDao.loadAllByUserId(user.getId());
			request.setAttribute("solutions", solutions);
		}
		request.getServletContext().getRequestDispatcher("/views/userDetails.jsp")
		.forward(request, response);
	}

}
