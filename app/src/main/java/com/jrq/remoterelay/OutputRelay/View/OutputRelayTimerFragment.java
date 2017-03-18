package com.jrq.remoterelay.OutputRelay.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.Slider;
import com.jrq.remoterelay.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jrq on 2016-09-18.
 */

public class OutputRelayTimerFragment extends DialogFragment {

    private static OutputRelayTimerFragment controlTimerFragment;
    private static ControlTimerFragmentListener mControlTimerFragmentListener;
    private static int mPosition;

    @BindView(R.id.buttonControlFragmentSave)
    ButtonRectangle acceptButton;

    @BindView(R.id.buttonControlFragmentCancel)
    ButtonRectangle cancelButton;

    @BindView(R.id.textViewSlider)
    EditText valueSlider;

    @BindView(R.id.slider)
    Slider slider;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static OutputRelayTimerFragment newInstance(ControlTimerFragmentListener controlTimerFragmentListener, int position) {
        try {
            mControlTimerFragmentListener = controlTimerFragmentListener;
            controlTimerFragment = new OutputRelayTimerFragment();
            mPosition = position;
            return controlTimerFragment;
        } catch (Exception ex) {
        }
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.output_relay_timer_dialogfragment, null, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        ButterKnife.bind(this, view);

        valueSlider.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int i = Integer.parseInt(s.toString());
                    if(i > 240) {
                        i = 240;
                    }
                    slider.setValue(i);
                } catch(Exception ex) {
                }
            }
        });

        slider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                valueSlider.setText(Integer.toString(value));
            }
        });
        return view;
    }

    @Nullable
    @OnClick(R.id.buttonControlFragmentSave)
    public void acceptOnBluetooth() {
        mControlTimerFragmentListener.onSetTimeDuring(slider.getValue(), mPosition);
        this.dismiss();
    }

    @Nullable
    @OnClick(R.id.buttonControlFragmentCancel)
    public void cancelOnBluetooth() {
        this.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface ControlTimerFragmentListener {
        public void onSetTimeDuring(int time, int position);
    }
}
