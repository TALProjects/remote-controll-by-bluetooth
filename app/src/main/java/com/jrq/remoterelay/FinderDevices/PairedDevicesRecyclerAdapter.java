package com.jrq.remoterelay.FinderDevices;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jrq.remoterelay.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jrq on 2016-09-14.
 */

public class PairedDevicesRecyclerAdapter extends RecyclerView.Adapter<PairedDevicesRecyclerAdapter.ViewHolder> {
    private static ArrayList<DevicesProperties> deviceProperties;
    private static PairedDevicesRecyclerCaller pairedContext;

    public PairedDevicesRecyclerAdapter(ArrayList<DevicesProperties> deviceProperties, PairedDevicesRecyclerCaller pairedContext) {
        this.deviceProperties = deviceProperties;
        this.pairedContext = pairedContext;
    }

    @Override
    public PairedDevicesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.paired_devices_recycler_adapter, null);

        ButterKnife.bind(this, itemLayoutView);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PairedDevicesRecyclerAdapter.ViewHolder viewHolder, int i) {
        DevicesProperties fp = deviceProperties.get(i);
        viewHolder.lightView.setImageResource(fp.getImageLight());
        viewHolder.title.setText(fp.getTitle());
        viewHolder.iconView.setImageResource(fp.getThumbnail());
        viewHolder.connectedIcon.setImageResource(fp.getImageConnected());
        viewHolder.devicePropertiesLocal = fp;
    }

    @Override
    public int getItemCount() {
        return deviceProperties.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.descriptionDevice) public TextView title;
        @BindView(R.id.iconIdDevice) public ImageView iconView;
        @BindView(R.id.oneLightOn) public ImageView lightView;
        @BindView(R.id.connectedicon) public ImageView connectedIcon;
        private boolean isConnected = false;


        public DevicesProperties devicePropertiesLocal;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(devicePropertiesLocal.getImageLight() == R.drawable.ledgreen) {
                        if(devicePropertiesLocal.getImageConnected() == R.drawable.greenaccept322) {
                            pairedContext.disconnectWithDevice(v, getAdapterPosition());
                        } else {
                            pairedContext.connectWithDevice(v, getAdapterPosition());
                        }
                    }
                }
            });
        }
    }

    public interface PairedDevicesRecyclerCaller {
        public void connectWithDevice(View v, int position);
        public void disconnectWithDevice(View w, int position);
    }
}