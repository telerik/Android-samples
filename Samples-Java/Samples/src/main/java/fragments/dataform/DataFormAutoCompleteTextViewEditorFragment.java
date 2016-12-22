package fragments.dataform;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.telerik.android.common.Function;
import com.telerik.android.sdk.R;
import com.telerik.widget.autocomplete.AutoCompleteAdapter;
import com.telerik.widget.autocomplete.DisplayMode;
import com.telerik.widget.autocomplete.RadAutoCompleteTextView;
import com.telerik.widget.autocomplete.TokenModel;
import com.telerik.widget.dataform.engine.EntityProperty;
import com.telerik.widget.dataform.engine.EntityPropertyCore;
import com.telerik.widget.dataform.engine.PropertyChangedListener;
import com.telerik.widget.dataform.visualization.RadDataForm;
import com.telerik.widget.dataform.visualization.core.EntityPropertyEditor;
import com.telerik.widget.dataform.visualization.editors.DataFormRadAutoCompleteEditor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

public class DataFormAutoCompleteTextViewEditorFragment extends Fragment implements ExampleFragment {
    private JSONArray jsonObjects;
    private List<TokenModel> data;
    private RadDataForm dataForm;


    @Override
    public String title() {
        return "AutoComplete editor";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootLayout = (ViewGroup)inflater.inflate(R.layout.fragment_dataform_autocompletetextview_editor, container, false);

        this.dataForm = (RadDataForm)rootLayout.findViewById(R.id.data_form_autocompletetextview_editor);
        this.dataForm.setEntity(new Booking());

        String jsonData = this.getJSONFile(R.raw.airports);
        try {
            JSONObject jObj = new JSONObject(jsonData);
            jsonObjects = jObj.getJSONArray("airports");
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }

        List<String> list = new ArrayList<>();

        this.data = new ArrayList<TokenModel>();
        for (int i = 0; i < jsonObjects.length(); i++){
            String fullName = "";
            String abbreviationName = "";

            try {
                fullName = (String)jsonObjects.getJSONObject(i).get("FIELD2");
                abbreviationName = (String)jsonObjects.getJSONObject(i).get("FIELD5");
            }
            catch( JSONException ex){
                ex.printStackTrace();
            }

            String text = fullName + ", " + abbreviationName;

            TokenModel current = new TokenModel(text, null);
            list.add(text);

            this.data.add(current);
        }

        // >> dataform-autocomplete-updateValues
        ((EntityPropertyCore)this.dataForm.getPropertyByName("From")).updateValues(list.toArray());
        // << dataform-autocomplete-updateValues

        AutoCompleteAdapter adapter = new AutoCompleteAdapter(this.getContext(), this.data, R.layout.suggestion_item_layout);
        adapter.setHighlightColor(Color.parseColor("#187390"));

        Display display = this.getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        DataFormRadAutoCompleteEditor fromEditor = (DataFormRadAutoCompleteEditor) dataForm.getExistingEditorForProperty("From");
        TextView fromHeader = (TextView) fromEditor.getHeaderView();
        fromHeader.setTextColor(Color.parseColor("#187390"));
        RadAutoCompleteTextView fromAutocomplete = (RadAutoCompleteTextView) fromEditor.getEditorView();
        // >> dataform-autocomplete-updateadapter
        fromEditor.updateAdapter();
        // << dataform-autocomplete-updateadapter
        // >> dataform-autocomplete-setdisplaymode
        fromEditor.setDisplayMode(DisplayMode.TOKENS);
        // << dataform-autocomplete-setdisplaymode
        fromAutocomplete.setAutocompleteHint("Airport or city");


        DataFormRadAutoCompleteEditor toEditor = (DataFormRadAutoCompleteEditor) dataForm.getExistingEditorForProperty("To");
        TextView toHeader = (TextView) toEditor.getHeaderView();
        toHeader.setTextColor(Color.parseColor("#187390"));
        RadAutoCompleteTextView toAutocomplete = (RadAutoCompleteTextView) toEditor.getEditorView();
        toEditor.setDisplayMode(DisplayMode.PLAIN);
        toEditor.setAdapter(adapter);
        toAutocomplete.setSuggestionViewHeight(height/4 - 55);
        toAutocomplete.setAutocompleteHint("Airport or city");

        return rootLayout;
    }

    public String getJSONFile(int asset) {
        String json;
        try {
            InputStream is = getResources().openRawResource(asset);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }

            json = writer.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
