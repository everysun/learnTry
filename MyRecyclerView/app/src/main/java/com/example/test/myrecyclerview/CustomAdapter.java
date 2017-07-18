package com.example.test.myrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.myrecyclerview.common.logger.Log;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    private String[] mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;

        public ViewHolder(View v){
            super(v);

            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            textView = (TextView)v.findViewById(R.id.textView);
        }

        public TextView getTextView(){
            return textView;
        }
    }

    public CustomAdapter(String[] dataSet){
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_item,
                viewGroup, false);
        return new ViewHolder(v);

    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position){
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getTextView().setText(mDataSet[position]);
    }

    @Override
    public int getItemCount(){
        return mDataSet.length;
    }

}
