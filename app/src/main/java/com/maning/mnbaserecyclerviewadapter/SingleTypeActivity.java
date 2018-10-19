package com.maning.mnbaserecyclerviewadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.maning.library_base_adapter.BaseRecyclerViewAdapter;
import com.maning.mnbaserecyclerviewadapter.model.TestModel;

import java.util.ArrayList;
import java.util.List;

public class SingleTypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;

    private List<TestModel> datas = new ArrayList<>();
    private MyAdapter myAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_type);

        initViews();

        initData();

        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);


        //添加头部
        View headerView = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        View footerView = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
        myAdapter.addHeaderView(headerView);
        myAdapter.addFooterView(footerView);

    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            datas.add(new TestModel("描述：" + i));
        }
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("单类型例子");
        //设置toolbar
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_01:
                recyclerView.setLayoutManager(linearLayoutManager);
                break;
            case R.id.action_02:
                recyclerView.setLayoutManager(gridLayoutManager);
                myAdapter.onAttachedToRecyclerView(recyclerView);
                break;
            case R.id.action_03:
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    private class MyAdapter extends BaseRecyclerViewAdapter {

        @Override
        protected void bindData(BaseViewHolder holder, final int position, int viewType) {
            TextView tvDes = holder.getView(R.id.tv_des);
            tvDes.setText(datas.get(position).getDes());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("点击了：" + position);
                }
            });
        }

        @Override
        protected int getLayoutId(int viewType) {
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
