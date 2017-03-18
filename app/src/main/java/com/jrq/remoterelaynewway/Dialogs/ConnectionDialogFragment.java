package com.jrq.remoterelaynewway.Dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.jrq.remoterelaynewway.FinderDevices.Presenter.FinderDevicesPresenter;
import com.jrq.remoterelaynewway.R;

/**
 * Created by jrq on 2016-09-14.
 */

public class ConnectionDialogFragment extends DialogFragment {

    private ButtonRectangle connectButton;
    private ButtonRectangle cancelButton;
    private TextView communicat;

    private static int mPosition;
    private String tagDialog;
    private static ConnectionToDevice mContext;

    public static ConnectionDialogFragment newInstance(int position, ConnectionToDevice context) {
        try {
            mPosition = position;
            mContext = context;
            ConnectionDialogFragment connectionDialogFragment = new ConnectionDialogFragment();
            return connectionDialogFragment;
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setCancelable(false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(android.content.DialogInterface dialog, int keyCode, android.view.KeyEvent event) {
                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                    dismiss();
                    return true;
                } else return false;
            }
        });

        tagDialog = getTag();

        View view = inflater.inflate(R.layout.connection_dialog_fragment, null, false);

        communicat = (TextView) view.findViewById(R.id.communicationDialog);
        connectButton = (ButtonRectangle) view.findViewById(R.id.connectButton);
        cancelButton = (ButtonRectangle) view.findViewById(R.id.dismissButton);

        if (tagDialog.equals("disconnect")) {
            communicat.setText("Czy chcesz rozłączyć się od urządzenia?");
            connectButton.setText("ROZŁĄCZ");
        }

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectButton.getText().equals("ROZŁĄCZ")) {
                    mContext.disconnectOfDevice(mPosition);
                } else {
                    mContext.connectionToDevice(mPosition);
                }

                getDialog().dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    public interface ConnectionToDevice {
        public void connectionToDevice(int position);
        public void disconnectOfDevice(int position);
    }
}
