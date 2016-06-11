package com.george.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.george.hibernate.GetSessionFactory;
import com.george.hibernate.UserModel;

@WebServlet("/SearchById")
public class SearchById extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchById() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idStr = request.getParameter("id");
		int id = Integer.parseInt(idStr);

		SessionFactory sFactory = GetSessionFactory.getInstance();
		Session session = sFactory.getCurrentSession();

		session.beginTransaction();
		UserModel user = (UserModel) session.get(UserModel.class, id);
		session.getTransaction().commit();

		GetSessionFactory.closeSessionFactory();

		String source = "<table border= 1px>"
							+"<tr>" 
								+ "<td>" + "ID: " + "</td>"
								+ "<td>" + user.getId() + "</td>"
							+"</tr>"
							
							+"<tr>" 
								+ "<td>" + "First Name: " + "</td>"
								+ "<td>" + user.getFirstName() + "</td>"
							+"</tr>"
							
							+"<tr>" 
								+ "<td>" + "Last Name: " + "</td>"
								+ "<td>" + user.getLastName() + "</td>"
							+"</tr>"
							
							+"<tr>" 
								+ "<td>" + "Date of birth: " + "</td>"
								+ "<td>" + user.getDateOfBirth() + "</td>"
							+"</tr>"
							
							+"<tr>" 
								+ "<td>" + "Phone Number: " + "</td>"
								+ "<td>" + user.getPhoneNumber() + "</td>"
							+"</tr>"
								
							+"<tr>" 
								+ "<td>" + "E-mail Address: " + "</td>"
								+ "<td>" + user.getEmail() + "</td>"
							+"</tr>"
							+ "</table>";

		response.setContentType("text/html");
		response.getWriter().print(source);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
