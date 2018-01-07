package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.GroupDao;
import pl.coderslab.dao.User;
import pl.coderslab.dao.UserDao;




@WebServlet("/AdmUsers")
public class AdmUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User[] users = UserDao.loadAll();
		request.setAttribute("users", users);
		getServletContext().getRequestDispatcher("/views/admUsers.jsp")
		.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		int gId = 0;
		try {
			gId = Integer.parseInt(request.getParameter("gId"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("cos poszlo nie tak");
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setGroup(GroupDao.loadById(gId));
		UserDao.saveToDB(user);
		doGet(request, response);
	}

}
