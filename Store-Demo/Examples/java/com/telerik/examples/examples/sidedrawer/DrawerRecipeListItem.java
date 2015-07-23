package com.telerik.examples.examples.sidedrawer;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.examples.listview.FlickrHelper;
import com.telerik.examples.examples.listview.PhotoItemData;

public class DrawerRecipeListItem extends FrameLayout {
    private SimpleDraweeView imageView;

    public DrawerRecipeListItem(Context context, PhotoItemData photoContainer, int desiredWidth) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.recipe_item_layout, this);
        imageView = Util.getLayoutPart(this, R.id.imageView, SimpleDraweeView.class);
        TextView titleView = Util.getLayoutPart(this, R.id.titleView, TextView.class);
        TextView authorView = Util.getLayoutPart(this, R.id.authorView, TextView.class);
        View recipeInfo = this.findViewById(R.id.recipeInfo);
        recipeInfo.setBackgroundDrawable(this.createInfoBackground());

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(FlickrHelper.getPhotoDownloadUrl(photoContainer.getPhoto()))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(imageView.getController())
                .build();
        imageView.setController(controller);

        titleView.setText(photoContainer.getPhoto().getTitle());
        authorView.setText(photoContainer.getPhoto().getOwner().getUsername());
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
}
