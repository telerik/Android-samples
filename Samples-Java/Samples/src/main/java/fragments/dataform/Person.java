package fragments.dataform;

import com.telerik.widget.dataform.engine.MailValidator;
import com.telerik.widget.dataform.engine.NotifyPropertyChangedBase;
import com.telerik.widget.dataform.engine.RangeValidator;
import com.telerik.widget.dataform.visualization.annotations.DataFormProperty;
import com.telerik.widget.dataform.visualization.editors.DataFormDateEditor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Person extends NotifyPropertyChangedBase {
    private Integer age = 18;
    private Boolean isEmployed = false;
    private EmployeeType employeeType = EmployeeType.PROGRAMMER;
    private String name = "";
    private String mail = "";
    private Long birthDate = 0L;

    @DataFormProperty(label = "Age", index = 0, validator = RangeValidator.class, group = "Group 1")
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer value) {
        age = value;
        notifyListeners("Age", value);
    }

    @DataFormProperty(label = "Employee Type", index = 1, group = "Group 1")
    public EmployeeType getEmployeeType() {
        return employeeType;
    }
    public void setEmployeeType(EmployeeType value) {
        employeeType = value;
        notifyListeners("EmployeeType", value);
    }

    @DataFormProperty(label = "Name", index = 0, columnIndex = 1, group = "Group 2")
    public String getName() {
        return name;
    }
    public void setName(String value) {
        name = value;
        notifyListeners("Name", value);
    }

    @DataFormProperty(label = "E-mail", index = 2, validator = MailValidator.class, group = "Group 2")
    public String getMail() {
        return mail;
    }
    public void setMail(String value) {
        this.mail = value;
        notifyListeners("Mail", value);
    }

    @DataFormProperty(label = "Employed", index = 2, columnIndex = 1, group = "Group 3")
    public boolean getIsEmployed() {
        return isEmployed;
    }

    public void setIsEmployed(boolean value) {
        isEmployed = value;
        notifyListeners("IsEmployed", value);
    }

    @DataFormProperty(label = "Birth Date", index = 1, columnIndex = 1, editor = DataFormDateEditor.class, group = "Group 3")
    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long value) {
        this.birthDate = value;
        notifyListeners("BirthDate", value);
    }

    @Override
    public String toString() {
        DateFormat dateFormat = SimpleDateFormat.getDateInstance();

        return String.format("Name: %s, Age: %s, Mail: %s, EmployeeType: %s, IsEmployed: %s, BirthDate: %s",
                name, Integer.toString(age), mail, employeeType.toString(), Boolean.toString(isEmployed), dateFormat.format(new Date(birthDate)));
    }
}
