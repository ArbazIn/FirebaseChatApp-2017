package com.tech.chatapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.tech.chatapp.R;
import com.tech.chatapp.adapter.ChatListAdapter;
import com.tech.chatapp.global.Log;
import com.tech.chatapp.listener.RecyclerItemClickListener;
import com.tech.chatapp.model.ChatListMain;
import com.tech.chatapp.model.UserDetails;
import com.tech.chatapp.util.AnimUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ChatRoomActivity extends AppCompatActivity implements View.OnClickListener, RecognitionListener, TextToSpeech.OnInitListener {

    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;

    private android.support.v7.widget.RecyclerView rvChatList;
    private android.widget.EditText etMessage;
    private android.widget.ImageButton btnSend;

    Firebase reference1, reference2;

    ChatListAdapter chatListAdapter;
    ArrayList<ChatListMain> chatListMainArrayList = new ArrayList<>();
    ChatListMain chatListMain;
    private ImageButton btnAudio;
    LinearLayoutManager linearLayoutManager;
    //For Speech
    SpeechRecognizer speech = null;
    Intent recognizerIntent;

    //For Listening
    public static TextToSpeech tts;
    private ImageButton btnCamera;

    //For camera and gallery
    private static final int REQUEST_CAMERA = 3331;
    private static final int REQUEST_GALLERY = 3332;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //Firebase Init
        fireBaseInit();
        //For Listening
        tts = new TextToSpeech(this, this);
        //Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbIvBack = (ImageView) toolbar.findViewById(R.id.tbIvBack);
        tbTitle.setVisibility(View.VISIBLE);
        tbTitle.setText(UserDetails.chatWith);
        tbIvBack.setVisibility(View.VISIBLE);
        tbIvBack.setOnClickListener(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        this.btnSend = (ImageButton) findViewById(R.id.btnSend);
        this.btnAudio = (ImageButton) findViewById(R.id.btnAudio);
        this.etMessage = (EditText) findViewById(R.id.etMessage);
        this.btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        this.rvChatList = (RecyclerView) findViewById(R.id.rvChatList);

        //recycler view set LayoutManager
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rvChatList.setLayoutManager(linearLayoutManager);


        rvChatList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                chatListMain = chatListMainArrayList.get(position);
                speakOut(chatListMain.getMessage());
            }
        }));

        chatListAdapter = new ChatListAdapter(ChatRoomActivity.this, chatListMainArrayList);
        chatListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int chatCount = chatListAdapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (chatCount - 1))) {
                    rvChatList.smoothScrollToPosition(positionStart);
                }

            }
        });
        rvChatList.setAdapter(chatListAdapter);


        //EditText Text Change Listener
        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    btnSend.setVisibility(View.GONE);
                    btnAudio.setVisibility(View.VISIBLE);
                    Log.e("EditText" + "isAudio");
                } else {
                    btnSend.setVisibility(View.VISIBLE);
                    btnAudio.setVisibility(View.GONE);
                    Log.e("EditText" + "isSend");
                }

            }
        });

        btnSend.setOnClickListener(this);
        btnCamera.setOnClickListener(this);


        btnAudio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    speech.startListening(recognizerIntent);
                    Toast.makeText(ChatRoomActivity.this, "Audio Start", Toast.LENGTH_SHORT).show();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    speech.stopListening();
                    Toast.makeText(ChatRoomActivity.this, "Audio Stop", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    //Firebase Init
    public void fireBaseInit() {
        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://fir-testapp-211a5.firebaseio.com/messages/"
                + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://fir-testapp-211a5.firebaseio.com/messages/"
                + UserDetails.chatWith + "_" + UserDetails.username);
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                String messageTime = map.get("time").toString();

                if (userName.equals(UserDetails.username)) {
                    chatListMainArrayList.add(new ChatListMain(message, 1, messageTime));
                    chatListAdapter.notifyItemInserted(chatListMainArrayList.size() - 1);
                } else {
                    chatListMainArrayList.add(new ChatListMain(message, 2, messageTime));
                    chatListAdapter.notifyItemInserted(chatListMainArrayList.size() - 1);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                String messageText = etMessage.getText().toString();
                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    map.put("time", currentTimeStamp());
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    etMessage.setText("");
                }
                break;
            case R.id.tbIvBack:
                boolean b = true;
                Intent i = new Intent();
                i.putExtra("ListUpdate", b);
                setResult(RESULT_OK, i);
                finish();
                AnimUtils.activityenterAnim(ChatRoomActivity.this);
                break;
            case R.id.btnCamera:
                sendImage();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String SpeechMessage = matches.get(0);
        Map<String, String> map = new HashMap<String, String>();
        map.put("message", SpeechMessage);
        map.put("user", UserDetails.username);
        map.put("time", currentTimeStamp());
        reference1.push().setValue(map);
        reference2.push().setValue(map);
        etMessage.setText("");


    }

    //Start For Speech TO Text
    @Override
    public void onReadyForSpeech(Bundle params) {
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onRmsChanged(float rmsdB) {
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int error) {
        getErrorText(error);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
    }
    //End For Speech TO Text


    //Error Messages(Speech TO Text)
    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "Speak Again";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        speakOut(message);
        return message;
    }

    //For Listening (Speak Out)
    private static void speakOut(String txt) {
        String text = txt;
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    //For Listening inti
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS" + "This Language is not supported");
            }
        } else {
            Log.e("TTS" + "Initialization Failed!");
        }
    }

    public String currentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
        String currentDateandTime = sdf.format(new Date());
        Log.e("TS==" + currentDateandTime);
        return currentDateandTime;
    }

    @Override
    public void onBackPressed() {

        boolean b = true;
        Intent i = new Intent();
        i.putExtra("ListUpdate", b);
        setResult(RESULT_OK, i);
        finish();
        AnimUtils.activityenterAnim(ChatRoomActivity.this);
        super.onBackPressed();

    }

    /************************************** For Pick image for Cover*************************************/
    private void sendImage() {
        final CharSequence[] items = getResources().getStringArray(R.array.photo_array);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((getString(R.string.add_cover_image_text)));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.camera_photo))) {
                    cameraIntentCover();
                } else if (items[item].equals((getString(R.string.gallery_photo)))) {
                    galleryIntentCover();
                } else if (items[item].equals((getString(R.string.cancel_photo)))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    /**
     * **************************** Get Image from Camera*******************************************
     */
    private void cameraIntentCover() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    /**
     * ***********************************************Get Image From Gallery ***********************
     */
    private void galleryIntentCover() {

        if (Build.VERSION.SDK_INT < 19) {
            try {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_GALLERY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
