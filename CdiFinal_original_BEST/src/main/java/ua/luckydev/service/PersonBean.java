package ua.luckydev.service;

import ua.luckydev.dao.PersonDAO;
import ua.luckydev.entity.Person;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "personBean")
@SessionScoped
public class PersonBean implements Serializable
{
    @Inject
    PersonDAO personDAO;

    private Person person;
    private List<Person> listPersons;
    private List<String> listManagers;
    private List<Boolean> listColumnsIsVisible;
    private List<String> listPositions;
    private List<String> workersOfManager;

    private final int columnsQuanity = 7; //автоматизировать надо

    @PostConstruct
    public void init()
    {
        person = new Person();
        listManagers = new ArrayList<String>();
        listPersons = new ArrayList<Person>();
        listPersons.addAll(personDAO.listPersons());
        listPositions = new ArrayList<String>();
        listColumnsIsVisible = new ArrayList<Boolean>();
        listManagers = new ArrayList<String>();

        for (int i = 0; i < columnsQuanity; i++)
        {
            listColumnsIsVisible.add(true);
        }
    }

    public List<Boolean> getListColumnsIsVisible()
    {
        return listColumnsIsVisible;
    }

    public PersonDAO getPersonDAO()
    {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    public List<Person> getListPersons()
    {
        return listPersons;
    }

    public void addPerson()
    {
        personDAO.addPerson(person);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Успешно!", "Сотрудник " + this.person.getFio() + " добавлен в БД"));
        updateListPersons();
    }

    public void getPersonById(int id)
    {
        this.person = personDAO.getPerson(id); //без this тоже можно
    }

    public void updatePerson()
    {
        if ((person.getPosition().equals("Рабочий")) || (person.getPosition().equals("Менеджер"))) //Enum
        {
            person.setInfo(null);
        }

        if ((!person.getPosition().equals("Рабочий")))
        {
            person.setManager(null);
        }

        Person oldPerson = personDAO.getPerson(person.getId());

        if ((person.getPosition().equals("Менеджер") && (person.getFio() != oldPerson.getFio())))
        {
            for (Person oldWorker : listPersons)
            {
                System.out.println(oldWorker);
                if ((oldWorker.getManager() != null) && (oldWorker.getPosition().equals("Рабочий")) && (oldWorker.getManager().equals(oldPerson.getFio()))) //enum
                {
                    oldWorker.setManager(person.getFio());
                    personDAO.updatePerson(oldWorker);
                }
            }
        }

        personDAO.updatePerson(person);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Успешно!", "Данные по сотруднику " + person.getFio() + " обновлены"));
        updateListPersons();
    }

    public void delete(Person person)
    {
        personDAO.deletePerson(person);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Успешно!", "Сотрудник " + person.getFio() + " удалён из БД")); //сообщения зачесать
        updateListPersons();
    }

    public void updateListPersons()
    {
        listPersons.clear();
        listPersons.addAll(personDAO.listPersons());
    }

    public List<String> getListManagers()
    {
        listManagers = new ArrayList<String>();
        for (Person person : listPersons)
        {
            if (person.getPosition().equals("Менеджер") && (!person.getFio().equals(this.person.getFio()))) //enum
            {
                listManagers.add(person.getFio());
            }
        }
        return listManagers;
    }

    public List<String> getWorkersOfManager(String managerFIO)

    {
        workersOfManager = new ArrayList<String>();
        for (Person listPerson : listPersons)
        {
            if ((listPerson.getManager() != null) && (listPerson.getPosition().equals("Рабочий")) && (listPerson.getManager().equals(managerFIO))) //enum
            {
                workersOfManager.add(listPerson.getFio());
            }
        }
        return workersOfManager;
    }

    public List<String> getPositions()
    {
        for (Person listPerson : listPersons)
        {
            String pos = listPerson.getPosition();

            if (!listPositions.contains(pos) && (pos != "Другой"))
            {
                listPositions.add(pos);
            }
        }
        Collections.sort(listPositions);
        return listPositions;
    }


    public void newPerson(String value)
    {
        this.person = new Person();
        person.setPosition(value);
    }

    public void changePosition(String value)
    {
        person.setPosition(value);
    }

    //для корректной работы функции выбора столбцов
    public void onToggle(ToggleEvent e)
    {
        listColumnsIsVisible.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }

}

