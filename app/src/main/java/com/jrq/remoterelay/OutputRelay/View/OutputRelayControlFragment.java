package com.jrq.remoterelay.OutputRelay.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jrq.remoterelay.Database.Model.OutputRelay;
import com.jrq.remoterelay.OutputRelay.MvpOutputRelayView;
import com.jrq.remoterelay.OutputRelay.OutputRelayRecyclerAdapter;
import com.jrq.remoterelay.OutputRelay.Presenter.OutputRelayControlPresenter;
import com.jrq.remoterelay.R;
import com.jrq.remoterelay.RemoteApplication;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jrq on 2016-09-12.
 */
public class OutputRelayControlFragment extends Fragment implements MvpOutputRelayView {


    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;

    @Inject
    OutputRelayControlPresenter outputRelayControlPresenter;

    public static OutputRelayRecyclerAdapter outputRelayRecyclerAdapter;
    private static List<OutputRelay> listOutputRelays;
    private static boolean isStartContentView = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser == true) {
                outputRelayControlPresenter.registerEventBus(true);
                if(isStartContentView == false) {
                    outputRelayControlPresenter.onStartServices();
                    isStartContentView = true;
                }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((RemoteApplication) this.getActivity().getApplication())
                .getComponent()
                .inject(this);

        outputRelayControlPresenter.attachView(this, getActivity(), listOutputRelays);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.output_relay_control_fragment, container, false);
        ButterKnife.bind(this, v);

        listOutputRelays = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(isStartContentView == true) {
            SharedPreferences prefs = getContext().getSharedPreferences("userdetails", getContext().MODE_PRIVATE);
            Gson gson = new Gson();
            String json = prefs.getString("outputProperties", "");

            Type type = new TypeToken<List<OutputRelay>>(){}.getType();
            ArrayList<OutputRelay> tDeviceProperties = new ArrayList<OutputRelay>();
            tDeviceProperties = gson.fromJson(json, type);
            if(tDeviceProperties != null) {
                refreshListView(tDeviceProperties);
            }
        } else {
            listOutputRelays.clear();
            refreshListView(listOutputRelays);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences prefs = getContext().getSharedPreferences("userdetails", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            Gson gson = new Gson();
            String json = gson.toJson(listOutputRelays);
            editor.putString("outputProperties", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        editor.commit();
    }

    public void setStartContentView() {
        outputRelayControlPresenter.unBindServices();
        isStartContentView = false;
    }

    @Override
    public void refreshListView(List<OutputRelay> outputRelays) {

        if(listOutputRelays.size() > 0) {
            listOutputRelays = outputRelays;
            outputRelayRecyclerAdapter = (OutputRelayRecyclerAdapter) recyclerView.getAdapter();
            outputRelayRecyclerAdapter.setOutputRelays(listOutputRelays);
            outputRelayRecyclerAdapter.notifyDataSetChanged();
        } else {
            listOutputRelays = outputRelays;
            outputRelayRecyclerAdapter = new OutputRelayRecyclerAdapter(outputRelayControlPresenter, getActivity(), listOutputRelays);
            recyclerView.setAdapter(outputRelayRecyclerAdapter);
        }
    }


}
