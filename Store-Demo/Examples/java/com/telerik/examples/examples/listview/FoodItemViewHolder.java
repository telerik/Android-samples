package com.telerik.examples.examples.listview;

import android.graphics.Bitmap;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.widget.list.ListViewHolder;

class FoodItemViewHolder extends ListViewHolder implements View.OnLayoutChangeListener, PropertyChangeListener {

    public ImageView itemImage;
    public TextView itemTitle;
    public TextView itemAuthor;
    public PhotoItemContainer entity;

    public FoodItemViewHolder(View itemView) {
        super(itemView);

        this.itemImage = (ImageView) itemView.findViewById(R.id.imageView);
        this.itemTitle = (TextView) itemView.findViewById(R.id.titleView);
        this.itemAuthor = (TextView) itemView.findViewById(R.id.authorView);

        View recipeInfoBackground = itemView.findViewById(R.id.recipeInfo);
        if (recipeInfoBackground != null) {
            recipeInfoBackground.setBackgroundDrawable(this.createInfoBackground());
        }
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

    public void bind(PhotoItemContainer entity) {
        if(this.entity != null) {
            this.entity.setPropertyChangeListener(null);
        }
        this.entity = entity;

        if(this.entity != null) {
            this.entity.setPropertyChangeListener(this);
        }

        this.itemAuthor.setText(entity.getPhoto().getOwner().getUsername());
        this.itemTitle.setText(entity.getPhoto().getTitle());

        if(entity.getBitmap() == null) {
            this.showProgressBar();
            this.itemImage.addOnLayoutChangeListener(this);
            this.itemImage.setImageBitmap(null);
        } else {
            this.hideProgressBar();
            itemImage.setImageBitmap(entity.getBitmap());
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        itemImage.removeOnLayoutChangeListener(this);
        this.entity.downloadPhoto(right - left);
    }

    private void hideProgressBar(){
        ProgressBar pb = (ProgressBar)itemView.findViewById(R.id.progressBar);
        pb.setVisibility(View.GONE);
    }

    private void showProgressBar(){
        ProgressBar pb = (ProgressBar)itemView.findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPropertyChanged(String propertyName, Object newValue, Object oldValue) {
        if(propertyName.equals("Bitmap")) {
            this.hideProgressBar();
            itemImage.setVisibility(View.VISIBLE);
            this.itemImage.setImageBitmap((Bitmap)newValue);
        }
    }
}
