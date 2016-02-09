package com.telerik.examples.examples.sidedrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.telerik.android.primitives.widget.sidedrawer.DrawerChangeListener;
import com.telerik.android.primitives.widget.sidedrawer.DrawerLocation;
import com.telerik.android.primitives.widget.sidedrawer.DrawerTransition;
import com.telerik.android.primitives.widget.sidedrawer.RadSideDrawer;
import com.telerik.android.primitives.widget.sidedrawer.transitions.FallDownTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.PushTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.RevealTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.ReverseSlideOutTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.ScaleDownPusherTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.ScaleUpTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.SlideAlongTransition;
import com.telerik.android.primitives.widget.sidedrawer.transitions.SlideInOnTopTransition;
import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;

import java.util.HashMap;

public class SettingsFragment extends ExampleFragmentBase implements RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener, View.OnLayoutChangeListener {
    private RadSideDrawer sideDrawer;
    private ToggleButton currentPosition;
    private HashMap<Integer, DrawerTransition> transitions = new HashMap<Integer, DrawerTransition>();
    private int drawerSize;

    public SettingsFragment() {
        transitions.put(R.id.revealTransitionButton, new RevealTransition());
        transitions.put(R.id.slideInOnTopTransitionButton, new SlideInOnTopTransition());
        transitions.put(R.id.pushTransitionButton, new PushTransition());
        transitions.put(R.id.slideAlongTransitionButton, new SlideAlongTransition());
        transitions.put(R.id.fallDownTransitionButton, new FallDownTransition());
        transitions.put(R.id.reverseSlideOutTransitionButton, new ReverseSlideOutTransition());
        transitions.put(R.id.scaleUpTransitionButton, new ScaleUpTransition());
        transitions.put(R.id.scaleDownTransitionButton, new ScaleDownPusherTransition());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sideDrawer = (RadSideDrawer)inflater.inflate(R.layout.example_side_drawer_settings_drawer, null);
        sideDrawer.setDrawerTransition(new RevealTransition());
        sideDrawer.setMainContent(R.layout.example_side_drawer_settings);
        sideDrawer.setDrawerContent(R.layout.example_side_drawer_drawer);
        drawerSize = Math.round(this.getResources().getDimension(R.dimen.example_side_drawer_width));
        sideDrawer.setDrawerSize(drawerSize);
        sideDrawer.addOnLayoutChangeListener(this);

        ListView menuList = (ListView)sideDrawer.getDrawerContent().findViewById(R.id.drawerMenuList);
        menuList.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.example_side_drawer_drawer_list_item, new String[] { "Home", "About", "Settings", "User profile" }));

        int[] ids = new int[] { R.id.drawerPositionLeft, R.id.drawerPositionRight, R.id.drawerPositionTop, R.id.drawerPositionBottom };
        this.addListenerToButtons(ids);

        this.currentPosition = (ToggleButton)this.sideDrawer.getMainContent().findViewById(R.id.drawerPositionLeft);
        this.setDrawerTitle("Reveal");

        ImageButton button = (ImageButton)this.sideDrawer.getMainContent().findViewById(R.id.drawerSettingsToolbar).findViewById(R.id.hamburger);
        button.setOnClickListener(this);

        return sideDrawer;
    }

    private void addListenerToButtons(int[] ids) {
        for(Integer id : ids) {
            ToggleButton button = (ToggleButton)this.sideDrawer.getMainContent().findViewById(id);
            button.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        this.onTransitionChanged(checkedId);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(currentPosition != null) {
            currentPosition.setChecked(false);
            currentPosition.setEnabled(true);
        }

        if(isChecked) {
            this.onPositionChanged(buttonView.getId());
            currentPosition = (ToggleButton)buttonView;
            currentPosition.setEnabled(false);
        }
    }

    private void onPositionChanged(int positionId) {
        switch (positionId) {
            case R.id.drawerPositionLeft:
                this.sideDrawer.setDrawerLocation(DrawerLocation.LEFT);
                this.sideDrawer.setDrawerSize(drawerSize);
                break;
            case R.id.drawerPositionRight:
                this.sideDrawer.setDrawerLocation(DrawerLocation.RIGHT);
                this.sideDrawer.setDrawerSize(drawerSize);
                break;
            case R.id.drawerPositionTop:
                this.sideDrawer.setDrawerLocation(DrawerLocation.TOP);
                this.sideDrawer.setDrawerSize(0);
                break;
            case R.id.drawerPositionBottom:
                this.sideDrawer.setDrawerLocation(DrawerLocation.BOTTOM);
                this.sideDrawer.setDrawerSize(0);
                break;
        }
    }

    private void onTransitionChanged(int transitionId) {
        DrawerTransition transition = transitions.get(transitionId);
        this.sideDrawer.setDrawerTransition(transition);
        this.setDrawerTitle(transition.toString());
    }

    private void setDrawerTitle(String title) {
        ((TextView)this.sideDrawer.getDrawerContent().findViewById(R.id.transitionName)).setText(title);
    }

    @Override
    public void onClick(View v) {
        this.sideDrawer.setIsOpen(true);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        // This is a workaround for a system bug that causes the app to crash on orientation change for some reason.
        // Adding a listener after onCreateView solves the problem.
        sideDrawer.removeOnLayoutChangeListener(this);
        final RadioGroup transitionsPanel = (RadioGroup)sideDrawer.getMainContent().findViewById(R.id.transitionsPanel);
        transitionsPanel.setOnCheckedChangeListener(this);
    }
}
