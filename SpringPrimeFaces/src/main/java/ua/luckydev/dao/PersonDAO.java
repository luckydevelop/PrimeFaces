package ua.luckydev.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.luckydev.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Repository
public class PersonDAO implements Serializable
{
    @PersistenceContext
    private EntityManager em;

    @Transactional // ?!?!?!?!?
    public void addPerson(Person person) {
        this.em.persist(person);
    }

    @Transactional
    public void deletePerson (Person person){
       Person toBeRemoved = em.merge(person);
       em.remove(toBeRemoved);
    }

    @Transactional
    public void updatePerson (Person person)
    {
        em.merge(person);
    }

    @Transactional
    public Person getPerson(int id) {
        System.out.println("IDD - " + id);
       return em.find(Person.class, id);
    }

    //@SuppressWarnings("unchecked") //-????
    public List<Person> listPersons() {
        List<Person> personList = em.createQuery("SELECT p FROM Person p ORDER BY p.fio").getResultList(); //вывод по возростанию надо сделать
        return personList;
    }

}