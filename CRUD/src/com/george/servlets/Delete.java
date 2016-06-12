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

@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Delete() {
		super();
	}

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
		Query query = session.createQuery("delete UserModel where id = :userId");
		
		for (int j = 0; j < quieries.size(); j++) {		
			query.setParameter("userId",  quieries.get(j).getId());
			
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
