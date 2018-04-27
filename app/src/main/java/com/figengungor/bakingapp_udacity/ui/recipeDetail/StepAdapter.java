package com.figengungor.bakingapp_udacity.ui.recipeDetail;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.figengungor.bakingapp_udacity.R;
import com.figengungor.bakingapp_udacity.data.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by figengungor on 4/18/2018.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    List<Step> steps;
    ItemListener itemListener;
    int selectedStepIndex = -1;

    interface ItemListener {
        void onItemClick(int stepIndex);
    }

    public StepAdapter(List<Step> steps, ItemListener itemListener) {
        this.steps = steps;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StepViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bindItem(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shortDescriptionTv)
        TextView shortDescriptionTv;
        @BindView(R.id.stepNoTv)
        TextView stepNoTv;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemListener.onItemClick(getAdapterPosition());
                }
            });
        }

        public void bindItem(Step step) {

            shortDescriptionTv.setText(step.getShortDescription());
            stepNoTv.setText(String.valueOf(getAdapterPosition()));
            if (getAdapterPosition() == selectedStepIndex) {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorAccent));
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.whiteTrans));
            }
        }
    }

    public void setSelectedStep(int selectedStepIndex) {
        this.selectedStepIndex = selectedStepIndex;
        notifyDataSetChanged();
    }

}
