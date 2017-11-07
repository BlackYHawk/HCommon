package com.hawk.base.ui.widget.layoutmanager;

import android.support.v7.widget.RecyclerView;

import com.hawk.base.ui.adapter.BaseRecyclerAdapter;


public interface ILayoutManager {

    RecyclerView.LayoutManager getLayoutManager();

    int findLastVisiblePosition();

    void setUpAdapter(BaseRecyclerAdapter adapter);

}
