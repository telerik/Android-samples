package fragments.autocomplete;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    private View exampleMain;
    private View connectionInfo;
    private Button refresh;

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

        exampleMain = rootView.findViewById(R.id.exampleMainContainer);
        connectionInfo = rootView.findViewById(R.id.connectionInfoContainer);
        refresh = (Button) rootView.findViewById(R.id.retryButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isConnectionAvailable = isConnectionAvailable(getActivity());
                updateConnectivity(isConnectionAvailable);
            }
        });

        boolean isConnectionAvailable = isConnectionAvailable(getActivity());
        updateConnectivity(isConnectionAvailable);

        autocomplete.setSuggestMode(SuggestMode.SUGGEST);
        autocomplete.setDisplayMode(DisplayMode.PLAIN);
        autocomplete.setAutocompleteHint("Choose airport");
        // >> set-async-data
        autocomplete.setUsingAsyncData(true);
        adapter = new AutoCompleteAdapter(this.getContext(), new ArrayList<TokenModel>(),
                R.layout.suggestion_item_layout);
        // << set-async-data
        adapter.setCompletionMode(STARTS_WITH_REMOTE);
        autocomplete.setAdapter(adapter);

        Display display = this.getActivity().getWindowManager().getDefaultDisplay();
        int height = display.getHeight();
        autocomplete.setSuggestionViewHeight(height / 4);

        return rootView;
    }

    private void updateConnectivity(boolean isNetwork) {
        exampleMain.setVisibility(isNetwork ? View.VISIBLE : View.GONE);
        connectionInfo.setVisibility(isNetwork ? View.GONE : View.VISIBLE);
    }

    // >> autocomplete-remote-full
    private class FeedAutoCompleteTask extends AsyncTask<String, String, Void> {
        JSONArray data;
        private Procedure<List<TokenModel>> remoteCallback;
        private String filter;

        public FeedAutoCompleteTask(Procedure<List<TokenModel>> callback, String filterString) {
            this.remoteCallback = callback;
            this.filter = filterString;
        }

        protected void onPreExecute() {

        }

        // >> autocomplete-remote-do-in-background
        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL
                        ("http://www.telerik.com/docs/default-source/ui-for-ios/airports.json?sfvrsn=2");

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-length", "0");
                urlConnection.setUseCaches(false);
                urlConnection.setAllowUserInteraction(false);
                urlConnection.connect();
                int status = urlConnection.getResponseCode();

                if (status == 200) {
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(urlConnection.getInputStream()));
                    char[] buffer = new char[1024];
                    int n;
                    Writer writer = new StringWriter();

                    while ((n = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, n);
                    }

                    String json = writer.toString();

                    try {
                        JSONObject jObj = new JSONObject(json);
                        data = jObj.getJSONArray("airports");
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }

            } catch (IOException e) {
                Log.e("IOException", e.toString());
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return null;
        }
        // << autocomplete-remote-do-in-background

        // >> autocomplete-remote-on-post-execute
        @Override
        protected void onPostExecute(Void result) {
            List<TokenModel> filtered = new ArrayList<>();
            remoteData = getTokenModelObjects(data);
            for (TokenModel item : remoteData) {
                String text = item.getText();
                if (text.toLowerCase().startsWith(filter.toLowerCase())) {
                    filtered.add(item);
                }
            }
            remoteCallback.apply(filtered);
            autocomplete.resolveAfterFilter(autocomplete.getTextField().getText().toString(),
                    true);

        }
        // << autocomplete-remote-on-post-execute

    }

    // >> autocomplete-remote-completion-mode
    public Function2Async<String, List<TokenModel>, List<TokenModel>> STARTS_WITH_REMOTE
            = new Function2Async<String, List<TokenModel>, List<TokenModel>>() {
        @Override
        public void apply(String filterString, List<TokenModel> originalCollection,
                          Procedure<List<TokenModel>> callback) {
            FeedAutoCompleteTask task =
                    new FeedAutoCompleteTask(callback, filterString);
            task.execute();
        }
    };
    // << autocomplete-remote-completion-mode
    // << autocomplete-remote-full

    private ArrayList<TokenModel> getTokenModelObjects(JSONArray json) {
        ArrayList<TokenModel> feedData = new ArrayList<TokenModel>();
        JSONObject current = new JSONObject();
        for (int i = 0; i < json.length(); i++) {
            String airport = "";

            try {
                current = json.getJSONObject(i);
                String fullname = (String) current.get("FIELD2");
                String abr = (String) current.get("FIELD5");
                airport = fullname + "," + abr;
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            TokenModel token = new TokenModel(airport, null);
            feedData.add(token);
        }

        return feedData;
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
