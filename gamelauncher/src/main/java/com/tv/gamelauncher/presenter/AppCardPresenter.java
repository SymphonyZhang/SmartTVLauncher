/*
 * Copyright (c) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tv.gamelauncher.presenter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ViewGroup;

import com.tv.gamelauncher.R;
import com.tv.gamelauncher.util.Utils;
import com.tv.gamelauncher.model.App;


/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
public class AppCardPresenter extends Presenter {
    private static final String TAG = "AppCardPresenter";
    private int mSelectedBackgroundColor = -1;
    private int mDefaultBackgroundColor = -1;
    private Drawable mDefaultCardImage;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        //mDefaultBackgroundColor = parent.getResources().getColor(R.color.default_background, null);
        mDefaultBackgroundColor = ContextCompat.getColor(parent.getContext(), R.color.default_background);
        //mSelectedBackgroundColor = parent.getResources().getColor(R.color.selected_background, null);
        mSelectedBackgroundColor = ContextCompat.getColor(parent.getContext(),R.color.selected_background);
        mDefaultCardImage = parent.getResources().getDrawable(R.drawable.movie, null);

        ImageCardView cardView = new ImageCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };
        cardView.setCardType(ImageCardView.CARD_TYPE_FLAG_IMAGE_ONLY);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    private void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? mSelectedBackgroundColor : mDefaultBackgroundColor;

        // Both background colors should be set because the view's
        // background is temporarily visible during animations.
        view.setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        App app = (App) item;

        Log.d(TAG, "onBindViewHolder: --------------------------app = "+app.getAppName());
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setTitleText(app.getAppName());
        cardView.setContentText(app.getPackageName()+"  :  "+app.getIntentActivity());

        if (app.getIntentActivity() != null) {
            // Set card size from dimension resources.
            Resources res = cardView.getResources();
            int width = res.getDimensionPixelSize(R.dimen.card_width);
            int height = res.getDimensionPixelSize(R.dimen.card_height);
            cardView.setMainImageDimensions(width, height);
            cardView.setMainImage(Utils.getAppIcon(cardView.getContext(),app.getIntentActivity()));
//Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), Utils.getAppIcon(cardView.getContext(),app.getIntentActivity()), null,null))
           /* Glide.with(cardView.getContext())
                    .load(Uri.parse(MediaStore.Images.Media.insertImage(cardView.getContext().getContentResolver(), Utils.getAppIcon(cardView.getContext(),app.getIntentActivity()), null,null)))
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());*/
        }
    }
    /*private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            // do something with the bitmap
            // for demonstration purposes, let's just set it to an ImageView
            imageView1.setImageBitmap( bitmap );
        }
    };*/
    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;

        // Remove references to images so that the garbage collector can free up memory.
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}
