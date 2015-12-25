package ua.luckydev.dao;


import ua.luckydev.entity.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Stateless
public class PersonDAO implements Serializable
{
    @PersistenceContext(unitName = "em")
    private EntityManager em;

    public void addPerson(Person person)
    {
        this.em.persist(person);
    }

    public void deletePerson(Person person)
    {
        Person toBeRemoved = em.merge(person);
        em.remove(toBeRemoved);
    }

    public void updatePerson(Person person)
    {
        em.merge(person);
    }

    public Person getPerson(int id)
    {
        System.out.println("IDD - " + id);
        return em.find(Person.class, id);
    }

    public List<Person> listPersons()
    {
        List<Person> personList = em.createQuery("SELECT p FROM Person p ORDER BY p.fio").getResultList(); //вывод по возростанию надо сделать
        return personList;
    }
}