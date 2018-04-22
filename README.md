# MNBaseRecyclerViewAdapter
抽取BaseRecyclerViewAdapter，简化代码，支持添加头尾布局。

## 使用步骤：
### 代码使用：
#### 1.继承BaseRecyclerViewAdapter
``` java

    private class MyAdapter extends BaseRecyclerViewAdapter<T> {
        //构造方法，传入数据源
        public MyAdapter(List<T> datas) {
            super(datas);
        }
            
        //数据绑定
        @Override
        protected void bindData(BaseViewHolder holder, final int position, int viewType) {
            //通过ID获取View
            TextView tvDes = holder.getView(R.id.tv_des);
        }

        //Item布局ID
        @Override
        protected int getLayoutId(int viewType) {
            return R.layout.item_01;
        }

    }
    
    
    //添加头尾布局
    View headerView = LayoutInflater.from(this).inflate(R.layout.item_header, null);
    View footerView = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
    myAdapter.addHeaderView(headerView);
    myAdapter.addFooterView(footerView);


``` 

