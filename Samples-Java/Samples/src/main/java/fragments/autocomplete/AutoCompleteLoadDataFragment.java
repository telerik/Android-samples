package fragments.autocomplete;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.common.Function2Async;
import com.telerik.android.common.Procedure;
import com.telerik.android.sdk.R;
import com.telerik.widget.autocomplete.AutoCompleteAdapter;
import com.telerik.widget.autocomplete.DisplayMode;
import com.telerik.widget.autocomplete.RadAutoCompleteTextView;
import com.telerik.widget.autocomplete.SuggestMode;
import com.telerik.widget.autocomplete.TokenModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

public class AutoCompleteLoadDataFragment extends Fragment implements ExampleFragment {

    private static RadAutoCompleteTextView autocomplete;
    private AutoCompleteAdapter adapter;
    private static List<TokenModel> remoteData;

    @Override
    public String title() {
        return "Loading Remote Data";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.autocomplete_load_data, container, false);
        rootView.setFocusableInTouchMode(true);

        autocomplete = (RadAutoCompleteTextView) rootView.findViewById(R.id.autocomplete);

        autocomplete.setSuggestMode(SuggestMode.SUGGEST);
        autocomplete.setDisplayMode(DisplayMode.PLAIN);
        autocomplete.setAutocompleteHint("Choose airport");
        autocomplete.setUsingAsyncData(true);

        adapter = new AutoCompleteAdapter(this.getContext(),new ArrayList<TokenModel>(), R.layout.suggestion_item_layout);
        adapter.setCompletionMode(FeedAutoCompleteTask.STARTS_WITH_REMOTE);
        autocomplete.setAdapter(adapter);

        Display display = this.getActivity().getWindowManager().getDefaultDisplay();
        int height  =  display.getHeight();
        autocomplete.setSuggestionViewHeight(height/4);

        return rootView;
    }


    private static class FeedAutoCompleteTask extends AsyncTask<String, String, Void> {
        JSONArray data;
        private static Procedure<List<TokenModel>> remoteCallback;
        private static String filter;

        public FeedAutoCompleteTask() {
        }

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL("http://www.telerik.com/docs/default-source/ui-for-ios/airports.json?sfvrsn=2");

                HttpURLConnection urlConnection = (HttpURLConnection) url
                        .openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-length", "0");
                urlConnection.setUseCaches(false);
                urlConnection.setAllowUserInteraction(false);
                urlConnection.connect();
                int status = urlConnection.getResponseCode();

                if (status == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    char[] buffer = new char[1024];
                    int n;
                    Writer writer = new StringWriter();

                    while ((n = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, n);
                    }

                    String json = writer.toString();

                    try{
                        JSONObject jObj = new JSONObject(json);
                        data = jObj.getJSONArray("airports");
                    }
                    catch(JSONException ex){
                        ex.printStackTrace();
                    }
                }

            } catch (IOException e) {
                Log.e("IOException", e.toString());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            List<TokenModel> filtered = new ArrayList<>();
            remoteData = getTokenModelObjects(data);
            for (TokenModel item : remoteData) {
                String text = item.getText();
                if(text.toLowerCase().startsWith(filter.toLowerCase())) {
                    filtered.add(item);
                }
            }
            remoteCallback.apply(filtered);
            autocomplete.resolveAfterFilter(autocomplete.getTextField().getText().toString());

        }

        public static Function2Async<String, List<TokenModel>, List<TokenModel>> STARTS_WITH_REMOTE = new Function2Async<String, List<TokenModel>, List<TokenModel>>() {
            @Override
            public void apply(String filterString, List<TokenModel> originalCollection, Procedure<List<TokenModel>> callback) {
                remoteCallback = callback;
                filter = filterString;
                System.out.println("Custom Apply");
                if (originalCollection.size() == 0) {
                    FeedAutoCompleteTask task  = new FeedAutoCompleteTask();
                    task.execute();
                }
            }
        };
    }

    private static ArrayList<TokenModel> getTokenModelObjects(JSONArray json) {
        ArrayList<TokenModel> feedData = new ArrayList<TokenModel>();
        JSONObject current = new JSONObject();
        for(int i = 0; i < json.length(); i++) {
            String airport = "";

            try{
                current = json.getJSONObject(i);
                String fullname = (String)current.get("FIELD2");
                String abr = (String)current.get("FIELD5");
                airport = fullname + "," + abr;
            }
            catch (JSONException ex){
                ex.printStackTrace();
            }

            TokenModel token = new TokenModel(airport, null);
            feedData.add(token);
        }

        return feedData;
    }
}
