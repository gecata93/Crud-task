package com.george.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.george.hibernate.GetSessionFactory;
import com.george.hibernate.UserModel;

@WebServlet("/Update")
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Edit() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String firstNameEdit = request.getParameter("firstNameEdit");
		String lastNameEdit = request.getParameter("lastNameEdit");
		String dateOfBirthEdit = request.getParameter("dateOfBirthEdit");
		String phoneNumberEdit = request.getParameter("phoneNumberEdit");
		String emailAddressEdit = request.getParameter("emailAddressEdit");
		
		
		SessionFactory sFactory = GetSessionFactory.getInstance();
		Session session = sFactory.getCurrentSession();

		session.beginTransaction();

		Criteria criteria = session.createCriteria(UserModel.class);
		Criterion Crit = Restrictions.isNotNull("id");
		criteria.add(Crit);
		
		@SuppressWarnings("unchecked")
		List<UserModel> quieries = criteria.list();
		Query query = session.createQuery("update UserModel set firstName = :firstNameEdit,"
				+ " lastName = :lastNameEdit, "
				+ "dateOfBirth = :dateOfBirthEdit, "
				+ "phoneNumber = :phoneNumberEdit, "
				+ "email =:emailAddressEdit "
				+ "where id = :userId");
				
		for (int j = 0; j < quieries.size(); j++) {	
			if (quieries.get(j).getId() == Integer.parseInt(id)) {
				query.setParameter("userId",  quieries.get(j).getId());
				query.setParameter("firstNameEdit",  firstNameEdit);
				query.setParameter("lastNameEdit",  lastNameEdit);
				query.setParameter("dateOfBirthEdit",  dateOfBirthEdit);
				query.setParameter("phoneNumberEdit",  phoneNumberEdit);
				query.setParameter("emailAddressEdit",  emailAddressEdit);
			}
			
		}
		query.executeUpdate();
		session.getTransaction().commit();

		response.setContentType("text/html");
		request.getRequestDispatcher("/search.html").include(request, response);
	
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}

}
