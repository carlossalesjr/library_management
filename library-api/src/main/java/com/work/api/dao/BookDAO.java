package com.work.api.dao;

import com.work.api.model.Book;
import com.work.api.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class BookDAO implements GenericDAO<Book> {
    
    public void save(Book book) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(book);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Book findById(Long bookId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Book.class, bookId);
        } finally {
            em.close();
        }
    }

    public List<Book> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Long bookId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Book managedBook = em.find(Book.class, bookId);
            em.remove(managedBook);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
