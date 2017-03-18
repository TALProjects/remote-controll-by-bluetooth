package com.jrq.remoterelaynewway.Thermostat;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jrq.remoterelaynewway.Database.Model.OutputRelay;
import com.jrq.remoterelaynewway.Database.Model.ThermostatRelay;
import com.jrq.remoterelaynewway.R;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jrq on 2016-09-18.
 */

public class ThermostatRecyclerAdapter extends RecyclerView.Adapter<ThermostatRecyclerAdapter.ViewHolder> {
    private Context context;
    private static WriterToDevice mWriterToDevice;
    private static List<ThermostatRelay> thermostatRelays;

    private static String statusRelay;
    private static String currentStatusRelay;

    int idGrzewczy;
    int idChlodzenie;
    int idWylaczony;

    public ThermostatRecyclerAdapter(Context context, WriterToDevice writerToDevice, List<ThermostatRelay> thermostatRelays) {
        this.context = context;
        this.mWriterToDevice = writerToDevice;
        this.thermostatRelays = thermostatRelays;

        idGrzewczy = context.getResources().getIdentifier("fire38", "drawable", context.getPackageName());
        idChlodzenie  = context.getResources().getIdentifier("cold38", "drawable", context.getPackageName());
        idWylaczony  = context.getResources().getIdentifier("dissable", "drawable", context.getPackageName());
    }

    @Override
    public ThermostatRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.cardview_thermostat, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }


    public void setThermostatRelays(List<ThermostatRelay> thermostatRelayss) {
        thermostatRelays = thermostatRelayss;
    }

    @Override
    public void onBindViewHolder(ThermostatRecyclerAdapter.ViewHolder viewHolder, int i) {
        int resIDImageRelay = context.getResources().getIdentifier("circleblue3", "drawable", context.getPackageName());
        statusRelay = thermostatRelays.get(i).getStatus();
        Integer numberRelay = thermostatRelays.get(i).getNumberRelay();

        viewHolder.tvVersionName.setText(numberRelay.toString());
        viewHolder.iconView.setImageResource(resIDImageRelay);

        String temp = thermostatRelays.get(i).getTemp();
        String minTemp = thermostatRelays.get(i).getMinTemp();
        String hysteresis = thermostatRelays.get(i).getHysteresisTemp();

        viewHolder.temp.setText(temp.replace("+", ""));
        viewHolder.minTemp.setText(minTemp.replace("+", ""));
        viewHolder.hysteresisTemp.setText(hysteresis.replace("+", ""));

        viewHolder.minValueTemp.setText(minTemp.isEmpty() ? "--" : minTemp + " \u00b0" + "C");
        viewHolder.maxValueTemp.setText(temp.isEmpty() ? "--" : temp + " \u00b0" + "C");

        if(statusRelay.equals("wyłącz")) {
            viewHolder.statusIcon.setImageResource(idWylaczony);
            viewHolder.wylaczLayout.setBackgroundColor(Color.parseColor("#f1eab3"));
            viewHolder.grzanieLayout.setBackgroundColor(Color.TRANSPARENT);
            viewHolder.chlodzenieLayout.setBackgroundColor(Color.TRANSPARENT);
        } else if(statusRelay.equals("grzanie")) {
            viewHolder.statusIcon.setImageResource(idGrzewczy);
            viewHolder.grzanieLayout.setBackgroundColor(Color.parseColor("#f1eab3"));
            viewHolder.chlodzenieLayout.setBackgroundColor(Color.TRANSPARENT);
            viewHolder.wylaczLayout.setBackgroundColor(Color.TRANSPARENT);
        } else {
            viewHolder.statusIcon.setImageResource(idChlodzenie);
            viewHolder.chlodzenieLayout.setBackgroundColor(Color.parseColor("#f1eab3"));
            viewHolder.grzanieLayout.setBackgroundColor(Color.TRANSPARENT);
            viewHolder.wylaczLayout.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return thermostatRelays.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvVersionNameThermostat) public TextView tvVersionName;
        @BindView(R.id.iconIdThermostat) public ImageView iconView;
        @BindView(R.id.iconTypeThermostat) public ImageView statusIcon;
        @BindView(R.id.expandable_part_layout) public ViewGroup expandableLayout;
        @BindView(R.id.valueMinThermostat) public TextView minValueTemp;
        @BindView(R.id.valueMaxThermostat) public TextView maxValueTemp;
        @BindView(R.id.minTempEditText) public EditText minTemp;
        @BindView(R.id.tempEditText) public EditText temp;
        @BindView(R.id.histerezaTempEditText) public EditText hysteresisTemp;
        @BindView(R.id.saveThermostat) public Button saveThermostat;
        @BindView(R.id.cancelThermostat) public Button cancelThermostat;
        @BindView(R.id.iconOne) public ImageButton grzanieIcon;
        @BindView(R.id.iconTwo) public ImageButton chlodniczaIcon;
        @BindView(R.id.iconThree) public ImageButton wylaczIcon;
        @BindView(R.id.grzanieLayout) public RelativeLayout grzanieLayout;
        @BindView(R.id.chlodzenieLayout) public RelativeLayout chlodzenieLayout;
        @BindView(R.id.wylaczLayout) public RelativeLayout wylaczLayout;

        private int mOriginalHeight = 0;
        private boolean mIsViewExpanded = false;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);

            grzanieIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    grzanieLayout.setBackgroundColor(Color.parseColor("#f1eab3"));
                    chlodzenieLayout.setBackgroundColor(Color.TRANSPARENT);
                    wylaczLayout.setBackgroundColor(Color.TRANSPARENT);
                    currentStatusRelay = "grzanie";
                }
            });

            chlodniczaIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chlodzenieLayout.setBackgroundColor(Color.parseColor("#f1eab3"));
                    grzanieLayout.setBackgroundColor(Color.TRANSPARENT);
                    wylaczLayout.setBackgroundColor(Color.TRANSPARENT);
                    currentStatusRelay = "chłodzenie";
                }
            });

            wylaczIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wylaczLayout.setBackgroundColor(Color.parseColor("#f1eab3"));
                    grzanieLayout.setBackgroundColor(Color.TRANSPARENT);
                    chlodzenieLayout.setBackgroundColor(Color.TRANSPARENT);
                    currentStatusRelay = "wyłącz";
                }
            });

            grzanieLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setBackgroundColor(Color.parseColor("#f1eab3"));
                    chlodzenieLayout.setBackgroundColor(Color.TRANSPARENT);
                    wylaczLayout.setBackgroundColor(Color.TRANSPARENT);
                    currentStatusRelay = "grzanie";
                }
            });

            chlodzenieLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setBackgroundColor(Color.parseColor("#f1eab3"));
                    grzanieLayout.setBackgroundColor(Color.TRANSPARENT);
                    wylaczLayout.setBackgroundColor(Color.TRANSPARENT);
                    currentStatusRelay = "chłodzenie";
                }
            });

            wylaczLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setBackgroundColor(Color.parseColor("#f1eab3"));
                    grzanieLayout.setBackgroundColor(Color.TRANSPARENT);
                    chlodzenieLayout.setBackgroundColor(Color.TRANSPARENT);
                    currentStatusRelay = "wyłącz";
                }
            });

            saveThermostat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String temperature = temp.getText().toString();
                    String minTemperature = minTemp.getText().toString();
                    String hysteresis = hysteresisTemp.getText().toString();

