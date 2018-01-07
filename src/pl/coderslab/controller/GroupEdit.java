package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.model.Group;


@WebServlet("/GroupEdit")
public class GroupEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int gId = 0;
		try {
			gId = Integer.parseInt(request.getParameter("gId"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("coś poszło nie tak");
		}
		Group group = Group.loadById(gId);
		if (group == null) {
			group = new Group("Nazwa nowej grupy");
		}
		request.setAttribute("group", group);
		HttpSession session = request.getSession();
		session.setAttribute("group", group);
		getServletContext().getRequestDispatcher("/views/groupEdit.jsp")
		.forward(request, response);
	}

}
