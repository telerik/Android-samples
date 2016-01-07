package fragments.dataform;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.visualization.RadDataForm;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import activities.ExampleFragment;


public class DataFormJsonEditFragment extends Fragment implements ExampleFragment {


    @Override
    public String title() {
        return "Json Object Editing";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup layoutRoot = (ViewGroup)inflater.inflate(R.layout.fragment_data_form_json_edit, container, false);
        RadDataForm dataForm = new RadDataForm(this.getActivity());

        String json = loadJSONFromAsset(R.raw.person);

        try {
            JSONObject jsonObject = new JSONObject(json);
            dataForm.setEntity(jsonObject);
        } catch (JSONException e) {
            Log.e("json", "error parsing json", e);
        }

        layoutRoot.addView(dataForm);

        return layoutRoot;
    }

    public String loadJSONFromAsset(int asset) {
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
