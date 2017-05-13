package com.tech.chatapp.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech.chatapp.R;
import com.tech.chatapp.model.ChatListMain;

import java.util.ArrayList;

/**
 * Created by arbaz on 8/5/17.
 */

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<ChatListMain> chatListMainArrayList;
    ChatListMain chatListMain;


    public ChatListAdapter(Context context, ArrayList<ChatListMain> chatListMainArrayList) {
        this.context = context;
        this.chatListMainArrayList = chatListMainArrayList;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_right_row, parent, false);
                return new RightChatViewHolder(view);

            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_left_row, parent, false);
                return new LeftChatViewHolder(view);

        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        chatListMain = chatListMainArrayList.get(position);
        try {

            String getTime = chatListMain.getTime();
            String[] msgTime = getTime.split("_");

            if (chatListMain.getSenderType() == 1) {
                ((RightChatViewHolder) holder).tvRightMsgTime.setText(msgTime[1]);
                ((RightChatViewHolder) holder).tvRightMsg.setText(chatListMain.getMessage());

            } else if (chatListMain.getSenderType() == 2) {
                ((LeftChatViewHolder) holder).tvLeftMsgTime.setText(msgTime[1]);
                ((LeftChatViewHolder) holder).tvLeftMsg.setText(chatListMain.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemViewType(int position) {
        chatListMain = chatListMainArrayList.get(position);
        if (chatListMain.getSenderType() == 1) {
            return 1;//Right Side
        } else if (chatListMain.getSenderType() == 2) {
            return 2;//Left Side
        } else {
            return 3;
        }

    }

    @Override
    public int getItemCount() {
        return chatListMainArrayList.size();
    }


    //For Right Chat
    private class RightChatViewHolder extends RecyclerView.ViewHolder {
        TextView tvRightMsg, tvRightMsgTime;

        public RightChatViewHolder(View view) {
            super(view);
            tvRightMsgTime = (TextView) view.findViewById(R.id.tvRightMsgTime);
            tvRightMsg = (TextView) view.findViewById(R.id.tvRightMsg);

        }
    }

    //For Left Chat
    private class LeftChatViewHolder extends RecyclerView.ViewHolder {
        TextView tvLeftMsg, tvLeftMsgTime;

        public LeftChatViewHolder(View view) {
            super(view);
            tvLeftMsgTime = (TextView) view.findViewById(R.id.tvLeftMsgTime);
            tvLeftMsg = (TextView) view.findViewById(R.id.tvLeftMsg);
        }
    }

    public void updateList(ArrayList<ChatListMain> updateList) {
        chatListMainArrayList = updateList;
        notifyDataSetChanged();
    }


}
