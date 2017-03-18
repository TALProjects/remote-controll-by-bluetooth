package com.jrq.remoterelay.Calender;

import android.app.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.jrq.remoterelay.Calender.Presenter.CalenderChoosePresenter;
import com.jrq.remoterelay.Database.Model.CalenderRelay;
import com.jrq.remoterelay.R;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jrq on 2016-10-24.
 */

public class RecyclerVerticalViewAdapter extends RecyclerView.Adapter<RecyclerVerticalViewAdapter.ViewHolder> {

    static String[] days = {"1 ON", "1 OFF", "2 ON", "2 OFF", "3 ON", "3 OFF", "4 ON", "4 OFF", "5 ON", "5 OFF", "6 ON", "6 OFF", "7 ON", "7 OFF", "8 ON", "8 OFF"};
    static String[] daysWeek = {"Doba", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public static CalenderRelay calenderRelays;
    private static String weekDays;
    private static Activity mainActivity;
    private static CalenderChoosePresenter mCalenderChoosePresenter;
    private static FragmentManager fragmentManager;

    private static RecyclerVerticalViewAdapter.ViewHolder viewHolder;
    private static int idReject;
    private static int idAccept;

    private static int positionWeek;
    private static View verticalView;
    private static boolean isFirstOpen = false;

    public static ArrayList<String> hourDoba = new ArrayList<>();
    public static ArrayList<String> hourMonday = new ArrayList<>();
    public static ArrayList<String> hourTuesday = new ArrayList<>();
    public static ArrayList<String> hourWednesday = new ArrayList<>();
    public static ArrayList<String> hourThursday = new ArrayList<>();
    public static ArrayList<String> hourFriday = new ArrayList<>();
    public static ArrayList<String> hourSaturday = new ArrayList<>();
    public static ArrayList<String> hourSunday = new ArrayList<>();

    public static boolean changeDoba = false;
    public static boolean changeMonday = false;
    public static boolean changeTuesday = false;
    public static boolean changeWednesday = false;
    public static boolean changeThursday = false;
    public static boolean changeFriday = false;
    public static boolean changeSaturday = false;
    public static boolean changeSunday = false;

    private static Map<Integer, String> mapDays = new HashMap<Integer, String>() {{
        put(0, "OnOne"); put(1, "OffOne");
        put(2, "OnTwo"); put(3, "OffTwo");
        put(4, "OnThree"); put(5, "OffThree");
        put(6, "OnFour"); put(7, "OffFour");
        put(8, "OnFive"); put(9, "OffFive");
        put(10, "OnSix"); put(11, "OffSix");
        put(12, "OnSeven"); put(13, "OffSeven");
        put(14, "OnEight"); put(15, "OffEight");
    }};

    public RecyclerVerticalViewAdapter(CalenderRelay calenderRelays, String weekDays, Activity mainActivity, CalenderChoosePresenter calenderChoosePresenter, FragmentManager fragmentManager) {
        this.calenderRelays = calenderRelays;
        this.weekDays = weekDays;
        this.mainActivity = mainActivity;
        this.mCalenderChoosePresenter = calenderChoosePresenter;
        this.fragmentManager = fragmentManager;
    }

    public void setCalenderRelays(CalenderRelay calenderRelayss, String weekdayss) {
        this.calenderRelays = calenderRelayss;
        this.weekDays = weekdayss;
    }

    @Override
    public RecyclerVerticalViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recycler_vertical_calender, null);

        idReject = mainActivity.getResources().getIdentifier("reject32", "drawable", mainActivity.getPackageName());
        idAccept = mainActivity.getResources().getIdentifier("greenaccept322", "drawable", mainActivity.getPackageName());

