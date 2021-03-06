package com.rpll.okeoke.bettingplatform.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rpll.okeoke.bettingplatform.Model.Report;
import com.rpll.okeoke.bettingplatform.R;
import com.rpll.okeoke.bettingplatform.Controller.ViewDetailReportActivity;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private ArrayList<Report> dataset;
    private static Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView txtCategory;
        public TextView txtDate;

        public ViewHolder(Context mcontext, View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.viewReportCategory);
            txtDate = itemView.findViewById(R.id.viewReportDate);
        }

//        @Override
//        public void onClick(View view) {
//            Intent intent = new Intent(context, ViewDetailReportActivity.class);
//            intent.putExtra("position", getAdapterPosition());
//            context.startActivity(intent);
//        }
    }

    public ReportAdapter(Context context, ArrayList<Report> mDataset) {
        dataset = mDataset;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_report, parent, false);

        ViewHolder vh = new ViewHolder(context, v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtCategory.setText(dataset.get(position).getCategory());
        holder.txtDate.setText(dataset.get(position).getDate());

        holder.txtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewDetailReportActivity.class);
                intent.putExtra("position", Integer.toString(position+1));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}