package com.telerik.examples.examples.sidedrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.examples.listview.PhotoItemContainer;
import com.telerik.examples.examples.listview.PropertyChangeListener;

public class DrawerRecipeListItem extends FrameLayout implements PropertyChangeListener {
    private ImageView imageView;
    private ProgressBar progressBar;

    public DrawerRecipeListItem(Context context, PhotoItemContainer photoContainer, int desiredWidth) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.recipe_item_layout, this);
        progressBar = Util.getLayoutPart(this, R.id.progressBar, ProgressBar.class);
        int color = context.getResources().getColor(R.color.listViewToolbarColor);
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        imageView = Util.getLayoutPart(this, R.id.imageView, ImageView.class);
        TextView titleView = Util.getLayoutPart(this, R.id.titleView, TextView.class);
        TextView authorView = Util.getLayoutPart(this, R.id.authorView, TextView.class);
        View recipeInfo = this.findViewById(R.id.recipeInfo);
        recipeInfo.setBackgroundDrawable(this.createInfoBackground());
        if(photoContainer.getBitmap() == null) {
            photoContainer.setPropertyChangeListener(this);
            photoContainer.downloadPhoto(desiredWidth);
        } else {
            imageView.setImageBitmap(photoContainer.getBitmap());
            progressBar.setVisibility(GONE);
        }

        titleView.setText(photoContainer.getPhoto().getTitle());
        authorView.setText(photoContainer.getPhoto().getOwner().getUsername());
    }

    private Drawable createInfoBackground() {
        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(width / 2.0f, 0, width / 2.0f, height,
                        new int[] { 0x00ffffff, 0xccffffff, 0xccffffff },
                        new float[] { 0, 0.3f, 1 }, Shader.TileMode.REPEAT );
            }
        };

        PaintDrawable background = new PaintDrawable();
        background.setShape(new RectShape());
        background.setShaderFactory(shaderFactory);

        return background;
    }

    @Override
    public void onPropertyChanged(String propertyName, Object newValue, Object oldValue) {
        if(propertyName.equals("Bitmap")) {
            imageView.setImageBitmap((Bitmap)newValue);
            progressBar.setVisibility(GONE);
        }
    }
}
