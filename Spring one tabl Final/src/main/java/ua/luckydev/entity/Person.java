package ua.luckydev.entity;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SessionScoped
@ManagedBean(name = "validPerson")
@Entity
@Table(name = "person")
public class Person  // - ???? implements Serializable
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp="^[a-z]{15}$")
    private String fio;

    @Temporal(TemporalType.DATE)
    private Date dateBirth;

    @Temporal(TemporalType.DATE)
    private Date dateHire;

    String position;
    String manager;
    String info;

    @Transient
    List<Person> listPersons = new ArrayList<>();

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFio()
    {
        return fio;
    }

    public void setFio(String fio)
    {
        this.fio = fio;
    }

    public Date getDateBirth()
    {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth)
    {
        this.dateBirth = dateBirth;
    }

    public Date getDateHire()
    {
        return dateHire;
    }

    public void setDateHire(Date dateHire)
    {
        this.dateHire = dateHire;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getManager()
    {
        return manager;
    }

    public void setManager(String manager)
    {
        this.manager = manager;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public List<Person> getListPersons()
    {
        return listPersons;
    }

    public void setListPersons(List<Person> listPersons)
    {
        this.listPersons = this.listPersons;
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
