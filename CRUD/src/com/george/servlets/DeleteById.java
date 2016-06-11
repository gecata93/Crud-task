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

@WebServlet("/DeleteById")
public class DeleteById extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteById() {
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
		
	    UserModel user = (UserModel) session.load(UserModel.class, id);
	     
	    session.delete(user);
	    session.getTransaction().commit();
	        
	       String source = 
	        		
	        		"<a href=./delete.html>Back</a>"+ "<br> "
	        		+ "<table border= 1px>" 
	        		+"<tr>" 
						+ "<td>" + "ID: " + "</td>"
						+ "<td>" + user.getId() + "</td>"
					+"</tr>"
					
					+"<tr>" 
						+ "<td>" + "First Name: " + "</td>"
						+ "<td>" + user.getFirstName() + "</td>"
					+"</tr>"
					
					+ "</table>"
					+"<br>"+ "User deleted!";

	    	response.setContentType("text/html");
			response.getWriter().print(source);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
