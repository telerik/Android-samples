package fragments.autocomplete;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.telerik.android.sdk.R;
import com.telerik.widget.autocomplete.AutoCompleteAdapter;
import com.telerik.widget.autocomplete.CompletionMode;
import com.telerik.widget.autocomplete.DisplayMode;
import com.telerik.widget.autocomplete.LayoutMode;
import com.telerik.widget.autocomplete.SuggestMode;
import com.telerik.widget.autocomplete.TestModuledAutoComplete;
import com.telerik.widget.autocomplete.TokenModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import activities.ExampleFragment;

public class AutoCompleteTokensFragment extends JsonDataLoadFragment implements ExampleFragment {

    private JSONArray data;
    private TestModuledAutoComplete autocomplete = null;

    public String title() {
        return "Token Layouts";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.autocomplete_tokens, container, false);

        autocomplete = (TestModuledAutoComplete) rootView.findViewById(R.id.autocmp);
        autocomplete.setSuggestMode(SuggestMode.SUGGEST);
        autocomplete.setDisplayMode(DisplayMode.TOKENS);
        autocomplete.setTokensLayoutMode(LayoutMode.HORIZONTAL);

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

        this.setButtonActions(rootView);

        return rootView;
    }

    private void setButtonActions(View rootView){
        Button horizontalBtn = (Button)rootView.findViewById(R.id.horizontal_layout_btn);
        Button wrapBtn = (Button)rootView.findViewById(R.id.wrap_layout_btn);

        horizontalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autocomplete.setTokensLayoutMode(LayoutMode.HORIZONTAL);
                autocomplete.resetAutocomplete();
            }
        });

        wrapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autocomplete.setTokensLayoutMode(LayoutMode.WRAP);
                autocomplete.resetAutocomplete();
            }
        });

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

}
