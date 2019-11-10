package com.example.android.bookexchange;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerViewDecorator extends RecyclerView.ItemDecoration {

    private final int marginRight;

    public recyclerViewDecorator(int marginRight) {
        this.marginRight = marginRight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.right = marginRight;
    }
}
