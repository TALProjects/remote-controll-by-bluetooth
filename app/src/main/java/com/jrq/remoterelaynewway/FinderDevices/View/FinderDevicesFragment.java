package com.jrq.remoterelaynewway.FinderDevices.View;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jrq.remoterelaynewway.Database.Model.ThermostatRelay;
import com.jrq.remoterelaynewway.Dialogs.ConnectionDialogFragment;
import com.jrq.remoterelaynewway.FinderDevices.DevicesProperties;
import com.jrq.remoterelaynewway.FinderDevices.MvpFinderView;
import com.jrq.remoterelaynewway.FinderDevices.PairedDevicesRecyclerAdapter;
import com.jrq.remoterelaynewway.FinderDevices.Presenter.FinderDevicesPresenter;
import com.jrq.remoterelaynewway.R;
import com.jrq.remoterelaynewway.RemoteApplication;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jrq on 2016-09-12.
 */
public class FinderDevicesFragment extends Fragment implements MvpFinderView {

    @BindView(R.id.device_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progressBarCircularIndeterminate)
    ProgressBarCircularIndeterminate progressBar;

    @BindView(R.id.progressText)
    TextView progressText;

    @BindView(R.id.searchBluetoothButton)
    ButtonRectangle searchBluetoothButton;

    @BindView(R.id.buttonFloatDevice)
    ButtonFloat searchBluetoothFloatButton;

    @Inject
    FinderDevicesPresenter finderDevicesPresenter;

    private static final int REQUEST_ENABLE_BT = 3;

    private static ArrayList<DevicesProperties> mainDeviceProperties;
    public static boolean isFinded = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.finder_devices_fragment, container, false);

        ButterKnife.bind(this, v);

        ((RemoteApplication) this.getActivity().getApplication())
                .getComponent()
                .inject(this);

        finderDevicesPresenter.attachView(this, getActivity());
        mainDeviceProperties = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isFinded == true && finderDevicesPresenter.checkConnection()) {
            SharedPreferences prefs = getContext().getSharedPreferences("userdetails", getContext().MODE_PRIVATE);
            Gson gson = new Gson();
            String json = prefs.getString("finderProperties", "");

            Type type = new TypeToken<ArrayList<DevicesProperties>>() {}.getType();
            ArrayList<DevicesProperties> tDeviceProperties;
            tDeviceProperties = gson.fromJson(json, type);
            mainDeviceProperties = tDeviceProperties;

            finderDevicesPresenter.setDevicesPropertiesFromSharedPreferences(mainDeviceProperties);

            if (tDeviceProperties != null) {
                refreshListView(new PairedDevicesRecyclerAdapter(tDeviceProperties, finderDevicesPresenter), tDeviceProperties);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mainDeviceProperties != null) {
            SharedPreferences prefs = getContext().getSharedPreferences("userdetails", getContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            try {
                Gson gson = new Gson();
                String json = gson.toJson(mainDeviceProperties);
                editor.putString("finderProperties", json);
            } catch (Exception e) {
                e.printStackTrace();
            }

            editor.commit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void searchBluetoothDevices() {
        if(finderDevicesPresenter.checkEnableBluetoothAdapter()) {
            finderDevicesPresenter.searchBluetoothDevices();
        } else {
           onEnableBluetoothAdapter();
        }
        isFinded = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                searchBluetoothDevices();
            }
        }
    }

    public void onEnableBluetoothAdapter() {
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }

    @Nullable
    @OnClick(R.id.searchBluetoothButton)
    public void searchBluetoothButton() {
        searchBluetoothDevices();
    }

    @Nullable
    @OnClick(R.id.buttonFloatDevice)
    public void searchBluetoothFloatButton() {
        searchBluetoothDevices();
    }

    public void refreshListView(PairedDevicesRecyclerAdapter findedDevices, ArrayList<DevicesProperties> devicesProperties) {
        mainDeviceProperties = devicesProperties;
        recyclerView.setAdapter(findedDevices);
    }

    public void dissableSearchButton(boolean disabled) {
        if (disabled == true) {
            searchBluetoothButton.setEnabled(false);
            searchBluetoothFloatButton.setEnabled(false);
        } else {
            searchBluetoothButton.setEnabled(true);
            searchBluetoothFloatButton.setEnabled(true);
        }
    }

    public void showLoadingProgressBar(boolean enable) {
        if (enable == true) {
            progressBar.setVisibility(View.VISIBLE);
            progressText.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            progressText.setVisibility(View.INVISIBLE);
        }
    }
}
