package com.work.api.dao;

import com.work.api.model.Book;
import com.work.api.model.Loan;
import com.work.api.model.User;
import com.work.api.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.List;

public class LoanDAO {

    public boolean lendBook(User user, Book book) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Book managedBook = em.find(Book.class, book.getBookId());
            if (managedBook == null) {
                tx.rollback();
                return false;
            }
            if (managedBook.getAvailableCopies() > 0) {
                managedBook.setAvailableCopies(managedBook.getAvailableCopies() - 1);
                Loan newLoan = new Loan(user, managedBook);
                em.persist(newLoan);
                tx.commit();
                return true;
            } else {
                tx.rollback();
                return false;
            }
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public void returnBook(Loan loan) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Loan managedLoan = em.find(Loan.class, loan.getLoanId());
            if (managedLoan != null && managedLoan.getReturnDate() == null) {
                managedLoan.setReturnDate(LocalDateTime.now());
                Book managedBook = managedLoan.getBook();
                managedBook.setAvailableCopies(managedBook.getAvailableCopies() + 1);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Loan> findActiveLoans() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT l FROM Loan l WHERE l.returnDate IS NULL ORDER BY l.loanDate DESC";
            return em.createQuery(jpql, Loan.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Loan> findReturnedLoans() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT l FROM Loan l WHERE l.returnDate IS NOT NULL ORDER BY l.returnDate DESC";
            return em.createQuery(jpql, Loan.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Loan> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT l FROM Loan l ORDER BY l.loanDate DESC";
            return em.createQuery(jpql, Loan.class).getResultList();
        } finally {
            em.close();
        }
    }
}