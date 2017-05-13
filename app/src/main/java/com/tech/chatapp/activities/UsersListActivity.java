package com.tech.chatapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tech.chatapp.R;
import com.tech.chatapp.adapter.UserListAdapter;
import com.tech.chatapp.global.AppDialog;
import com.tech.chatapp.model.UserDetails;
import com.tech.chatapp.model.UserListMain;
import com.tech.chatapp.util.AnimUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


public class UsersListActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;
    TextView tvUserError;
    RecyclerView rvUserList;
    UserListAdapter userListAdapter;
    ArrayList<UserListMain> userListMainArrayList;
    UserListMain userListMain;

    private Paint p = new Paint();
    View view;

    public static final int REQUEST_CHAT_LIST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        //Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbIvBack = (ImageView) toolbar.findViewById(R.id.tbIvBack);
        tbTitle.setVisibility(View.VISIBLE);
        tbTitle.setText(getString(R.string.chat_with_str));
        tbIvBack.setVisibility(View.GONE);
        tbIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*finish();*/
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvUserError = (TextView) findViewById(R.id.tvUserError);
        rvUserList = (RecyclerView) findViewById(R.id.rvUserList);
        rvUserList.setLayoutManager(new LinearLayoutManager(this));
        initSwipe();
        /*rvUserList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                *//*userListMain = userListMainArrayList.get(position);

                UserDetails.chatWith = userListMainArrayList.get(position).getUserName();
                Intent iChatRoom = new Intent(UsersListActivity.this, ChatRoomActivity.class);
                iChatRoom.putExtra("ChatWith", userListMain);

                startActivity(iChatRoom);*//*
                Toast.makeText(UsersListActivity.this, "Touch Call", Toast.LENGTH_SHORT).show();
            }
        }));*/

        /*rvUserList.addOnItemTouchListener(new RecyclerTouchListener(this,
                rvUserList, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(UsersListActivity.this, "Single Click on position        :" + position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(UsersListActivity.this, "Long press on position :" + position,
                        Toast.LENGTH_LONG).show();
                int isTouch = 1;
                userListAdapter = new UserListAdapter(userListMainArrayList, UsersListActivity.this, isTouch);
                rvUserList.setAdapter(userListAdapter);
                rvUserList.getViewTreeObserver().addOnPreDrawListener(
                        new ViewTreeObserver.OnPreDrawListener() {

                            @Override
                            public boolean onPreDraw() {
                                rvUserList.getViewTreeObserver().removeOnPreDrawListener(this);

                                for (int i = 0; i < rvUserList.getChildCount(); i++) {
                                    View v = rvUserList.getChildAt(i);
                                    v.setAlpha(0.0f);
                                    v.animate().alpha(1.0f)
                                            .setDuration(300)
                                            .setStartDelay(i * 50)
                                            .start();
                                }

                                return true;
                            }
                        });
            }
        }));*/
        getUsersList();


    }

    public void getUsersList() {
        //get User List Here
        AppDialog.showProgressDialog(this);
        final String url = "https://fir-testapp-211a5.firebaseio.com/users.json";
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    userListMainArrayList = new ArrayList<UserListMain>();
                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String responseString) {
                            AppDialog.dismissProgressDialog();
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);
                                Iterator iterator = jsonObject.keys();
                                while (iterator.hasNext()) {
                                    userListMain = new UserListMain(iterator.next().toString());
                                    //add user except login user
                                    if (!userListMain.getUserName().equals(UserDetails.username)) {
                                        userListMainArrayList.add(userListMain);
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            rvUserList.setVisibility(View.VISIBLE);
                            tvUserError.setVisibility(View.GONE);
                            Collections.sort(userListMainArrayList, new Comparator<UserListMain>() {
                                @Override
                                public int compare(UserListMain s1, UserListMain s2) {
                                    return s1.getUserName().compareToIgnoreCase(s2.getUserName());
                                }
                            });
                            userListAdapter = new UserListAdapter(userListMainArrayList, UsersListActivity.this);
                            rvUserList.setAdapter(userListAdapter);
                            rvUserList.getViewTreeObserver().addOnPreDrawListener(
                                    new ViewTreeObserver.OnPreDrawListener() {

                                        @Override
                                        public boolean onPreDraw() {
                                            rvUserList.getViewTreeObserver().removeOnPreDrawListener(this);

                                            for (int i = 0; i < rvUserList.getChildCount(); i++) {
                                                View v = rvUserList.getChildAt(i);
                                                v.setAlpha(0.0f);
                                                v.animate().alpha(1.0f)
                                                        .setDuration(300)
                                                        .setStartDelay(i * 50)
                                                        .start();
                                            }

                                            return true;
                                        }
                                    });

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            AppDialog.dismissProgressDialog();
                            rvUserList.setVisibility(View.GONE);
                            tvUserError.setVisibility(View.VISIBLE);
                            AppDialog.showAlertDialog(UsersListActivity.this, null, getResources().getString(R.string.something_wrong), getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                    RequestQueue rQueue = Volley.newRequestQueue(UsersListActivity.this);
                    rQueue.add(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //End User List Here
    }

    /*Link
    https://www.learn2crack.com/2016/02/custom-swipe-recyclerview.html*/
    private void initSwipe() {//ItemTouchHelper.LEFT |
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                 return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.RIGHT) {
//                    userListAdapter.updateLines();
//                    Toast.makeText(UsersListActivity.this, "Right Side", Toast.LENGTH_SHORT).show();

                    userListMain = userListMainArrayList.get(position);
                    UserDetails.chatWith = userListMainArrayList.get(position).getUserName();
                    Intent iChatRoom = new Intent(UsersListActivity.this, ChatRoomActivity.class);
                    iChatRoom.putExtra("ChatWith", userListMain);
                    startActivityForResult(iChatRoom, REQUEST_CHAT_LIST);
                    userListMainArrayList.remove(position);
                    userListAdapter.notifyDataSetChanged();
                    AnimUtils.activityexitAnim(UsersListActivity.this);


                } /*else {
                    Toast.makeText(UsersListActivity.this, "Left Side", Toast.LENGTH_SHORT).show();

                }*/
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvUserList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == REQUEST_CHAT_LIST && resultCode == RESULT_OK) {
            boolean b = data.getBooleanExtra("ListUpdate", true);
            if (b) {
                getUsersList();
            }

        }
    }


}