        RecyclerVerticalViewAdapter.ViewHolder viewHolder = new RecyclerVerticalViewAdapter.ViewHolder(itemLayoutView);
        viewHolder.getLayoutPosition();
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        if(calenderRelays == null) {
            return 0;
        } else {
            return 16;
        }
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

    }

    @Override
    public void onBindViewHolder(RecyclerVerticalViewAdapter.ViewHolder viewHolder, int i) {
        String hour = "";
        viewHolder.tvVersionName.setText(days[i]);

    try {
    Field changeField = RecyclerVerticalViewAdapter.class.getDeclaredField("change" + weekDays);
    changeField.setAccessible(true);
    if(changeField.getBoolean(RecyclerVerticalViewAdapter.class) == true) {
        switch(weekDays) {
            case "Doba":
                hourDoba.get(i);
                break;
            case "Monday":
                hourMonday.get(i);
                break;
            case "Tuesday":
                hourTuesday.get(i);
                break;
            case "Wednesday":
                hourWednesday.get(i);
                break;
            case "Thursday":
                hourThursday.get(i);
                break;
            case "Friday":
                hourFriday.get(i);
                break;
            case "Saturday":
                hourSaturday.get(i);
                break;
            case "Sunday":
                hourSunday.get(i);
                break;
        }
    } else {

        try {
            hour = (String) calenderRelays.getClass().getMethod("get"  + mapDays.get(i) + weekDays, null).invoke(calenderRelays, null);
            if(hourDoba.size() < 16) {
                hourDoba.add(hour);
            }
        } catch(Exception e) {
            hour = null;
        }
    }

    if(hour != null) {
        viewHolder.timeDays.setText(hour);
    }

    try {
        Field changeFieldWeek = RecyclerVerticalViewAdapter.class.getDeclaredField("change" + weekDays);
        changeFieldWeek.setAccessible(true);
        if(changeField.getBoolean(RecyclerVerticalViewAdapter.class) == false) {
            if(!hour.equals("--:--")) {
                viewHolder.statusImage.setImageResource(idAccept);
            } else {
                viewHolder.statusImage.setImageResource(idReject);
            }
        }
    } catch (Exception e) {

    }
} catch(Exception e) {

}
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.days) TextView tvVersionName;
        @BindView(R.id.timeDays) TextView timeDays;
        @BindView(R.id.statusImage) ImageView statusImage;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SlideDateTimePicker.Builder(fragmentManager)
                            .setListener(listener)
                            .setTheme(SlideDateTimePicker.HOLO_LIGHT)
                            .setIs24HourTime(true)
                            .build()
                            .show();

                    positionWeek = getAdapterPosition();
                    verticalView = v;
                }
            });

            timeDays.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SlideDateTimePicker.Builder(fragmentManager)
                            .setListener(listener)
                            .setTheme(SlideDateTimePicker.HOLO_LIGHT)
                            .setIs24HourTime(true)
                            .build()
                            .show();

                    positionWeek = getAdapterPosition();
                    verticalView = v;
                }
            });

            statusImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (statusImage.getDrawable().getConstantState() == mainActivity.getResources().getDrawable(idAccept).getConstantState()) {
                       // mCalenderChoosePresenter.recyclerViewCalenderVerticalClearHour(itemLayoutView, getAdapterPosition());

                        positionWeek = getAdapterPosition();
                        verticalView = itemLayoutView;

                        try {
                            switch (weekDays) {
                                case "Doba":
                                    hourDoba.set(positionWeek, "--:--");
                                    break;
                                case "Monday":
                                    hourMonday.set(positionWeek, "--:--");
                                    break;
                                case "Tuesday":
                                    hourTuesday.set(positionWeek, "--:--");
                                    break;
                                case "Wednesday":
                                    hourWednesday.set(positionWeek, "--:--");
                                    break;
                                case "Thursday":
                                    hourThursday.set(positionWeek, "--:--");
                                    break;
                                case "Friday":
                                    hourFriday.set(positionWeek, "--:--");
                                    break;
                                case "Saturday":
                                    hourSaturday.set(positionWeek, "--:--");
                                    break;
                                case "Sunday":
                                    hourSunday.set(positionWeek, "--:--");
                                    break;
                            }
                            setChangeBoolean(weekDays);
                            RelativeLayout r = (RelativeLayout) v.getParent();
                            TextView thour = (TextView) r.findViewById(R.id.timeDays);

                            thour.setText("--:--");

                        } catch(Exception e) {

                         }
                    } else {
                        new SlideDateTimePicker.Builder(fragmentManager)
                                .setListener(listener)
                                .setTheme(SlideDateTimePicker.HOLO_LIGHT)
                                .setIs24HourTime(true)
                                .build()
                                .show();

                        positionWeek = getAdapterPosition();
                        verticalView = v;
                    }
                } });
        }
    }

    private static SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            String hour;
            if(date != null) {
                DateFormat df = new SimpleDateFormat("H:mm");
                hour = df.format(date);
            } else {
                hour = "--:--";
            }

            try {
                switch(weekDays) {
                    case "Doba":
                            hourDoba.set(positionWeek, hour);
                        break;
                    case "Monday":
                            hourMonday.set(positionWeek, hour);
                        break;
                    case "Tuesday":
                            hourTuesday.set(positionWeek, hour);
                        break;
                    case "Wednesday":
                            hourWednesday.set(positionWeek, hour);
                        break;
                    case "Thursday":
                            hourThursday.set(positionWeek, hour);
                        break;
                    case "Friday":
                            hourFriday.set(positionWeek, hour);
                        break;
                    case "Saturday":
                            hourSaturday.set(positionWeek, hour);
                        break;
                    case "Sunday":
                            hourSunday.set(positionWeek, hour);
                        break;
                }
                setChangeBoolean(weekDays);

                TextView thour = (TextView) verticalView.findViewById(R.id.timeDays);
                thour.setText(hour);

            } catch (Exception e) {

            }
        }
    };

    public static void setChangeBoolean(String weekDays) {
        try {
            Field changeField = RecyclerVerticalViewAdapter.class.getDeclaredField("change" + weekDays);
            changeField.setAccessible(true);
            changeField.setBoolean(RecyclerVerticalViewAdapter.class, true);
        } catch (Exception e) {
        }
    }

    public interface RecyclerViewClickListener {
        public void recyclerViewCalenderVerticalChangeHourClicker(View v, int position);
        public void recyclerViewCalenderVerticalClearHour(View v, int position);
    }

    public static void changeDayOfWeek(int position) {
        weekDays = daysWeek[position];

        try {
            Field changeField = RecyclerVerticalViewAdapter.class.getDeclaredField("change" + weekDays);
            changeField.setAccessible(true);
            if (changeField.getBoolean(RecyclerVerticalViewAdapter.class) == false) {
                switch (weekDays) {
                    case "Doba":
                        hourDoba.clear();
                        break;
                    case "Monday":
                        hourMonday.clear();
                        break;
                    case "Tuesday":
                        hourTuesday.clear();
                        break;
                    case "Wednesday":
                        hourWednesday.clear();
                        break;
                    case "Thursday":
                        hourThursday.clear();
                        break;
                    case "Friday":
                        hourFriday.clear();
                        break;
                    case "Saturday":
                        hourSaturday.clear();
                        break;
                    case "Sunday":
                        hourSunday.clear();
                        break;
                }

                for (int i = 0; i < 16; i++) {
                    switch (weekDays) {
                        case "Doba":
                            hourDoba.add((String) calenderRelays.getClass().getMethod("get" + mapDays.get(i) + weekDays, null).invoke(calenderRelays, null));
                            break;
                        case "Monday":
                            hourMonday.add((String) calenderRelays.getClass().getMethod("get" + mapDays.get(i) + weekDays, null).invoke(calenderRelays, null));
                            break;
                        case "Tuesday":
                            hourTuesday.add((String) calenderRelays.getClass().getMethod("get" + mapDays.get(i) + weekDays, null).invoke(calenderRelays, null));
                            break;
                        case "Wednesday":
                            hourWednesday.add((String) calenderRelays.getClass().getMethod("get" + mapDays.get(i) + weekDays, null).invoke(calenderRelays, null));
                            break;
                        case "Thursday":
                            hourThursday.add((String) calenderRelays.getClass().getMethod("get" + mapDays.get(i) + weekDays, null).invoke(calenderRelays, null));
                            break;
                        case "Friday":
                            hourFriday.add((String) calenderRelays.getClass().getMethod("get" + mapDays.get(i) + weekDays, null).invoke(calenderRelays, null));
                            break;
                        case "Saturday":
                            hourSaturday.add((String) calenderRelays.getClass().getMethod("get" + mapDays.get(i) + weekDays, null).invoke(calenderRelays, null));
                            break;
                        case "Sunday":
                            hourSunday.add((String) calenderRelays.getClass().getMethod("get" + mapDays.get(i) + weekDays, null).invoke(calenderRelays, null));
                            break;
                    }
                }
            }
        } catch(Exception e){

        }
    }
}
