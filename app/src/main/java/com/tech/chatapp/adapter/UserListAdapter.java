package com.tech.chatapp.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tech.chatapp.R;
import com.tech.chatapp.model.UserListMain;

import java.util.ArrayList;

/**
 * Created by arbaz on 6/5/17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    ArrayList<UserListMain> userNameListMainArrayList;
    UserListMain userNameListMain;
    Context context;
    UserListAdapter.ViewHolder holder1;
    int isTouch;


    public UserListAdapter(ArrayList<UserListMain> userNameListMainArrayList, Context context) {
        this.userNameListMainArrayList = userNameListMainArrayList;
        this.context = context;
    }

    //for changing view color
    public UserListAdapter(ArrayList<UserListMain> userNameListMainArrayList, Context context, int isTouch) {
        this.userNameListMainArrayList = userNameListMainArrayList;
        this.context = context;
        this.isTouch = isTouch;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        userNameListMain = userNameListMainArrayList.get(position);
        holder1 = holder;
        try {

//            if (isTouch == 1) {
//                holder.viewLeft.setBackgroundResource(R.color.colorWhite);
//                holder.viewRight.setBackgroundResource(R.color.colorWhite);
//                holder.tvUserName.setText(userNameListMain.getUserName());
//
//            } else {
                if (position == userNameListMainArrayList.size() - 1) {
                    holder.llLines.setVisibility(View.GONE);
                    holder.tvUserName.setText(userNameListMain.getUserName());
                } else {
                    holder.tvUserName.setText(userNameListMain.getUserName());
                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return userNameListMainArrayList.size();
    }

    public void removeItem(int position) {
        userNameListMainArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, userNameListMainArrayList.size());

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName;
        public LinearLayout llLines;
        View view;
        View viewLeft, viewRight;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            llLines = (LinearLayout) view.findViewById(R.id.llLines);
            viewLeft = (View) view.findViewById(R.id.viewLeft);
            viewRight = (View) view.findViewById(R.id.viewRight);

        }
    }

}
