package com.example.efficientpomodoro;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//导入第三方图片加载库
import com.bumptech.glide.Glide;

import java.util.List;

//自定义适配器
public class PomodoroAdapter extends RecyclerView.Adapter<PomodoroAdapter.ViewHolder> {

    private Context mContext;
    private List<Pomodoro> mPomodoroList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView pomodoroImage;
        TextView pomodoroName;

        //创建ViewHolder的意义
        //提高运行效率,避免重复使用findViewById
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view;
            pomodoroImage = (ImageView)view.findViewById(R.id.background_image);
            pomodoroName = (TextView)view.findViewById(R.id.clock_name);
        }
    }

    public PomodoroAdapter(List<Pomodoro> mpomodoroList) {
        mPomodoroList = mpomodoroList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        //将每一个子项转换为可视View
        View view = LayoutInflater.from(mContext).inflate(R.layout.pomodoro, parent, false);
        //我们最初的写法是在这里绑定布局,设置内容
        //但现在我们在返回时调用ViewHolder()来完成以前的操作
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Pomodoro pomodoro = mPomodoroList.get(position);
                Intent intent = new Intent(mContext, SettingsActivity.class);
                intent.putExtra(SettingsActivity.Clock_Name, pomodoro.getName());
                intent.putExtra(SettingsActivity.Clock_Image_Id, pomodoro.getImageId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pomodoro pomodoro = mPomodoroList.get(position);
        holder.pomodoroName.setText(pomodoro.getName());
        //使用Glide来加载图片
        Glide.with(mContext).load(pomodoro.getImageId()).into(holder.pomodoroImage);
    }

    @Override
    public int getItemCount() {
        return mPomodoroList.size();
    }
}
