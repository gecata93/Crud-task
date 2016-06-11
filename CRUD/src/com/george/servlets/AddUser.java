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

@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String dateOfBirth = request.getParameter("dateOfBirth");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");

		UserModel user = new UserModel(firstName, lastName, dateOfBirth, phoneNumber, email);

		SessionFactory sFactory = GetSessionFactory.getInstance();
		Session session = sFactory.getCurrentSession();

		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		
		String source = 
        		"<a href=./index.html>Back</a>"+ "<br> "
				+"<br>"+ "User Added!";

    	response.setContentType("text/html");
		response.getWriter().print(source);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
