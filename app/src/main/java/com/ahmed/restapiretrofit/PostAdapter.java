package com.ahmed.restapiretrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private ItemClickListener mItemClickListener;
    List<Post> mPostList;
    Context mContext;

    public PostAdapter(Context context , ItemClickListener listener) {
        mContext = context;
        this.mItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item , parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText("User ID: " + mPostList.get(position).getUserId());
        holder.title.setText("Title: " + mPostList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        if(mPostList == null){
            return 0;
        }
        return mPostList.size();
    }


    public void setPostList(List<Post> postList) {
        mPostList = postList;
        notifyDataSetChanged();
    }


    public List<Post> getPostList() {
        return mPostList;
    }


    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView id;
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            title = itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClickListener(getAdapterPosition());
        }
    }
}
