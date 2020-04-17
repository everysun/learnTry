package com.example.myrecyclerviewtest.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myrecyclerviewtest.R;
import com.example.myrecyclerviewtest.beans.ItemBean;
import java.util.List;

public abstract class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final List<ItemBean> mData;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewBaseAdapter(List<ItemBean> data){
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = getSubView(parent, viewType);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        ((InnerHolder) holder).setData(mData.get(position), position);

    }

    @Override
    public int getItemCount(){
        if(mData != null){
            return mData.size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }



    protected abstract View getSubView(ViewGroup parent, int viewType);



    public interface OnItemClickListener{
        void onItemClick(int position);
    }


    public class InnerHolder extends RecyclerView.ViewHolder{
        private ImageView mIcon;
        private TextView mTitle;
        private int mPosition;

        public InnerHolder(View itemView){
            super(itemView);

            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mTitle = (TextView) itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(mPosition);
                    }
                }
            });

        }


        public void setData(ItemBean itemBean, int position){
            this.mPosition = position;

            mIcon.setImageResource(itemBean.icon);
            mTitle.setText(itemBean.title);
        }
    }

}
