package com.george.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.george.hibernate.GetSessionFactory;
import com.george.hibernate.UserModel;

@WebServlet("/AllUsers")
public class AllUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		SessionFactory sFactory = GetSessionFactory.getInstance();
		Session session = sFactory.getCurrentSession();

		session.beginTransaction();

		Criteria criteria = session.createCriteria(UserModel.class);
		Criterion Crit = Restrictions.isNotNull("id");
		criteria.add(Crit);
		
		
		@SuppressWarnings("unchecked")
		List<UserModel> quieries = criteria.list();
		session.getTransaction().commit();

		// send html
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
	
		request.getRequestDispatcher("/search.html").include(request, response);
		
		String source = "<table border= 1px>"
				+"<tr>"
					+"<th>"+ "ID" + "</th>"
					+"<th>" + "First name" + "</th>"
					+"<th>" + "Last name" + "</th>"
					+"<th>" + "Date  of  birth" + "</th>"
					+"<th>" + "Phone Number" + "</th>"
					+"<th>" + "E-mail Address" + "</th>"
					+"<th>" + "Delete" + "</th>"
					+"<th>" + "Edit" + "</th>"
				+"</tr>";

		for (int i = 0; i < quieries.size(); i++) {
		
			source += "<tr>" + "<td>" + quieries.get(i).getId() + "</td>" + "<td>"
						+ quieries.get(i).getFirstName() + "</td>" + "<td>"
						+ quieries.get(i).getLastName() + "</td>" + "<td>"
						+ quieries.get(i).getDateOfBirth() + "</td>" + "<td>"
						+ quieries.get(i).getPhoneNumber() + "</td>" + "<td>"
						+ quieries.get(i).getEmail() + "</td>" + "<td>"
						+ "<a href=./Delete>Delete</a>"+ "</td>" + "<td>"
						+ "<a href=./edit.html?id="+ quieries.get(i).getId() +">Edit</a>"+ "</td>" 
					+"</tr>";
		}

		source += "</table>";
		out.print(source);
		GetSessionFactory.closeSessionFactory();
	}

}
