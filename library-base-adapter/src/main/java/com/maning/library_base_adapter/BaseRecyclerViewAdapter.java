package com.maning.library_base_adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

/**
 * <pre>
 *     author : maning
 *     e-mail : xxx@xx
 *     time   : 2018/04/19
 *     desc   : 抽取RecyclerViewAdapter，简化使用方式，提供添加头部和尾部的方法
 *     version: 1.0
 * </pre>
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {
    //数据源
    private List<T> mDatas;
    //header footer
    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;
    //标记是Header还是Footer
    private static final int TYPE_HEADER = 10000;  //说明是带有Header的
    private static final int TYPE_FOOTER = 20000;  //说明是带有Footer的
    private static final int TYPE_NORMAL = 30000;  //正常的Item

    //构造方法
    public BaseRecyclerViewAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //添加头布局
        if (viewType == TYPE_HEADER) {
            return new BaseViewHolder(mHeaderLayout);
        }
        //添加尾布局
        if (viewType == TYPE_FOOTER) {
            return new BaseViewHolder(mFooterLayout);
        }
        //其他Item
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, int position) {
        if (position >= getHeaderViewCount() && position < getItemCount() - getFooterViewCount()) {
            int newPosition = position - getHeaderViewCount();
            bindData(holder, newPosition, getItemViewType(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? getHeaderViewCount() + getFooterViewCount() : mDatas.size() + getHeaderViewCount() + getFooterViewCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return TYPE_HEADER;
        }
        if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        int newPosition = position - getHeaderViewCount();
        return bindItemViewType(newPosition);
    }

    /**
     * 添加头部和尾部布局适配GridLayoutManager
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {   // 布局是GridLayoutManager所管理
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果是Header、Footer的对象则占据spanCount的位置，否则就只占用1个位置
                    return (isHeader(position) || isFooter(position)) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 添加头部和尾部布局适配 StaggeredGridLayoutManager
     */
    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            // 如果是Header、Footer的对象则占据一行
            if (isHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition())) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                p.setFullSpan(true);
            }
        }
    }

    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 是不是头布局
     *
     * @param position
     * @return
     */
    private boolean isHeader(int position) {
        if (position < getHeaderViewCount()) {
            return true;
        }
        return false;
    }

    /**
     * 是不是尾部局
     *
     * @param position
     * @return
     */
    private boolean isFooter(int position) {
        if (position >= getItemCount() - getFooterViewCount()) {
            return true;
        }
        return false;
    }

    /**
     * 实现多种类型布局需要实现这个方法
     *
     * @param position
     * @return
     */
    public int bindItemViewType(int position) {
        return TYPE_NORMAL;
    }


    /**
     * 绑定数据
     *
     * @param holder   具体的viewHolder
     * @param position 对应的索引
     */
    protected abstract void bindData(BaseViewHolder holder, int position, int viewType);


    /**
     * 获取子item
     *
     * @return
     */
    protected abstract int getLayoutId(int viewType);


    /**
     * 刷新数据
     *
     * @param datas
     */
    public void refreshDatas(List<T> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }


    /**
     * 添加数据
     *
     * @param datas
     */
    public void addDatas(List<T> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 获取头部数量
     *
     * @return
     */
    public int getHeaderViewCount() {
        if (mHeaderLayout != null && mHeaderLayout.getChildCount() > 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 获取尾部数量
     *
     * @return
     */
    public int getFooterViewCount() {
        if (mFooterLayout != null && mFooterLayout.getChildCount() > 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 添加头部布局
     *
     * @param header
     */
    public void addHeaderView(View header) {
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayout(header.getContext());
            mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
            mHeaderLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        mHeaderLayout.addView(header);
        notifyDataSetChanged();
    }

    /**
     * 删除头部布局
     *
     * @param position 0表示最上面的依次向下排列
     */
    public void removeHeaderView(int position) {
        mHeaderLayout.removeViewAt(position);
        notifyDataSetChanged();
    }

    /**
     * 添加尾部布局
     *
     * @param footer
     */
    public void addFooterView(View footer) {
        if (mFooterLayout == null) {
            mFooterLayout = new LinearLayout(footer.getContext());
            mFooterLayout.setOrientation(LinearLayout.VERTICAL);
            mFooterLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        mFooterLayout.addView(footer);
        notifyDataSetChanged();
    }

    /**
     * 删除尾部布局
     *
     * @param position 0表示最上面的依次向下排列
     */
    public void removeFooterView(int position) {
        mFooterLayout.removeViewAt(position);
        notifyDataSetChanged();
    }

    /**
     * 封装ViewHolder ,子类可以直接使用
     */
    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> views;

        public BaseViewHolder(View itemView) {
            super(itemView);
            this.views = new SparseArray<>();
        }

        /**
         * 获取设置的view
         *
         * @param viewId
         * @return
         */
        public <K extends View> K getView(int viewId) {
            View view = views.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (K) view;
        }
    }

}
