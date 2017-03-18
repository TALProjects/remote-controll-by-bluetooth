package com.jrq.remoterelay.Calender;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jrq.remoterelay.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jrq on 2016-10-24.
 */

public class RecyclerHorizontalViewAdapter  extends RecyclerView.Adapter<RecyclerHorizontalViewAdapter.ViewHolder>{
    private static ChangeItemInRecycler changeItemInRecycler;

    String[] days = {"Doba", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"};

    public RecyclerHorizontalViewAdapter(ChangeItemInRecycler changeiteminRecycler) {
        this.changeItemInRecycler = changeiteminRecycler;
    }

    @Override
    public RecyclerHorizontalViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recycler_horizontal_calender, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        viewHolder.getLayoutPosition();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerHorizontalViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.daysTextView.setText(days[i]);
    }

    @Override
    public int getItemCount() {
        return days.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.days) TextView daysTextView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeItemInRecycler.changeItemInHorizontalRecycler(getLayoutPosition());
                }
            });
        }
    }

    public interface ChangeItemInRecycler {
        public void changeItemInHorizontalRecycler(int position);
    }
}
