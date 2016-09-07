package fragments.autocomplete;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.telerik.android.sdk.R;
import com.telerik.widget.autocomplete.AutoCompleteAdapter;
import com.telerik.widget.autocomplete.CompletionMode;
import com.telerik.widget.autocomplete.DisplayMode;
import com.telerik.widget.autocomplete.SuggestMode;
import com.telerik.widget.autocomplete.TestModuledAutoComplete;
import com.telerik.widget.autocomplete.TokenModel;

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

import activities.ExampleFragment;


public class AutoCompleteGettingStartedFragment extends Fragment implements ExampleFragment {

    private JSONArray data;

    @Override
    public String title() {
        return "Getting Started";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.autocomplete_getting_started, container, false);

        final TestModuledAutoComplete autocomplete = (TestModuledAutoComplete) rootView.findViewById(R.id.autocmp);
        autocomplete.setSuggestMode(SuggestMode.SUGGEST);
        autocomplete.setDisplayMode(DisplayMode.PLAIN);

        String jsonData = this.getJSONFile(R.raw.countries);
        try{
            JSONObject jObj = new JSONObject(jsonData);
            data = jObj.getJSONArray("data");
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }

        final AutoCompleteAdapter adapter = new AutoCompleteAdapter(this.getContext(),this.getTokenModelObjects(data), R.layout.suggestion_item_layout);
        adapter.setCompletionMode(CompletionMode.STARTS_WITH);
        autocomplete.setAdapter(adapter);

        Button btnSuggest = (Button)rootView.findViewById(R.id.suggestButton);
        btnSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autocomplete.setSuggestMode(SuggestMode.SUGGEST);
            }
        });

        Button btnSuggestAppend = (Button)rootView.findViewById(R.id.suggestAppendButton);
        btnSuggestAppend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autocomplete.setSuggestMode(SuggestMode.SUGGEST_APPEND);
            }
        });

        Button btnAppend = (Button)rootView.findViewById(R.id.appendButton);
        btnAppend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autocomplete.setSuggestMode(SuggestMode.APPEND);
            }
        });

        Button btnStartsWith = (Button)rootView.findViewById(R.id.startsWithButton);
        btnStartsWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setCompletionMode(CompletionMode.STARTS_WITH);
                autocomplete.resetAutocomplete();
            }
        });
        Button btnContains = (Button)rootView.findViewById(R.id.containsButton);
        btnContains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setCompletionMode(CompletionMode.CONTAINS);
                autocomplete.setSuggestMode(SuggestMode.SUGGEST);
                autocomplete.resetAutocomplete();
            }
        });
        return rootView;
    }

    private ArrayList<TokenModel> getTokenModelObjects(JSONArray json) {
        ArrayList<TokenModel> feedData = new ArrayList<TokenModel>();
        JSONObject current = new JSONObject();
        for(int i = 0; i < json.length(); i++) {
            String name = "";
            String flag = "";

            try{
                current = json.getJSONObject(i);
                name = (String)current.get("country");
                flag = (String)current.get("flag");
            }
            catch (JSONException ex){
                ex.printStackTrace();
            }

            int dr = getResources().getIdentifier(flag, "drawable","com.telerik.android.sdk");
            Drawable m = getResources().getDrawable(dr);
            TokenModel token = new TokenModel(name,m, null);
            feedData.add(token);
        }

        return feedData;
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
