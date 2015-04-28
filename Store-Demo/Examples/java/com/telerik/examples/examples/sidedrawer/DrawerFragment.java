package com.telerik.examples.examples.sidedrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.googlecode.flickrjandroid.photos.PhotoList;
import com.telerik.android.primitives.widget.sidedrawer.DrawerChangeListener;
import com.telerik.android.primitives.widget.sidedrawer.RadSideDrawer;
import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;

public class DrawerFragment extends ExampleFragmentBase implements View.OnClickListener, AdapterView.OnItemClickListener, AsyncTaskNotificationListener<PhotoList> {
    private RadSideDrawer drawer;
    private View previousSelectedItem;
    private ListView mainList;
    private DrawerPicturesInfoDownloader downloader;
    private ListView menuList;
    private PhotoList photos;

    public DrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRetainInstance(true);

        downloader = new DrawerPicturesInfoDownloader(this);
        downloader.execute("Lunch");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if(downloader != null) {
            downloader.cancel(true);
        }
    }

    @Override
    public void unloadExample() {
        super.unloadExample();

        RecipeMenuAdapter adapter = (RecipeMenuAdapter)mainList.getAdapter();
        if(adapter != null) {
            adapter.cancelDownloads();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        drawer = (RadSideDrawer)inflater.inflate(R.layout.example_main_side_drawer_drawer_control, null);

        drawer.setMainContent(R.layout.example_main_side_drawer_main_content);
        drawer.setDrawerContent(R.layout.example_main_side_drawer_drawer_content);
        drawer.setDrawerSize(Math.round(this.getResources().getDimension(R.dimen.example_side_drawer_width)));
        TextView txtMyRecipes = (TextView)drawer.getMainContent().findViewById(R.id.txtMyRecipes);
        txtMyRecipes.setTypeface(Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/RobotoSlabRegular.ttf"));

        menuList = (ListView) drawer.getDrawerContent().findViewById(R.id.side_menu_list);
        menuList.setDividerHeight(0);
        menuList.setDivider(null);
        MenuAdapter menuAdapter = new MenuAdapter(this.getActivity(), new String[]{"Lunch", "Breakfast", "Mains", "Desserts", "Paleo", "Cocktails"});
        menuList.setAdapter(menuAdapter);
        menuList.setOnItemClickListener(this);

        mainList = (ListView) drawer.getMainContent().findViewById(R.id.main_recipe_list);
        mainList.setDivider(null);
        mainList.setDividerHeight(0);

        View button = drawer.getMainContent().findViewById(R.id.drawerSettingsToolbar).findViewById(R.id.hamburger);
        button.setOnClickListener(this);

        if (this.photos != null) {
            mainList.setAdapter(new RecipeMenuAdapter(this.getActivity(), photos));
        }

        return this.drawer;
    }

    @Override
    public void onClick(View v) {
        drawer.setIsOpen(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(previousSelectedItem != null) {
            previousSelectedItem.setSelected(false);
        }
        view.setSelected(true);
        previousSelectedItem = view;

        ((MenuAdapter)this.menuList.getAdapter()).notifyDataSetChanged();

        TextView textView = (TextView)view.findViewById(R.id.textView);

        drawer.setIsOpen(false);

        if(downloader != null) {
            downloader.cancel(true);
        }

        downloader = new DrawerPicturesInfoDownloader(this);
        downloader.execute(textView.getText().toString());
    }

    @Override
    public void onTaskFinished(AsyncTaskWithNotifications task, PhotoList result) {
        if (result == null){
            return;
        }
        this.photos = result;
        RecipeMenuAdapter recipeMenuAdapter = new RecipeMenuAdapter(this.getActivity(), result);

        if(this.mainList != null) {
            this.mainList.setAdapter(recipeMenuAdapter);
        }

        downloader = null;
    }

    @Override
    public void onTaskCancelled(AsyncTaskWithNotifications asyncTask) {

    }

    public class MenuAdapter extends BaseAdapter {
        private Context context;
        private String[] menuItems;
        public MenuAdapter(Context context, String[] menuItems) {
            super();

            this.menuItems = menuItems;
            this.context = context;
        }

        @Override
        public int getCount() {
            return menuItems.length;
        }

        @Override
        public Object getItem(int position) {
            return menuItems[position];
        }

        @Override
        public long getItemId(int position) {
            return menuItems[position].hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewGroup root = (ViewGroup)convertView;
            if(root == null) {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                root = (ViewGroup)inflater.inflate(R.layout.example_drawer_menu_list_item, null);
            }

            TextView textView = (TextView)root.findViewById(R.id.textView);
            textView.setText(menuItems[position]);

            if(textView.isSelected()) {
                textView.setTextColor(0xFFF66C38);
            } else {
                textView.setTextColor(Color.BLACK);
            }

            return root;
        }
    }

    @Override
    protected boolean usesInternet() {
        return true;
    }
}
