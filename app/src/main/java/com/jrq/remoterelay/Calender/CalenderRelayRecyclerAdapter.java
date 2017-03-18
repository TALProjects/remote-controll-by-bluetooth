package com.jrq.remoterelay.Calender;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.jrq.remoterelay.Database.Model.CalenderRelay;
import com.jrq.remoterelay.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jrq on 2016-10-09.
 */

public class CalenderRelayRecyclerAdapter extends RecyclerView.Adapter<CalenderRelayRecyclerAdapter.ViewHolder> {
    private static CalenderRelayRecyclerAdapter.CalenderRelayRecyclerWriterMessage writeToDevice;
    private static Context activity;
    private static List<CalenderRelay> calenderRelays;

    int resIDImageLightOn;
    int resIDImageLightOff;
    int resIDImageLightNone;
    Map<String, Integer> drawableLightMap;

    public CalenderRelayRecyclerAdapter(Context context, CalenderRelayRecyclerAdapter.CalenderRelayRecyclerWriterMessage writerToDevice, List<CalenderRelay> calenderRelays) {
        this.activity = context;
        this.calenderRelays = calenderRelays;
        this.writeToDevice = writerToDevice;
    }

    public void setCalenderRelays(List<CalenderRelay> calenderRelayss) {
        calenderRelays = calenderRelayss;
    }

