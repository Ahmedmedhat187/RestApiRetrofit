package com.ahmed.restapiretrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CommentAdapter  extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    List<Comment> mCommentLists;
    Context mContext;

    public CommentAdapter(Context context ) {
        mContext = context;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item , parent , false);
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.id.setText(mCommentLists.get(position).getId());
        holder.name.setText(mCommentLists.get(position).getName());
        holder.email.setText(mCommentLists.get(position).getEmail());
        holder.comment.setText(mCommentLists.get(position).getText());
        holder.postId.setText(mCommentLists.get(position).getPostId());
    }

    @Override
    public int getItemCount() {
        if(mCommentLists == null){
            return 0;
        }
        return mCommentLists.size();
    }

    public void setCommentLists(List<Comment> commentLists) {
        mCommentLists = commentLists;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id;
        TextView name;
        TextView email;
        TextView comment;
        TextView postId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_comment_id);
            name = itemView.findViewById(R.id.tv_comment_name);
            email = itemView.findViewById(R.id.tv_comment_email);
            comment = itemView.findViewById(R.id.tv_comment_comment);
            postId = itemView.findViewById(R.id.tv_post_id);
        }
    }

}
