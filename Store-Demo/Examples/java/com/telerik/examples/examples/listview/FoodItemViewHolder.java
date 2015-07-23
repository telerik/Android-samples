package com.telerik.examples.examples.listview;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.telerik.examples.R;
import com.telerik.widget.list.ListViewHolder;

class FoodItemViewHolder extends ListViewHolder {

    public SimpleDraweeView itemImage;
    public TextView itemTitle;
    public TextView itemAuthor;
    public PhotoItemData entity;

    public FoodItemViewHolder(View itemView) {
        super(itemView);

        this.itemImage = (SimpleDraweeView) itemView.findViewById(R.id.imageView);
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
                        new int[]{0x00ffffff, 0xccffffff, 0xccffffff},
                        new float[]{0, 0.3f, 1}, Shader.TileMode.REPEAT);
            }
        };

        PaintDrawable background = new PaintDrawable();
        background.setShape(new RectShape());
        background.setShaderFactory(shaderFactory);

        return background;
    }

    public void bind(PhotoItemData entity) {
        this.entity = entity;

        this.itemAuthor.setText(entity.getPhoto().getOwner().getUsername());
        this.itemTitle.setText(entity.getPhoto().getTitle());

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(FlickrHelper.getPhotoDownloadUrl(entity.getPhoto()))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(itemImage.getController())
                .build();
        itemImage.setController(controller);
    }
}
