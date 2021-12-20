package com.example.androidproject.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.androidproject.DAL.UserSQLiteHelper;
import com.example.androidproject.Entity.Review;
import com.example.androidproject.Entity.User;
import com.example.androidproject.R;

import java.util.List;

public class ReviewAdapter extends BaseAdapter {

    private Activity activity;
    private List<Review> list;
    private int layout;

    public ReviewAdapter(Activity activity, int layout, List<Review> list) {
        this.activity = activity;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView avatar;
        RatingBar ratingBar;
        TextView username;
        TextView review;

        if (convertView == null){
            convertView = activity.getLayoutInflater().inflate(layout, null);
            avatar = convertView.findViewById(R.id.imgAvatar);
            ratingBar = convertView.findViewById(R.id.rtbProductRating);
            username = convertView.findViewById(R.id.lblUserName);
            review = convertView.findViewById(R.id.lblComment);
            convertView.setTag(R.id.imgAvatar, avatar);
            convertView.setTag(R.id.rtbProductRating, ratingBar);
            convertView.setTag(R.id.lblUserName, username);
            convertView.setTag(R.id.lblComment, review);
        }
        else {
            avatar = (ImageView) convertView.getTag(R.id.imgAvatar);
            ratingBar = (RatingBar) convertView.getTag(R.id.rtbProductRating);
            username = (TextView) convertView.getTag(R.id.lblUserName);
            review = (TextView) convertView.getTag(R.id.lblComment);
        }

        final Review comment = list.get(position);
        username.setText(comment.getUsername());
        review.setText(comment.getComment());
        ratingBar.setRating(comment.getRating());
        UserSQLiteHelper userSQLiteHelper = new UserSQLiteHelper(activity.getApplicationContext());
        User user = userSQLiteHelper.getUserByUsername(comment.getUsername());
        userSQLiteHelper.close();
        byte[] userAvatar = user.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(userAvatar, 0, userAvatar.length);
        avatar.setImageBitmap(bitmap);
        return convertView;
    }
}
