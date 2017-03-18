package com.jrq.remoterelay.OutputRelay;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.jrq.remoterelay.Database.Model.OutputRelay;
import com.jrq.remoterelay.R;
import com.jrq.remoterelay.Utils.BluetoothCommand;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by jrq on 2016-09-15.
 */

public class OutputRelayRecyclerAdapter extends RecyclerView.Adapter<OutputRelayRecyclerAdapter.ViewHolder> {
    private static OutputRelayRecyclerWriterMessage context;
    private static Context activity;
    private static List<OutputRelay> outputRelays;

    public OutputRelayRecyclerAdapter(OutputRelayRecyclerWriterMessage context, Context activity, List<OutputRelay> outputRelays) {
        this.context = context;
        this.activity = activity;
        this.outputRelays = outputRelays;
    }

    public void setOutputRelays(List<OutputRelay> outputRelayss) {
        outputRelays = outputRelayss;
    }

    @Override
    public OutputRelayRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.cardview_output_control, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OutputRelayRecyclerAdapter.ViewHolder viewHolder, final int i) {
        int resIDImageRelay = activity.getResources().getIdentifier("circleblue3", "drawable", activity.getPackageName());
        Integer numberRelay = outputRelays.get(i).getNumberRelay();

        viewHolder.tvVersionName.setText(numberRelay.toString());
        viewHolder.iconView.setImageResource(resIDImageRelay);

        if(outputRelays.get(i).getOn() == 1) {

            viewHolder.onButton.setBackgroundColor(Color.parseColor("#81c784"));
            viewHolder.offButton.setBackgroundColor(Color.parseColor("#993432"));
            viewHolder.autoButton.setBackgroundColor(Color.parseColor("#ad9c57"));

            int resIDImageAutoLight = activity.getResources().getIdentifier("darkyellowlight", "drawable", activity.getPackageName());
            int resIDImageOffLight = activity.getResources().getIdentifier("darkredlight", "drawable", activity.getPackageName());
            int resIDImageOnLight = activity.getResources().getIdentifier("ledgreen", "drawable", activity.getPackageName());

            viewHolder.onLight.setImageResource(resIDImageOnLight);
            viewHolder.offLight.setImageResource(resIDImageOffLight);
            viewHolder.autoLight.setImageResource(resIDImageAutoLight);

        } else if(outputRelays.get(i).getOff() == 1) {

            viewHolder.onButton.setBackgroundColor(Color.parseColor("#466b47"));
            viewHolder.offButton.setBackgroundColor(Color.parseColor("#ef5350"));
            viewHolder.autoButton.setBackgroundColor(Color.parseColor("#ad9c57"));

            int resIDImageAutoLight = activity.getResources().getIdentifier("darkyellowlight", "drawable", activity.getPackageName());
            int resIDImageOffLight = activity.getResources().getIdentifier("redlight", "drawable", activity.getPackageName());
            int resIDImageOnLight = activity.getResources().getIdentifier("darkgreenlight", "drawable", activity.getPackageName());

            viewHolder.onLight.setImageResource(resIDImageOnLight);
            viewHolder.offLight.setImageResource(resIDImageOffLight);
            viewHolder.autoLight.setImageResource(resIDImageAutoLight);

        } else {

            viewHolder.onButton.setBackgroundColor(Color.parseColor("#466b47"));
            viewHolder.offButton.setBackgroundColor(Color.parseColor("#993432"));
            viewHolder.autoButton.setBackgroundColor(Color.parseColor("#ffe57f"));

            int resIDImageAutoLight = activity.getResources().getIdentifier("yellowlight", "drawable", activity.getPackageName());
            int resIDImageOffLight = activity.getResources().getIdentifier("darkredlight", "drawable", activity.getPackageName());
            int resIDImageOnLight = activity.getResources().getIdentifier("darkgreenlight", "drawable", activity.getPackageName());

            viewHolder.onLight.setImageResource(resIDImageOnLight);
            viewHolder.offLight.setImageResource(resIDImageOffLight);
            viewHolder.autoLight.setImageResource(resIDImageAutoLight);
        }
    }

    @Override
    public int getItemCount() {
        return outputRelays.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvVersionName) public TextView tvVersionName;
        @BindView(R.id.iconId) public ImageView iconView;

        @BindView(R.id.buttonOnControl) public ButtonRectangle onButton;
        @BindView(R.id.buttonOffControl) public ButtonRectangle offButton;
        @BindView(R.id.buttonAutoControl) public ButtonRectangle autoButton;

        @BindView(R.id.greenLight) public ImageView onLight;
        @BindView(R.id.redLight) public ImageView offLight;
        @BindView(R.id.yellowLight) public ImageView autoLight;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
        }

        @OnClick(R.id.buttonOnControl)
        public void onButtonClick() {
           // context.outputRelayRecyclerShowChooseDuring(getAdapterPosition(), "On");
            context.outputRelayRecyclerWriterMessage(BluetoothCommand.MEM_ON[getAdapterPosition()], "On", Integer.toString(getAdapterPosition() + 1));
        }

        @OnClick(R.id.buttonOffControl)
        public void offButtonClick() {
            context.outputRelayRecyclerWriterMessage(BluetoothCommand.MEM_OFF[getAdapterPosition()], "Off", Integer.toString(getAdapterPosition() + 1));
        }

        @OnClick(R.id.buttonAutoControl)
        public void autoButtonClick() {
            context.outputRelayRecyclerWriterMessage(BluetoothCommand.MEM_AUTO[getAdapterPosition()], "Auto", Integer.toString(getAdapterPosition() + 1));
        }
    }

    public interface OutputRelayRecyclerWriterMessage {
        public void outputRelayRecyclerWriterMessage(String message, String status, String nameRelay);
        public void outputRelayRecyclerShowChooseDuring(int position, String status);
    }
}
