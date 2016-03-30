/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dks3counter;

import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.H2Dialect;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author stuby
 */
public class HibernateCharacterDAO {
    
    private SessionFactory sessionFactory;
    
    static Configuration h2Config(Class[] dbClasses) {
        Configuration configuration =
                new Configuration()
                    .setProperty(Environment.DRIVER, "org.h2.Driver")
                    .setProperty(Environment.URL, "jdbc:h2:~/dks3Help")
                    .setProperty(Environment.USER, "sa")
                    .setProperty(Environment.HBM2DDL_AUTO, "update")
                    .setProperty(Environment.DIALECT, H2Dialect.class.getName())
                    .setProperty(Environment.SHOW_SQL, "true");
        for (Class clazz : dbClasses) {
            configuration.addClass(clazz);
        }
        return configuration;
    }
    
    @SuppressWarnings("all")
    public HibernateCharacterDAO() {
        Configuration configuration = h2Config(new Class[]{dks3counter.Character.class});
        sessionFactory = configuration.buildSessionFactory();
    }
    
    public void StoreCharacter(Character c) {
        System.out.println("Storing Character obj: " + c.toString());
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(c);
            session.getTransaction().commit();
            session.flush();
            session.close();
            System.out.println("Character obj successfully stored...");
        }
        catch (Exception ex) {
            System.out.println("Failed to store Character obj.. Reason: " + 
                    ex.getMessage());
        }
    }
    
    public void UpdateCharacter(Character c) { 
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(c);
        session.getTransaction().commit();
        session.flush();
        session.close();
    }
    
    public void RemoveCharacter(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Character c = (Character)session.get(Character.class, id);
        session.delete(c);
        session.getTransaction().commit();
        session.flush();
        session.close();
    }
    
    public Character GetCharacterByID(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Character c = (Character)session.get(Character.class, id);
        session.getTransaction().commit();
        session.flush();
        session.close();
        return c;
    }
    
    public List<Character> GetAllCharacters() {
        List<Character> characters = new LinkedList<>();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        characters = session.createQuery("from Character").list();
        session.getTransaction().commit();
        session.flush();
        session.close();
        return characters;
    }
    
    public List<String> GetAllCharactersName() {
        List<String> retVals = new LinkedList<>();
        List<Character> characters = GetAllCharacters();
        for (Character item : characters) {
            retVals.add(item.getId() + "-" + item.getName());
        }
        return retVals;
    }
}
