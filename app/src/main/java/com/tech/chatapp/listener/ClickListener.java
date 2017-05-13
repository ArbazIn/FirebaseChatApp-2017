package com.tech.chatapp.listener;

import android.view.View;

/**
 * Created by arbaz on 13/5/17.
 */

public interface ClickListener {
    public void onClick(View view, int position);

    public void onLongClick(View view, int position);
}