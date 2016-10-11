package fragments.dataform;


import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.visualization.annotations.DataFormProperty;
import com.telerik.widget.dataform.visualization.editors.DataFormDateEditor;
import com.telerik.widget.dataform.visualization.editors.DataFormPhoneEditor;

public class Employee {
    private String name;
    private String phone;
    private Long birthDate;
    private Integer id;

    // >> data-form-image-labels
    @DataFormProperty(label = "", hint = "name", index = 0,
            imageResource = R.drawable.ic_dataform_guest)
    public String getName() {
        return name;
    }
    // << data-form-image-labels

    public void setName(String name) {
        this.name = name;
    }

    @DataFormProperty(label = "", hint = "phone", index = 1,
            imageResource = R.drawable.ic_dataform_phone, editor = DataFormPhoneEditor.class)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @DataFormProperty(label = "", hint = "birth date", index = 2,
            imageResource = R.drawable.ic_dataform_calendar, editor = DataFormDateEditor.class)
    public Long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
    }

    @DataFormProperty(label = "", hint = "employee id", index = 3,
            imageResource = R.drawable.ic_dataform_table)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
