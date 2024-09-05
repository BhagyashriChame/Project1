package com.sakshi.atm.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MyConnection {
	private MyConnection() {};
	private static EntityManager entityManager;
	public static EntityManager getEntityManagerObject()
	{
		if(entityManager==null)
		{
			EntityManagerFactory  entityManagerFactory=Persistence.createEntityManagerFactory("sakshi");
			entityManager=entityManagerFactory.createEntityManager();
		}
		return entityManager;
	}

}
