package com.example.hedgehog.kursach;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hedgehog.kursach.database.Comments;
import com.example.hedgehog.kursach.database.Films;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hedgehog on 23.05.17.
 */

public class CustomCommentAdapter extends ArrayAdapter<Comments> implements View.OnClickListener {

    Context context;

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    ArrayList<Comments> comments;


    public CustomCommentAdapter(@NonNull Context context, ArrayList<Comments> comments) {
        super(context, R.layout.comment_row, comments);
        this.context = context;
        setComments(comments);
    }

    @Override
    public void onClick(View v) {

    }

    private static class ViewHolder {
        TextView commentAuthor;
        TextView comment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView;
        Comments comment = getItem(position);
        CustomCommentAdapter.ViewHolder viewHolder;
        rowView = convertView;

        if (rowView == null) {
            viewHolder = new CustomCommentAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(R.layout.comment_row, parent, false);
            viewHolder.commentAuthor = (TextView) rowView.findViewById(R.id.comment_author);
            viewHolder.comment = (TextView) rowView.findViewById(R.id.comment);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (CustomCommentAdapter.ViewHolder) rowView.getTag();
        }

        viewHolder.commentAuthor.setText("От: " + comment.getUser().getEmail());
        viewHolder.comment.setText(comment.getComment());

        return rowView;
    }
}
