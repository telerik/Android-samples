package fragments.dataform;

import com.telerik.widget.dataform.engine.MailValidator;
import com.telerik.widget.dataform.engine.NotifyPropertyChangedBase;
import com.telerik.widget.dataform.engine.RangeValidator;
import com.telerik.widget.dataform.visualization.annotations.DataFormProperty;
import com.telerik.widget.dataform.visualization.editors.DataFormDateEditor;
import com.telerik.widget.dataform.visualization.editors.DataFormRadAutoCompleteEditor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking extends NotifyPropertyChangedBase {
    private String[] from = { "Akureyri, AEY", "Goroka, GKA" };
    private String to = "Akureyri, AEY";

    // >> dataform-autocomplete-annotation
    @DataFormProperty(
            label = "From",
            index = 0,
            editor = DataFormRadAutoCompleteEditor.class
    )
    public String[] getFrom() {
        return from;
    }
    public void setFrom(String[] value) {
        from = value;
        notifyListeners("From", value);
    }
    // << dataform-autocomplete-annotation

    @DataFormProperty(label = "To", index = 1, editor = DataFormRadAutoCompleteEditor.class)
    public String getTo() {
        return to;
    }
    public void setTo(String value) {
        to = value;
        notifyListeners("To", value);
    }
}
