package com.jrq.remoterelaynewway.Settings.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Button;
import android.widget.Toast;

import com.jrq.remoterelaynewway.Database.Model.OutputRelay;
import com.jrq.remoterelaynewway.OutputRelay.MvpOutputRelayView;
import com.jrq.remoterelaynewway.OutputRelay.Presenter.OutputRelayControlPresenter;
import com.jrq.remoterelaynewway.R;
import com.jrq.remoterelaynewway.RemoteApplication;
import com.jrq.remoterelaynewway.Settings.MvpSettingsRelayView;
import com.jrq.remoterelaynewway.Settings.Presenter.SettingsRelayPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jrq on 2016-11-19.
 */

public class SettingsRelayFragment extends Fragment implements MvpSettingsRelayView {
    @BindView(R.id.connectHoldEditText)
    public EditText timeForConnectHold;

    @BindView(R.id.connectHold)
    public TextView connectHold;

    @BindView(R.id.connectHoldSave)
    public Button saveConnectHold;

    @BindView(R.id.connectHoldCancel)
    public Button cancelConnectHold;

    @Inject
    SettingsRelayPresenter settingsRelayPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((RemoteApplication) this.getActivity().getApplication())
                .getComponent()
                .inject(this);

        settingsRelayPresenter.attachView(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        settingsRelayPresenter.getTimeConnect();
    }


    @OnClick(R.id.connectHoldCancel)
    public void cancelConnectHold() {
        settingsRelayPresenter.getTimeConnect();
    }

    @OnClick(R.id.connectHoldSave)
    public void saveConnectHold() {
        int time = Integer.parseInt(timeForConnectHold.getText().toString());
        settingsRelayPresenter.saveTimeConnect(time);
        Toast.makeText(getContext(), "Wartość podtrzymania połączenia została zapisana",
                                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTimeContent(int time) {
        timeForConnectHold.setText(Integer.toString(time));
    }
}
