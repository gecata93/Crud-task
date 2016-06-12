package com.george.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.george.hibernate.GetSessionFactory;
import com.george.hibernate.UserModel;

@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Search() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String idStr = request.getParameter("firstName");
		
		SessionFactory sFactory = GetSessionFactory.getInstance();
		Session session = sFactory.getCurrentSession();

		session.beginTransaction();
		
		String hql = "from UserModel where firstName = :Name";
		
		@SuppressWarnings("unchecked")
		List<UserModel> result = session.createQuery(hql).setParameter("Name", idStr).list();
		
		session.getTransaction().commit();
		GetSessionFactory.closeSessionFactory();
		
		String source =  "<a href=./search.html>Back</a>" + "<br> "+ "<br> " + "<table border= 1px>"
				+"<tr>"
					+"<th>"+ "ID" + "</th>"
					+"<th>" + "First name" + "</th>"
					+"<th>" + "Last name" + "</th>"
					+"<th>" + "Date  of  birth" + "</th>"
					+"<th>" + "Phone Number" + "</th>"
					+"<th>" + "E-mail Address" + "</th>"
				+"</tr>";
		
		for(int i=0;i<result.size();i++){
			
			source += 
					"<tr>" 
						+ "<td>" + result.get(i).getId() + "</td>" 
						+ "<td>" + result.get(i).getFirstName() + "</td>" 
						+ "<td>" + result.get(i).getLastName() + "</td>" 
						+ "<td>" + result.get(i).getDateOfBirth() + "</td>" 
						+ "<td>" + result.get(i).getPhoneNumber() + "</td>"
						+ "<td>" + result.get(i).getEmail() + "</td>" 
					+"</tr>";
			
		} 
		source += "</table>";
		
		response.setContentType("text/html");
		response.getWriter().print(source);
	}

}
