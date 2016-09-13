package fragments.autocomplete;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;
import com.telerik.widget.autocomplete.AutoCompleteAdapter;
import com.telerik.widget.autocomplete.CompletionMode;
import com.telerik.widget.autocomplete.DisplayMode;
import com.telerik.widget.autocomplete.RadAutoCompleteTextView;
import com.telerik.widget.autocomplete.SuggestMode;
import com.telerik.widget.autocomplete.TokenModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import activities.ExampleFragment;

public class AutoCompleteCustomizationFragment extends JsonDataLoadFragment implements ExampleFragment {

    private JSONArray data;
    private RadAutoCompleteTextView autocomplete;
    private AutoCompleteAdapter adapter;

    @Override
    public String title() {
        return "Customization";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.autocomplete_customization, container, false);

        autocomplete = (RadAutoCompleteTextView) rootView.findViewById(R.id.autocomplete);
        autocomplete.setSuggestMode(SuggestMode.SUGGEST);
        autocomplete.setDisplayMode(DisplayMode.TOKENS);

        String jsonData = this.getJSONFile(R.raw.countries);
        try{
            JSONObject jObj = new JSONObject(jsonData);
            data = jObj.getJSONArray("data");
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }

        adapter = new AutoCompleteAdapter(this.getContext(),this.getTokenModelObjects(data), R.layout.suggestion_item_layout);
        adapter.setCompletionMode(CompletionMode.CONTAINS);
        autocomplete.setAdapter(adapter);

        Display display = this.getActivity().getWindowManager().getDefaultDisplay();
        int height  =  display.getHeight();
        autocomplete.setSuggestionViewHeight(height/3);
        Drawable img = getResources().getDrawable(R.drawable.search);
        autocomplete.setAutocompleteIcon(img);

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
}
