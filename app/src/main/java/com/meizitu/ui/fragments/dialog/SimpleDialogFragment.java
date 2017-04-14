package com.meizitu.ui.fragments.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;

import com.meizitu.R;

public class SimpleDialogFragment extends DialogFragment {
    private static final String TITLEKEY = "title";
    private static final String MESSAGEKEY = "message";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static SimpleDialogFragment newInstance(String title,String message) {
        SimpleDialogFragment addClassifyDialogFragment = new SimpleDialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLEKEY, title);
        args.putString(MESSAGEKEY, message);
        addClassifyDialogFragment.setArguments(args);
        return addClassifyDialogFragment;
    }



    private PositiveButtonOnClickListener buttonOnClickListener = null;

    public SimpleDialogFragment setPositiveButtonOnClickListener(PositiveButtonOnClickListener buttonOnClickListener) {
        this.buttonOnClickListener = buttonOnClickListener;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        CharSequence title = getArguments().getString(TITLEKEY);
        CharSequence message = getArguments().getString(MESSAGEKEY);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.custom_dialog);
        if(!TextUtils.isEmpty(title)){
            builder.setTitle(title);//
        }
        if(!TextUtils.isEmpty(message)){
            builder.setMessage(message);
        }
        builder.setPositiveButton(R.string.confirm, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (buttonOnClickListener != null) {
                    buttonOnClickListener.positiveOnClick();
                }
            }
        }).setNegativeButton(R.string.cancel, null);
        return builder.create();
    }

    public interface PositiveButtonOnClickListener {
        void positiveOnClick();
    }
}
