package com.tech.chatapp.global;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.widget.LinearLayout;

import com.tech.chatapp.R;


/**
 * Created by arbaz on 6/2/17.
 */

public class AppDialog {


    public static ProgressDialog progressDialog;
    private static Dialog d;

    //with one button
    public static void showAlertDialog(Context _context, String _title,
                                       String _message, String _positiveText,
                                       DialogInterface.OnClickListener _onPositiveClick) {
        AlertDialog dialog = new AlertDialog.Builder(_context).create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        if (_title != null && _title.length() > 0) {
            dialog.setTitle(_title);
        } else {
            dialog.setTitle(_context.getString(R.string.app_name));
        }
        dialog.setMessage(_message);
        dialog.setButton(Dialog.BUTTON_POSITIVE, _positiveText,
                _onPositiveClick);
        dialog.setCancelable(false);
        dialog.show();
    }

    //with two buttons
    public static void showAlertDialog(Context _context, String _title,
                                       String _message, String _positiveText, String _negativeText,
                                       DialogInterface.OnClickListener _onPositiveClick,
                                       DialogInterface.OnClickListener _onNegativeClick) {
        AlertDialog dialog = new AlertDialog.Builder(_context).create();
        if (_title != null && _title.length() > 0) {
            dialog.setTitle(_title);
        } else {
            dialog.setTitle(_context.getString(R.string.app_name));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setMessage(_message);
        dialog.setButton(Dialog.BUTTON_POSITIVE, _positiveText,
                _onPositiveClick);
        dialog.setButton(Dialog.BUTTON_NEGATIVE, _negativeText,
                _onNegativeClick);
        dialog.setCancelable(false);
        dialog.show();
    }

    //with three buttons
    public static void showAlertDialog(Context _context, String _title,
                                       String _message, String _positiveText, String _negativeText, String _neutralText,
                                       DialogInterface.OnClickListener _onPositiveClick,
                                       DialogInterface.OnClickListener _onNegativeClick, Dialog.OnClickListener _onNeutralClick) {
        AlertDialog dialog = new AlertDialog.Builder(_context).create();
        if (_title != null && _title.length() > 0) {
            dialog.setTitle(_title);
        } else {
            dialog.setTitle(_context.getString(R.string.app_name));
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setMessage(_message);
        dialog.setButton(Dialog.BUTTON_POSITIVE, _positiveText,
                _onPositiveClick);
        dialog.setButton(Dialog.BUTTON_NEGATIVE, _negativeText,
                _onNegativeClick);
        dialog.setButton(Dialog.BUTTON_NEUTRAL, _neutralText,
                _onNeutralClick);
        dialog.setCancelable(false);
        dialog.show();
    }




    public static void noNetworkDialog(Context _context,
                                       DialogInterface.OnClickListener _onClick) {
        AlertDialog dialog = new AlertDialog.Builder(_context).create();

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setMessage(_context.getString(R.string.txt_no_network));
        dialog.setButton(Dialog.BUTTON_POSITIVE,
                _context.getString(R.string.txt_ok), _onClick);
        dialog.setCancelable(false);
        dialog.show();
    }
/*

	public static void showProgressDialog(Context context, String msg) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(msg);
		progressDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setContentView(R.layout.app_progress_dialog);
		progressDialog.show();
	}

	public static void dismissProgressDialog() {
		progressDialog.dismiss();
	}
*/


    public static void showProgressDialog(Context context) {

        d = new Dialog(context, R.style.ProgressDialogAnimation);
        d.getWindow().getAttributes().windowAnimations = R.style.ProgressDialogAnimation;
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);
        d.setContentView(R.layout.app_progress_dialog);
        d.show();
    }

    public static void dismissProgressDialog() {
        if (d != null && d.isShowing()) {
            d.dismiss();


        }
    }
    //Application Exit Dialog
    public static void exitByBackKey(final Context _context) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(_context);
        alertDialog.setTitle(R.string.app_name);
        alertDialog.setMessage(R.string.app_exit_message);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) _context).finish();
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();


    }

}
