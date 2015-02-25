package fragments.sidedrawer;

import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.telerik.android.primitives.widget.sidedrawer.DrawerChangeListener;
import com.telerik.android.primitives.widget.sidedrawer.DrawerLocation;
import com.telerik.android.primitives.widget.sidedrawer.DrawerTransition;
import com.telerik.android.primitives.widget.sidedrawer.RadSideDrawer;
import com.telerik.android.primitives.widget.sidedrawer.transitions.EmptyTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.FallDownTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.OpenDoorTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.PushTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.RevealTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.ReverseSlideOutTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.RotateInTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.ScaleDownPusherTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.ScaleUpTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.SlideAlongTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.SlideInOnTopTransition;
import com.telerik.android.sdk.R;

import activities.ExampleFragment;

public class SideDrawerFeaturesFragment extends Fragment implements ExampleFragment, DrawerChangeListener {
    private RadSideDrawer drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_side_drawer_features, null);

        drawer = (RadSideDrawer)rootView.findViewById(R.id.sideDrawer);
        drawer.setMainContent(this.loadMainContent(inflater));
        drawer.setDrawerContent(this.loadDrawerContent(inflater));

        drawer.addChangeListener(this);

        return rootView;
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

        Spinner transitionsSpinner = (Spinner)result.findViewById(R.id.drawerTransitionsSpinner);
        final DrawerTransition[] transitions = new DrawerTransition[]
                {
                        new SlideInOnTopTransition(),
                        new FallDownTransition(),
                        new OpenDoorTransition(),
                        new PushTransition(),
                        new RevealTransition(),
                        new ReverseSlideOutTransition(),
                        new RotateInTransition(),
                        new ScaleDownPusherTransition(),
                        new ScaleUpTransition(),
                        new SlideAlongTransition(),
                        new EmptyTransition()
                };
        ArrayAdapter<DrawerTransition> transitionsAdapter = new ArrayAdapter<DrawerTransition>(this.getActivity(), android.R.layout.simple_list_item_1, transitions);
        transitionsSpinner.setAdapter(transitionsAdapter);
        transitionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drawer.setDrawerTransition(transitions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
