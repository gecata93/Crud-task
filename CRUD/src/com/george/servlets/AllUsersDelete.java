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

@WebServlet("/AllUsersDelete")
public class AllUsersDelete extends HttpServlet {
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
	
		request.getRequestDispatcher("/delete.html").include(request, response);
	
		
		String source = "<table border= 1px>";

		for (int i = 0; i < quieries.size(); i++) {

			source += "<tr>" + "<td>" + quieries.get(i).getId() + "</td>" + "<td>"
					+ quieries.get(i).getFirstName() + "</td>" + "<td>"
					+ quieries.get(i).getLastName() + "</td>" + "<td>"
					+ quieries.get(i).getDateOfBirth() + "</td>" + "<td>"
					+ quieries.get(i).getPhoneNumber() + "</td>" + "<td>"
					+ quieries.get(i).getEmail() + "</td>" + "</tr>";
		}

		source += "</table>";
		out.print(source);
		GetSessionFactory.closeSessionFactory();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
