package com.ahchim.android.memoapp;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ahchim.android.memoapp.domain.Memo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ahchim on 2017-02-14.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder>{
    private List<Memo> datas;
    private Context context;

    public ListAdapter(Context context, List<Memo> datas) {
        this.context = context;
        this.datas = datas;
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
        if(contentSplit.length>1){
            holder.txtContent.setText(contentSplit[1]);
        }

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

        public Holder(View view) {
            super(view);

            this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            this.txtContent = (TextView) view.findViewById(R.id.txtContent);
            this.txtDate = (TextView) view.findViewById(R.id.txtDate);

            this.cardView = (CardView) view.findViewById(R.id.cardView);

            this.cardView.setOnClickListener(listener);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteFragment writeFrag = WriteFragment.newInstance();
                if(writeFrag != null){
                    goWriteFrag(writeFrag);
                }
            }
        };

        private void goWriteFrag(WriteFragment writeFrag) {
            if (context == null)
                return;
            if (context instanceof MainActivity) {
                MainActivity mainAct = (MainActivity) context;

                FragmentManager manager = mainAct.getSupportFragmentManager();
                FragmentTransaction fragTrans = manager.beginTransaction();
                fragTrans.add(R.id.activity_main, writeFrag);
                //fragTrans.replace(R.id.activity_main, writeFrag);
                fragTrans.addToBackStack(null);
                fragTrans.commit();
            }
        }

    }
}
