package fragments.autocomplete;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.telerik.android.sdk.R;
import com.telerik.widget.autocomplete.AutoCompleteAdapter;
import com.telerik.widget.autocomplete.CompletionMode;
import com.telerik.widget.autocomplete.CompletionModeContains;
import com.telerik.widget.autocomplete.CompletionModeStartsWith;
import com.telerik.widget.autocomplete.DisplayMode;
import com.telerik.widget.autocomplete.RadAutoCompleteTextView;
import com.telerik.widget.autocomplete.SuggestMode;
import com.telerik.widget.autocomplete.TokenModel;
import java.util.ArrayList;

import activities.ExampleFragment;


public class AutoCompleteGettingStartedFragment extends JsonDataLoadFragment implements ExampleFragment {

    // >> autocomplete-array
    private String[] data = new String[]{"Australia", "Albania","Austria", "Argentina", "Maldives","Bulgaria","Belgium","Cyprus","Italy","Japan",
                                        "Denmark","Finland","France","Germany","Greece","Hungary","Ireland",
                                        "Latvia","Luxembourg","Macedonia","Moldova","Monaco","Netherlands","Norway",
                                        "Poland","Romania","Russia","Sweden","Slovenia","Slovakia","Turkey","Ukraine",
                                        "Vatican City", "Chad", "China", "Chile"};
    // << autocomplete-array

    private RadAutoCompleteTextView autocomplete;
    private AutoCompleteAdapter adapter;

    @Override
    public String title() {
        return "Getting Started";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.autocomplete_getting_started, container, false);
        // >> autocomplete-load
        autocomplete = (RadAutoCompleteTextView) rootView.findViewById(R.id.autocmp);
        // << autocomplete-load

        // >> autocomplete-suggest-display
        autocomplete.setSuggestMode(SuggestMode.SUGGEST);
        autocomplete.setDisplayMode(DisplayMode.PLAIN);
        // << autocomplete-suggest-display

        // >> autocomplete-adapter
        adapter = new AutoCompleteAdapter(this.getContext(),this.getTokenModelObjects(), R.layout.suggestion_item_layout);
        adapter.setCompletionMode(new CompletionModeStartsWith());
        autocomplete.setAdapter(adapter);
        // << autocomplete-adapter

        Display display = this.getActivity().getWindowManager().getDefaultDisplay();
        int height  =  display.getHeight();
        autocomplete.setSuggestionViewHeight(height/4);

        this.setButtonAction(rootView);

        return rootView;
    }

    private void setButtonAction(View rootView){
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
                adapter.setCompletionMode(new CompletionModeStartsWith());
                autocomplete.resetAutocomplete();
            }
        });
        Button btnContains = (Button)rootView.findViewById(R.id.containsButton);
        btnContains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setCompletionMode(new CompletionModeContains());
                autocomplete.setSuggestMode(SuggestMode.SUGGEST);
                autocomplete.resetAutocomplete();
            }
        });
        Button btnTokens = (Button)rootView.findViewById(R.id.tokens_mode_btn);
        btnTokens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autocomplete.setDisplayMode(DisplayMode.TOKENS);
                autocomplete.resetAutocomplete();
            }
        });

        Button btnPlain = (Button)rootView.findViewById(R.id.plain_mode_btn);
        btnPlain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autocomplete.setDisplayMode(DisplayMode.PLAIN);
                autocomplete.resetAutocomplete();
            }
        });

    }

    // >> autocomplete-token-models
    private ArrayList<TokenModel> getTokenModelObjects() {
        ArrayList<TokenModel> feedData = new ArrayList<TokenModel>();
        for(int i = 0; i < this.data.length; i++){
            TokenModel token = new TokenModel(this.data[i], null);
            feedData.add(token);
        }

        return feedData;
    }
    // << autocomplete-token-models
}
