package com.maning.mnbaserecyclerviewadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.maning.library_base_adapter.BaseRecyclerViewAdapter;
import com.maning.mnbaserecyclerviewadapter.model.TestModel;

import java.util.ArrayList;
import java.util.List;

public class MuchTypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<TestModel> datas = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_type);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        for (int i = 0; i < 20; i++) {
            datas.add(new TestModel("描述：" + i));
        }
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);


        //添加头部
        View headerView = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        View footerView = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
        myAdapter.addHeaderView(headerView);
        myAdapter.addFooterView(footerView);
    }

    private class MyAdapter extends BaseRecyclerViewAdapter {

        @Override
        protected void bindData(BaseViewHolder holder, final int position, int viewType) {
            if(viewType == 0){
                TextView tvDes2 = holder.getView(R.id.tv_des2);
                tvDes2.setText("我是其它");
            }else{
                TextView tvDes = holder.getView(R.id.tv_des);
                tvDes.setText(datas.get(position).getDes());
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("点击了：" + position);
                }
            });
        }

        @Override
        public int bindItemViewType(int position) {
            if (position == 0 || position == 3) {
                return 0;
            }
            return 1;
        }

        @Override
        protected int getLayoutId(int viewType) {
            if (viewType == 0) {
                return R.layout.item_02;
            }
            return R.layout.item_01;
        }

        @Override
        protected int getTotalItemCount() {
            return datas.size();
        }

    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
