package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.Group;
import pl.coderslab.dao.GroupDao;
import pl.coderslab.dao.User;
import pl.coderslab.dao.UserDao;




@WebServlet("/UserEdit")
public class UserEdit extends HttpServlet {
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
		if (user == null) {
			user = new User("imie i nazwisko","email", "", null);
		}
		request.setAttribute("user", user);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		Group[] groups = GroupDao.loadAll();
		request.setAttribute("groups", groups);
		getServletContext().getRequestDispatcher("/views/userEdit.jsp")
		.forward(request, response);
	}

}
