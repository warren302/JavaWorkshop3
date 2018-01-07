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




@WebServlet("/AdmUsersGroups")
public class AdmUsersGroups extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Group[] groups = GroupDao.loadAll();
		request.setAttribute("groups", groups);
		getServletContext().getRequestDispatcher("/views/admUsersGroups.jsp")
		.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		HttpSession session = request.getSession();
		Group group = (Group) session.getAttribute("group");
		group.setName(name);
		GroupDao.saveToDB(group);
		doGet(request, response);
	}

}
