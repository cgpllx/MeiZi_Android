package com.meizitu.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.meizitu.R;

import cc.easyandroid.easyui.utils.EasyViewUtil;

public class ProgressDialogFragment extends DialogFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        // setStyle(DialogFragment.STYLE_NO_INPUT, 0);
    }

    public static final String HINTMESSAGE_KEY = "hintMessage";
    private TextView hintMessage_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ac_dialogprogress, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hintMessage_tv = EasyViewUtil.findViewById(view, R.id.hintMessage_tv);
        String hintMessage = getArguments().getString(HINTMESSAGE_KEY, "正在加载...");
        hintMessage_tv.setText(hintMessage);
    }

    public static ProgressDialogFragment newInstance(String hintMessage) {
        ProgressDialogFragment dialogFragment = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString(HINTMESSAGE_KEY, hintMessage);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        getActivity().onBackPressed();
                        return true;
                }
                return false;
            }
        });
        return dialog;
    }
}
