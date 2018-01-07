package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.User;
import pl.coderslab.dao.UserDao;




@WebServlet("/GroupMembersList")
public class GroupMembersList extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int gId = 0;
		try {
			gId = Integer.parseInt(request.getParameter("gId"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("coś poszło nie tak");
		}
		User[] users = UserDao.loadAllByGrupId(gId);
		request.setAttribute("users", users);
		getServletContext().getRequestDispatcher("/views/listOfUsers.jsp")
		.forward(request, response);
	}

}
