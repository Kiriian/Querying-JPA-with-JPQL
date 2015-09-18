/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entity.Customer;
import entity.Employee;
import entity.Office;
import facade.ClassicFacade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Jeanette
 */
public class Tester
{

    public static void main(String[] args)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("classicmodelsPU");
        ClassicFacade cf = new ClassicFacade(emf);
        
        Employee emp = new Employee(1716, "Møller", "Jeanette", "x606", "thing@live.dk", "Sales rep");
        System.out.println(cf.createEmployee(emp).toString());

        System.out.println(cf.updateCustomer(cf.findCustomer(103)).toString());
        
        System.out.println(cf.getEmployeeCount());
        
        System.out.println(cf.getCustomerInCity("Barcelona").toString());
        
        System.out.println(cf.getEmployeeMaxCustomer());
        
        System.out.println(cf.getOrdersOnHold().toString());
        
        System.out.println(cf.getOrdersOnHold(144).toString());

    }
}
