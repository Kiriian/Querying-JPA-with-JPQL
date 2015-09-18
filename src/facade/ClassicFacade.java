/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Customer;
import entity.Employee;
import entity.Office;
import entity.Orders;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jeanette
 */
public class ClassicFacade
{

    EntityManagerFactory emf;

    public ClassicFacade(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public Employee createEmployee(Employee emp)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            Office o = em.find(Office.class, "1");
            emp.setOffice(o);
            em.persist(emp);
            em.getTransaction().commit();
            Employee empReturnValue = (Employee) em.createNamedQuery("Employee.findByEmployeeNumber").setParameter("employeeNumber", emp.getEmployeeNumber()).getSingleResult();
            return empReturnValue;
        } finally
        {
            em.close();
        }
    }

    public Customer findCustomer(int customerNumber)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            return em.find(Customer.class, customerNumber);
        } finally
        {
            em.close();
        }
    }

    public Customer updateCustomer(Customer cust)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.merge(cust);
            em.getTransaction().commit();
            Customer custReturnValue = (Customer) em.createNamedQuery("Customer.findByCustomerNumber").setParameter("customerNumber", cust.getCustomerNumber()).getSingleResult();
            return custReturnValue;
        } finally
        {
            em.close();
        }
    }

    public long getEmployeeCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long i = (long) em.createQuery("SELECT COUNT(e) From Employee e").getSingleResult();
            return i;
        } finally
        {
            em.close();
        }
    }

    public List<Customer> getCustomerInCity(String city)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<Customer> query = em.createNamedQuery("Customer.findByCity", Customer.class).setParameter("city", city);
            List<Customer> customerList = query.getResultList();
            return customerList;
        } finally
        {
            em.close();
        }
    }

    public Employee getEmployeeMaxCustomer()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            Employee emp = em.createQuery("SELECT e FROM Employee e ORDER BY e.extension", Employee.class).setMaxResults(1).getSingleResult();
            return emp;
        } finally
        {
            em.close();
        }
    }

    public List<Orders> getOrdersOnHold()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            return em.createNamedQuery("Orders.findByStatus").setParameter("status", "On Hold").getResultList();
        } finally
        {
            em.close();
        }
    }

    public List<Orders> getOrdersOnHold(int customerNumber)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            List<Orders> query = em.createQuery("SELECT o FROM Orders o WHERE o.customer.customerNumber = :customerNumber AND o.status = :status ").setParameter("customerNumber", customerNumber).setParameter("status", "On Hold").getResultList();
            return query;
        } finally
        {
            em.close();
        }
    }
}
