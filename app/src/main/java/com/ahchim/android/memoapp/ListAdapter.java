package com.ahchim.android.memoapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ahchim.android.memoapp.domain.Memo;
import com.ahchim.android.memoapp.interfaces.ListInterface;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ahchim on 2017-02-14.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder>{
    private ListInterface listInterface = null;

    private List<Memo> datas;
    private Context context;


    public ListAdapter(Context context, List<Memo> datas) {
        this.context = context;
        this.datas = datas;
        this.listInterface = (ListInterface) context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Memo memo = datas.get(position);

        // content를 한 줄 단위로 나눔
        String[] contentSplit = memo.getContent().split("\\n");
        // content의 첫 한줄을 제목으로 지정함.
        String title = contentSplit[0];

        holder.txtTitle.setText(title);
        holder.txtDate.setText(memo.getEditdate() + "");

        // 첫줄 다음 줄을 미리보기 콘텐츠로 지정.
        if(contentSplit.length > 1){
            holder.txtContent.setText(contentSplit[1]);
        }

        holder.position = position;

        // 첫줄을 제외한 나머지를 콘텐츠로 지정.
        //holder.txtContent.setText(memo.getContent().replaceAll(title + "\n", ""));

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.cardView.setAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtContent, txtDate;
        CardView cardView;

        int position;

        public Holder(View view) {
            super(view);

            this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            this.txtContent = (TextView) view.findViewById(R.id.txtContent);
            this.txtDate = (TextView) view.findViewById(R.id.txtDate);

            this.cardView = (CardView) view.findViewById(R.id.cardView);

            this.txtTitle.setOnTouchListener(touchlistener);
            this.txtContent.setOnTouchListener(touchlistener);
            this.txtDate.setOnTouchListener(touchlistener);
            this.cardView.setOnClickListener(clicklistener);
        }

        View.OnTouchListener touchlistener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return false;
            }
        };

        View.OnClickListener clicklistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메인의 goWrite 함수를 호출한다.
                if(listInterface != null) {
                    listInterface.goWrite(position);
                }
            }
        };

        GestureDetectorCompat mDetector = new GestureDetectorCompat(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // 메인의 goWrite 함수를 호출한다.
                if(listInterface != null) {
                    listInterface.goWrite(position);
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }
}
