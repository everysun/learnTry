package com.example.myrecyclerviewtest.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.myrecyclerviewtest.R;
import com.example.myrecyclerviewtest.beans.MoreTypeBean;
import java.util.List;

public class MoreTypeAdapter extends RecyclerView.Adapter{

    public static final int TYPE_FULL_IMAGE = 0;
    public static final int TYPE_RIGHT_IMAGE = 1;
    public static final int TYPE_THREE_IMAGE = 2;

    private final List<MoreTypeBean> mData;

    public MoreTypeAdapter(List<MoreTypeBean> data){
        this.mData = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view;
        if(viewType == TYPE_FULL_IMAGE){
            view = View.inflate(parent.getContext(), R.layout.item_type_full_image, null);
            return new FullImageHolder(view);
        }else if(viewType == TYPE_RIGHT_IMAGE){
            view = View.inflate(parent.getContext(), R.layout.item_type_left_right_image, null);
            return new RightImageHolder(view);
        }else if(viewType == TYPE_THREE_IMAGE){
            view = View.inflate(parent.getContext(), R.layout.item_type_three_image, null);
            return new  ThreeImagesHolder(view);
        }else{
            view = View.inflate(parent.getContext(), R.layout.item_type_left_right_image, null);
            return new RightImageHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){

    }

    @Override
    public int getItemCount(){
        if(mData != null){
            return mData.size();
        }
        return 0;

    }

    @Override
    public int getItemViewType(int position){
        MoreTypeBean moreTypeBean = mData.get(position);

        if(moreTypeBean.type == 0){
            return TYPE_FULL_IMAGE;
        }else if(moreTypeBean.type == 1){
            return TYPE_RIGHT_IMAGE;
        }else if(moreTypeBean.type == 2){
            return TYPE_THREE_IMAGE;
        }else{
            return TYPE_RIGHT_IMAGE;
        }
    }


    private class FullImageHolder extends RecyclerView.ViewHolder{
        public FullImageHolder(View itemView){
            super(itemView);
        }
    }

    private class ThreeImagesHolder extends RecyclerView.ViewHolder{
        public ThreeImagesHolder(View itemView){
            super(itemView);
        }
    }

    private class RightImageHolder extends RecyclerView.ViewHolder{
        public RightImageHolder(View itemView){
            super(itemView);
        }
    }



}
