package ua.luckydev.other;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

//@ManagedBean(name = "positions")
//@SessionScoped
public enum  Positions
{

    Worker("Рабочий"),
    Manager("Менеджер");
//    Other("Другой");

       private String value;

        private Positions(String value) {
            this.value = value;
        }

    public String getValue()
    {
        return value;
    }
}
