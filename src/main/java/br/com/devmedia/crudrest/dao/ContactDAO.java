package br.com.devmedia.crudrest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.devmedia.crudrest.domain.Contact;
import br.com.devmedia.crudrest.util.JPAUtil;

public class ContactDAO implements IContactDAO<Contact, Long> {
	
	private EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
	
	public void save(Contact contact) {
		
		entityManager.getTransaction().begin();
		entityManager.persist(contact);
		entityManager.getTransaction().commit();
		entityManager.close();		
	}

	public void update(Contact contact) {
		
		entityManager.getTransaction().begin();
		entityManager.merge(contact);
		entityManager.getTransaction().commit();
		entityManager.close();		
	}

	public void delete(Long id) {

		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.getReference(Contact.class, id));
		entityManager.getTransaction().commit();
		entityManager.close();			
	}

	public Contact findById(Long id) {
		
		return entityManager.find(Contact.class, id);
	}

	public List<Contact> findByName(String name) {
		TypedQuery<Contact> query = entityManager
				.createQuery("from Contact c where c.name like :name", Contact.class);		
		query.setParameter("name", "%" + name + "%");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Contact> findAll() {
		
		return entityManager.createQuery("from Contact").getResultList();
	}

}
