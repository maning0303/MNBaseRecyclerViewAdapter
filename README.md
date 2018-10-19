# MNBaseRecyclerViewAdapter
抽取BaseRecyclerViewAdapter，简化代码，支持添加头尾布局。
[![](https://jitpack.io/v/maning0303/MNBaseRecyclerViewAdapter.svg)](https://jitpack.io/#maning0303/MNBaseRecyclerViewAdapter)

## 如何添加
### 1:Gradle添加：
#### 1.在Project的build.gradle中添加仓库地址

``` gradle
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

#### 2.在app目录下的build.gradle中添加依赖
``` gradle
	dependencies {
	     compile 'com.github.maning0303:MNBaseRecyclerViewAdapter:V1.0.0'
	}
```

### 2:源码Module添加：
#### 直接关联 library-base-adapter

``` gradle

	compile project(':library-base-adapter')

```


## 代码使用（详情Demo）：
#### 1.继承BaseRecyclerViewAdapter
``` java

    private class MyAdapter extends BaseRecyclerViewAdapter {
            
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
        
        //Item数量
        @Override
        protected int getTotalItemCount() {
            return datas.size();
        }

    }
    
    
    //添加头尾布局
    View headerView = LayoutInflater.from(this).inflate(R.layout.item_header, null);
    View footerView = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
    myAdapter.addHeaderView(headerView);
    myAdapter.addFooterView(footerView);


``` 