//                    Pattern sPattern
//                            = Pattern.compile("[-]\\d*?.\\d*?");
//                    if (sPattern.matcher(minTemperature).matches() && minTemperature != "0") {
//                    } else {
//                        Toast.makeText(itemLayoutView.getContext(), "Dane w polu minimalna temperatura nie zostały poprawnie wpisane",
//                                Toast.LENGTH_SHORT).show();
//
//                        return;
//                    }
//
//                    if (sPattern.matcher(temperature).matches() && temperature != "0") {
//                    } else {
//                        Toast.makeText(itemLayoutView.getContext(), "Dane w polu temperatura nie zostały poprawnie wpisane",
//                                Toast.LENGTH_SHORT).show();
//
//                        return;
//                    }

                    String status;
                    if(currentStatusRelay.equals("wyłącz")) {
                        status = "01";
                    } else if(currentStatusRelay.equals("grzanie")) {
                        status = "02";
                    } else {
                        status = "03";
                    }

                    mWriterToDevice.writeToDevice(Integer.toString(getAdapterPosition() + 1), currentStatusRelay, temperature, minTemperature, hysteresis);
                    itemLayoutView.callOnClick();
                }
            });

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    if (mOriginalHeight == 0) {
                        mOriginalHeight = view.getHeight();
                    }
                    ValueAnimator valueAnimator;
                    if (!mIsViewExpanded) {
                        mIsViewExpanded = true;
                        valueAnimator = ValueAnimator.ofInt(mOriginalHeight, mOriginalHeight + (int) (mOriginalHeight * 4.25));
                        expandableLayout.setVisibility(View.VISIBLE);

                        currentStatusRelay = thermostatRelays.get(getAdapterPosition()).getStatus();
                    } else {
                        mIsViewExpanded = false;
                        valueAnimator = ValueAnimator.ofInt(mOriginalHeight + (int) (mOriginalHeight * 4.25), mOriginalHeight);
                        expandableLayout.setVisibility(View.GONE);
                    }
                    valueAnimator.setDuration(300);
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Integer value = (Integer) animation.getAnimatedValue();
                            view.getLayoutParams().height = value.intValue();
                            view.requestLayout();
                        }
                    });
                    valueAnimator.start();

                    if (currentStatusRelay.equals("wyłącz")) {
                        wylaczLayout.setBackgroundColor(Color.parseColor("#f1eab3"));
                        grzanieLayout.setBackgroundColor(Color.TRANSPARENT);
                        chlodzenieLayout.setBackgroundColor(Color.TRANSPARENT);
                    } else if (currentStatusRelay.equals("grzanie")) {
                        grzanieLayout.setBackgroundColor(Color.parseColor("#f1eab3"));
                        chlodzenieLayout.setBackgroundColor(Color.TRANSPARENT);
                        wylaczLayout.setBackgroundColor(Color.TRANSPARENT);
                    } else {
                        chlodzenieLayout.setBackgroundColor(Color.parseColor("#f1eab3"));
                        grzanieLayout.setBackgroundColor(Color.TRANSPARENT);
                        wylaczLayout.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            });
        }
    }
    public interface WriterToDevice {
        public void writeToDevice(String position, String status, String temperature, String minTemperature, String hysteresis);
    }
}
