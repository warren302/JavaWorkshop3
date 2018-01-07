package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.Group;
import pl.coderslab.dao.GroupDao;



@WebServlet("/GroupsList")
public class GroupsList extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Group[] groups = GroupDao.loadAll();
		request.setAttribute("groups", groups);
		getServletContext().getRequestDispatcher("/views/groupsList.jsp")
		.forward(request, response);
	}

}
