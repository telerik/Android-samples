package fragments.sidedrawer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.telerik.android.primitives.widget.sidedrawer.DrawerChangeListener;
import com.telerik.android.primitives.widget.sidedrawer.DrawerLocation;
import com.telerik.android.primitives.widget.sidedrawer.DrawerTransition;
import com.telerik.android.primitives.widget.sidedrawer.RadSideDrawer;
import com.telerik.android.primitives.widget.sidedrawer.SideDrawerToggle;
import com.telerik.android.primitives.widget.sidedrawer.transitions.FallDownTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.PushTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.RevealTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.ReverseSlideOutTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.ScaleDownPusherTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.ScaleUpTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.SlideAlongTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.SlideInOnTopTransition;
import com.telerik.android.sdk.R;

import activities.ExampleFragment;

public class SideDrawerFeaturesFragment extends Fragment implements ExampleFragment, DrawerChangeListener, View.OnLayoutChangeListener  {
    private RadSideDrawer drawer;
    private Spinner transitionsSpinner;
    DrawerTransition[] transitions;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_side_drawer_features, null);

        drawer = (RadSideDrawer)rootView.findViewById(R.id.sideDrawer);
        drawer.setMainContent(this.loadMainContent(inflater));
        drawer.setDrawerContent(this.loadDrawerContent(inflater));
        drawer.addOnLayoutChangeListener(this);
        drawer.addChangeListener(this);

        return rootView;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        this.drawer.removeOnLayoutChangeListener(this);
        this.addTransitionsSpinnerListener();
    }

    private View loadMainContent(LayoutInflater inflater) {
        View result = inflater.inflate(R.layout.side_drawer_features_main_content, null);

        Spinner locationSpinner = (Spinner)result.findViewById(R.id.drawerLocationSpinner);
        ArrayAdapter<DrawerLocation> locationAdapter = new ArrayAdapter<DrawerLocation>(this.getActivity(), android.R.layout.simple_list_item_1, DrawerLocation.values());
        locationSpinner.setAdapter(locationAdapter);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drawer.setDrawerLocation(DrawerLocation.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.transitionsSpinner = (Spinner)result.findViewById(R.id.drawerTransitionsSpinner);
        transitions = new DrawerTransition[]
                {
                        new SlideInOnTopTransition(),
                        new FallDownTransition(),
                        new PushTransition(),
                        new RevealTransition(),
                        new ReverseSlideOutTransition(),
                        new ScaleDownPusherTransition(),
                        new ScaleUpTransition(),
                        new SlideAlongTransition(),
                };
        ArrayAdapter<DrawerTransition> transitionsAdapter = new ArrayAdapter<DrawerTransition>(this.getActivity(), android.R.layout.simple_list_item_1, transitions);
        transitionsSpinner.setAdapter(transitionsAdapter);

        CheckBox closeOnBackPress = (CheckBox)result.findViewById(R.id.drawerCloseOnBackPress);
        closeOnBackPress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                drawer.setCloseOnBackPress(isChecked);
            }
        });

        Toolbar toolbar = (Toolbar)result.findViewById(R.id.drawerToolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        AppCompatActivity actionBarActivity = (AppCompatActivity) this.getActivity();
        ActionBar supportActionBar = actionBarActivity.getSupportActionBar();
        if(supportActionBar != null) {
            String title = (String) supportActionBar.getTitle();
            toolbar.setTitle(title);
            supportActionBar.hide();
        }

        SideDrawerToggle toggle = new SideDrawerToggle(drawer, toolbar);

        return result;
    }

    private View loadDrawerContent(LayoutInflater inflater) {
        View result = inflater.inflate(R.layout.side_drawer_features_drawer_content, null);

        ToggleButton tapOutside = (ToggleButton)result.findViewById(R.id.drawerTapOutsideButton);
        tapOutside.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                drawer.setTapOutsideToClose(isChecked);
            }
        });

        ToggleButton lockButton = (ToggleButton)result.findViewById(R.id.lockDrawerButton);
        lockButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                drawer.setIsLocked(isChecked);
            }
        });

        return result;
    }

    private void addTransitionsSpinnerListener() {
        this.transitionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drawer.setDrawerTransition(transitions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public String title() {
        return "Features";
    }

    @Override
    public boolean onDrawerOpening(RadSideDrawer radSideDrawer) {

        Log.d("RadSideDrawer: ", "opening");

        // If this method returns true the opening operation will be cancelled and the drawer will remain closed.
        return false;
    }

    @Override
    public void onDrawerOpened(RadSideDrawer radSideDrawer) {
        Log.d("RadSideDrawer: ", "opened");
    }

    @Override
    public boolean onDrawerClosing(RadSideDrawer radSideDrawer) {
        Log.d("RadSideDrawer: ", "closing");

        // If this method returns true the closing operation will be cancelled and the drawer will remain open.
        return false;
    }

    @Override
    public void onDrawerClosed(RadSideDrawer radSideDrawer) {
        Log.d("RadSideDrawer: ", "closed");
    }
}
