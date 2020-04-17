package com.example.myrecyclerviewtest.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myrecyclerviewtest.R;
import com.example.myrecyclerviewtest.beans.ItemBean;
import java.util.List;


public class ListViewAdapter extends RecyclerViewBaseAdapter {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_LOADER_MORE = 1;

    private OnRefreshListener mUpPullRefreshListener;

    public ListViewAdapter(List<ItemBean>data){
        super(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = getSubView(parent, viewType);
        if(viewType == TYPE_NORMAL){
            return new InnerHolder(view);
        }else{
            return new LoaderMoreHolder(view);
        }
    }


    @Override
    protected View getSubView(ViewGroup parent, int viewType){
        View view;

        if(viewType == TYPE_NORMAL){
            view = View.inflate(parent.getContext(), R.layout.item_list_view, null);
        }else{
            view = View.inflate(parent.getContext(), R.layout.item_list_loader_more, null);
        }

        return view;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        if(getItemViewType(position) == TYPE_NORMAL && holder instanceof InnerHolder){
            ((InnerHolder)holder).setData(mData.get(position), position);
        }else if(getItemViewType(position) == TYPE_LOADER_MORE && holder instanceof LoaderMoreHolder){
            ((LoaderMoreHolder)holder).update(LoaderMoreHolder.LOADER_STATE_LOADING);
        }
    }


    @Override
    public int getItemViewType(int position){
        if(position == getItemCount() - 1){
            return TYPE_LOADER_MORE;
        }
        return TYPE_NORMAL;
    }


    public void setOnRefresListener(OnRefreshListener listener){
        this.mUpPullRefreshListener = listener;
    }


    public interface OnRefreshListener{
        void onUpPullRefresh(LoaderMoreHolder loaderMoreHolder);
    }



    public class LoaderMoreHolder extends RecyclerView.ViewHolder{

        public static final int LOADER_STATE_LOADING = 0;
        public static final int LOADER_STATE_RELOAD = 1;
        public static final int LOADER_STATE_NORMAL = 2;

        private LinearLayout mLoading;
        private TextView mReload;

        public LoaderMoreHolder(View itemView){
            super(itemView);

            mLoading = (LinearLayout)itemView.findViewById(R.id.loading);
            mReload = (TextView)itemView.findViewById(R.id.reload);

            mReload.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    update(LOADER_STATE_LOADING);
                }
            });
        }

        public void update(int state){
            mLoading.setVisibility(View.GONE);
            mReload.setVisibility(View.GONE);

            switch(state){
                case LOADER_STATE_LOADING:
                    mLoading.setVisibility(View.VISIBLE);
                    startLoaderMore();
                    break;
                case LOADER_STATE_RELOAD:
                    mReload.setVisibility(View.VISIBLE);
                    break;
                case LOADER_STATE_NORMAL:
                    mLoading.setVisibility(View.GONE);
                    mReload.setVisibility(View.GONE);
                    break;
            }
        }

        private void startLoaderMore(){
            if(mUpPullRefreshListener != null){
                mUpPullRefreshListener.onUpPullRefresh(this);
            }
        }

    }

}
