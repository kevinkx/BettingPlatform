package com.rpll.okeoke.bettingplatform.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rpll.okeoke.bettingplatform.Model.Match;
import com.rpll.okeoke.bettingplatform.R;
import com.rpll.okeoke.bettingplatform.View.BetDetailActivity;

import java.util.ArrayList;

/**
 * Created by Kevin on 4/7/2018.
 */

public class BetAdapter extends RecyclerView.Adapter<BetAdapter.ViewHolder> {
    private ArrayList<Match> mDataset;
    private Context mContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtTeam1;
        public TextView txtTeam2;
        public TextView clickHere;
        public TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTeam1 = itemView.findViewById(R.id.team_1);
            txtTeam2 = itemView.findViewById(R.id.team_2);
            clickHere = itemView.findViewById(R.id.clickHere);
            status = itemView.findViewById(R.id.status);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BetAdapter(ArrayList<Match> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_bet, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String idMatch = mDataset.get(position).getId_match();
        holder.txtTeam1.setText(mDataset.get(position).getTeam_1());
        holder.txtTeam2.setText(mDataset.get(position).getTeam_2());
        holder.status.setText(mDataset.get(position).getStatus());
        if(mDataset.get(position).getStatus().equalsIgnoreCase("LIVE"))
        {
            holder.status.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        else if(mDataset.get(position).getStatus().equalsIgnoreCase("FINISHED"))
        {
            holder.status.setBackgroundColor(Color.parseColor("#0B6623"));
        }
        holder.clickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BetDetailActivity.BID = new String();
                Intent intent = new Intent(mContext, BetDetailActivity.class);
                BetDetailActivity.BID = idMatch;
                mContext.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}