package com.itoo.ohue.journalapp.data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itoo.ohue.journalapp.R;
import com.itoo.ohue.journalapp.view.ViewJournalDetailsActivity;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {
    private Context mContext;
    private List<JournalEntity> mJournalEntityList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView journalTitle;
        private final TextView journalBodyPrev;
        private final TextView journalDate;
        private final LinearLayout journalItemLayout;




        public ViewHolder(final View itemView) {
            super(itemView);
            journalBodyPrev = (TextView) itemView.findViewById(R.id.journal_body_preview);
            journalTitle = (TextView) itemView.findViewById(R.id.journal_title);
            journalDate = (TextView) itemView.findViewById(R.id.journal_date);
            journalItemLayout = (LinearLayout) itemView.findViewById(R.id.layout_item);



            journalItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newIntent = new Intent(mContext, ViewJournalDetailsActivity.class);
                    JournalEntity journalEntity = getItem((int) itemView.getTag());
                    newIntent.putExtra(ViewJournalDetailsActivity.BODY, journalEntity.getDescription());
                    newIntent.putExtra(ViewJournalDetailsActivity.DATE, journalEntity.getDateCreated());
                    newIntent.putExtra(ViewJournalDetailsActivity.TOPIC, journalEntity.getTitle());
                    newIntent.putExtra(ViewJournalDetailsActivity.TAG, (int) itemView.getTag());
                    mContext.startActivity(newIntent);
                }
            });

        }
    }

    public JournalAdapter(Context context) {
        mContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_view_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mJournalEntityList != null) {
            holder.journalDate.setText("");
            holder.journalTitle.setText(mJournalEntityList.get(position).getTitle());
            holder.journalBodyPrev.setMaxLines(2);
            holder.journalBodyPrev.setText(mJournalEntityList.get(position).getDescription());
            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        if (mJournalEntityList != null) {
            return mJournalEntityList.size();
        }
        return 0;
    }


    public JournalEntity getItem(int position) {
        if (mJournalEntityList != null) {
            return mJournalEntityList.get(position);
        }
        return null;
    }

    public void setJournalEntityLists(List<JournalEntity> journalEntityLists) {
        mJournalEntityList = journalEntityLists;
        notifyDataSetChanged();
    }


}