    @Override
    public CalenderRelayRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.cardview_calender, null);

        resIDImageLightOn = activity.getResources().getIdentifier("ledgreen", "drawable", activity.getPackageName());
        resIDImageLightOff = activity.getResources().getIdentifier("redlight", "drawable", activity.getPackageName());
        resIDImageLightNone = activity.getResources().getIdentifier("greylight2", "drawable", activity.getPackageName());

        drawableLightMap = new HashMap<>();
        drawableLightMap.put("ledgreen", resIDImageLightOn);
        drawableLightMap.put("redlight", resIDImageLightOff);
        drawableLightMap.put("greylight2", resIDImageLightNone);

        CalenderRelayRecyclerAdapter.ViewHolder viewHolder = new CalenderRelayRecyclerAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CalenderRelayRecyclerAdapter.ViewHolder viewHolder, final int i) {
      String status = calenderRelays.get(i).getStatus();

            if (status.equals("off")) {
                Integer text = i + 1;
                int resIDImageRelay = activity.getResources().getIdentifier("circleblue3", "drawable", activity.getPackageName());
                viewHolder.tvVersionName.setText(text.toString());
                viewHolder.iconView.setImageResource(resIDImageRelay);
                viewHolder.oneLightOn.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.twoLightOn.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.threeLightOn.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.fourLightOn.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.fiveLightOn.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.sixLightOn.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.sevenLightOn.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.eightLightOn.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.oneLightOff.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.twoLightOff.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.threeLightOff.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.fourLightOff.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.fiveLightOff.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.sixLightOff.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.sevenLightOff.setImageResource(drawableLightMap.get("greylight2"));
                viewHolder.eightLightOff.setImageResource(drawableLightMap.get("greylight2"));

            } else if (status.equals("doba")) {
                Integer text = i + 1;
                int resIDImageRelay = activity.getResources().getIdentifier("circleblue3", "drawable", activity.getPackageName());
                viewHolder.tvVersionName.setText(text.toString());
                viewHolder.iconView.setImageResource(resIDImageRelay);
                viewHolder.oneLightOn.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOnOneDoba().equals("--:--") ? "greylight2" : "ledgreen"));
                viewHolder.twoLightOn.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOnTwoDoba().equals("--:--") ? "greylight2" : "ledgreen"));
                viewHolder.threeLightOn.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOnThreeDoba().equals("--:--") ? "greylight2" : "ledgreen"));
                viewHolder.fourLightOn.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOnFourDoba().equals("--:--") ? "greylight2" : "ledgreen"));
                viewHolder.fiveLightOn.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOnFiveDoba().equals("--:--") ? "greylight2" : "ledgreen"));
                viewHolder.sixLightOn.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOnSixDoba().equals("--:--") ? "greylight2" : "ledgreen"));
                viewHolder.sevenLightOn.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOnSevenDoba().equals("--:--") ? "greylight2" : "ledgreen"));
                viewHolder.eightLightOn.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOnEightDoba().equals("--:--") ? "greylight2" : "ledgreen"));
                viewHolder.oneLightOff.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOffOneDoba().equals("--:--") ? "greylight2" : "redlight"));
                viewHolder.twoLightOff.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOffTwoDoba().equals("--:--") ? "greylight2" : "redlight"));
                viewHolder.threeLightOff.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOffThreeDoba().equals("--:--") ? "greylight2" : "redlight"));
                viewHolder.fourLightOff.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOffFourDoba().equals("--:--") ? "greylight2" : "redlight"));
                viewHolder.fiveLightOff.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOffFiveDoba().equals("--:--") ? "greylight2" : "redlight"));
                viewHolder.sixLightOff.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOffSixDoba().equals("--:--") ? "greylight2" : "redlight"));
                viewHolder.sevenLightOff.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOffSevenDoba().equals("--:--") ? "greylight2" : "redlight"));
                viewHolder.eightLightOff.setImageResource(drawableLightMap.get(calenderRelays.get(i).getOffEightDoba().equals("--:--") ? "greylight2" : "redlight"));

            } else if (status.equals("tydzień")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ENGLISH);
                Date d = new Date();
                String dayOfTheWeek = sdf.format(d);
                Integer text = i + 1;
                int resIDImageRelay = activity.getResources().getIdentifier("circleblue3", "drawable", activity.getPackageName());
                viewHolder.tvVersionName.setText(text.toString());
                viewHolder.iconView.setImageResource(resIDImageRelay);
                try {
                    viewHolder.oneLightOn.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OnOne" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "ledgreen"))));
                    viewHolder.oneLightOff.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OffOne" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "redlight"))));
                    viewHolder.twoLightOn.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OnTwo" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "ledgreen"))));
                    viewHolder.twoLightOff.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get" + "OffTwo" + dayOfTheWeek ).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "redlight"))));
                    viewHolder.threeLightOn.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OnThree" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "ledgreen"))));
                    viewHolder.threeLightOff.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OffThree" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "redlight"))));
                    viewHolder.fourLightOn.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OnFour" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "ledgreen"))));
                    viewHolder.fourLightOff.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OffFour" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "redlight"))));
                    viewHolder.fiveLightOn.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OnFive" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "ledgreen"))));
                    viewHolder.fiveLightOff.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OffFive" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "redlight"))));
                    viewHolder.sixLightOn.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OnSix" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "ledgreen"))));
                    viewHolder.sixLightOff.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OffSix" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "redlight"))));
                    viewHolder.sevenLightOn.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OnSeven" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "ledgreen"))));
                    viewHolder.sevenLightOff.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OffSeven" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "redlight"))));
                    viewHolder.eightLightOn.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OnEight" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "ledgreen"))));
                    viewHolder.eightLightOff.setImageResource(drawableLightMap.get(((((String) calenderRelays.get(i).getClass().getMethod("get"  + "OffEight" + dayOfTheWeek).invoke(calenderRelays.get(i), null)).equals("--:--") ? "greylight2" : "redlight"))));
                } catch (Exception ex) {

                }
            }

            viewHolder.buttonStatus.setText(status);

            if (status.equals("off")) {
                viewHolder.buttonStatus.setBackgroundColor(Color.RED);
            } else if (status.equals("doba")) {
                viewHolder.buttonStatus.setBackgroundColor(Color.YELLOW);
            } else if (status.equals("tydzień")) {
                viewHolder.buttonStatus.setBackgroundColor(Color.GREEN);
            }
        }

    @Override
    public int getItemCount() {
        return calenderRelays.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvVersionNameThermostat) public TextView tvVersionName;
        @BindView(R.id.iconIdThermostat) public ImageView iconView;

        @BindView(R.id.oneLightOn) public ImageView oneLightOn;
        @BindView(R.id.oneLightOff) public ImageView oneLightOff;
        @BindView(R.id.twoLightOn) public ImageView twoLightOn;
        @BindView(R.id.twoLightOff) public ImageView twoLightOff;
        @BindView(R.id.threeLightOn) public ImageView threeLightOn;
        @BindView(R.id.threeLightOff) public ImageView threeLightOff;
        @BindView(R.id.fourLightOn) public ImageView fourLightOn;
        @BindView(R.id.fourLightOff) public ImageView fourLightOff;
        @BindView(R.id.fiveLightOn) public ImageView fiveLightOn;
        @BindView(R.id.fiveLightOff) public ImageView fiveLightOff;
        @BindView(R.id.sixLightOn) public ImageView sixLightOn;
        @BindView(R.id.sixLightOff) public ImageView sixLightOff;
        @BindView(R.id.sevenLightOn) public ImageView sevenLightOn;
        @BindView(R.id.sevenLightOff) public ImageView sevenLightOff;
        @BindView(R.id.eightLightOn) public ImageView eightLightOn;
        @BindView(R.id.eightLightOff) public ImageView eightLightOff;
        @BindView(R.id.buttonCalender) public ButtonRectangle buttonStatus;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    writeToDevice.showCalenderChooseFragment(v, getAdapterPosition(), tvVersionName.getText().toString());
                }
            });
        }
    }

    public interface CalenderRelayRecyclerWriterMessage {
        public void showCalenderChooseFragment(View v, int position, String numberRealy);
    }
}
