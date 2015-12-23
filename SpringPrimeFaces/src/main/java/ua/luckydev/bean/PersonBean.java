package ua.luckydev.bean;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import ua.luckydev.dao.PersonDAO;
import ua.luckydev.entity.Person;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name = "personBean")
@SessionScoped //?!?!?!?!? //Serialisation
//@ViewScoped
public class PersonBean implements Serializable
{
    @ManagedProperty("#{personDAO}")
    PersonDAO personDAO;

    private Person person;
    private List<Person> listPersons;
    
    private List<String> listManagers;
    private List<Boolean> listColumnsIsVisible;
    private List<String> listPositions = new ArrayList<String>();
    private List<String> workersOfManager;

    private final int columnsQuanity = 7; //автоматизировать надо

    @PostConstruct
    public void init()
    {
        listPersons = new ArrayList<Person>();
        listPersons.addAll(personDAO.listPersons());

        listColumnsIsVisible = new ArrayList<Boolean>();

        for (int i = 0; i < columnsQuanity; i++)
        {
            listColumnsIsVisible.add(true);
        }
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
            if(person.getPosition().equals("Менеджер")&&(!person.getFio().equals(this.person.getFio()))) //enum
            {
                listManagers.add(person.getFio());
            }
        }
        return listManagers;
    }

    public List<String> getWorkersOfManager(String managerFIO)

    {
       // System.out.println("blin " + managerFIO);
        workersOfManager = new ArrayList<String>();
        for (Person listPerson : listPersons)
        {
            if((listPerson.getManager()!=null)&&(listPerson.getPosition().equals("Рабочий"))&&(listPerson.getManager().equals(managerFIO))) //enum
            {
                workersOfManager.add(listPerson.getFio());
            }
        }
        return workersOfManager;
    }

    public List<String> getPositions() {  //Рабочий и менеджер надо чтобы были вверху
        for (Person listPerson : listPersons) {
            String pos = listPerson.getPosition();

            if(!listPositions.contains(pos))
            {
                listPositions.add(pos);
            }
        }
        Collections.sort(listPositions);
        return listPositions;
    }

    public List<Boolean> getListColumnsIsVisible() {
        return listColumnsIsVisible;
    }

    public PersonDAO getPersonDAO()  //проверить надо ли
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


    public void newPerson(String value)  //переименовать типа newPerson
    {
        this.person = new Person();
        person.setPosition(value);
    }

    public void changePosition(String value)
    {
        person.setPosition(value);
    }

    public void addPerson()
    {
        personDAO.addPerson(person); //дублирование убрать
        // Add message
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
        if((person.getPosition().equals("Рабочий"))||(person.getPosition().equals("Менеджер")) ) //Enum
        {
            person.setInfo(null);
        }

        if((!person.getPosition().equals("Рабочий")))
        {
            person.setManager(null);
        }

        personDAO.updatePerson(person);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Успешно!", "Данные по сотруднику "+person.getFio()+" обновлены"));
        updateListPersons();
    }

    public void delete(Person person)
    {
        personDAO.deletePerson(person);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Успешно!","Сотрудник "+person.getFio()+" удалён из БД")); //сообщения зачесать
        updateListPersons();
    }

    //для корректной работы функции выбора столбцов
    public void onToggle(ToggleEvent e) {
        listColumnsIsVisible.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }

}