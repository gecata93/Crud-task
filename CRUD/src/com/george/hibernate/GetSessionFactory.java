package com.george.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Get one SessionFactory for the whole application. This class is Singleton.
 */
public final class GetSessionFactory {

	private static SessionFactory sessionFactory;
	/**
	 * Private constructor for the class GetSessionFactory.
	 */
	private GetSessionFactory() {
	}

	/**
	 * Through this method, the entire application gets the SessionFactory.
	 * Singleton.
	 * 
	 * @return SessionFactory singleton, for the whole application.
	 */
	public static SessionFactory getInstance() {
		if (sessionFactory == null) {
			Configuration configuration = new Configuration().configure();

			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();

			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}

	/**
	 * Closes the session factory.
	 */
	public static void closeSessionFactory() {
		sessionFactory.close();
		sessionFactory = null;
	}
}
