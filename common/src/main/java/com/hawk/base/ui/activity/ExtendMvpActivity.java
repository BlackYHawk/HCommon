package com.hawk.base.ui.activity;

import android.os.Bundle;

import com.hawk.base.ui.fragment.TransactionCommitter;
import com.hawk.base.ui.presenter.ExtendPresenter;
import com.hawk.mvp.component.BaseComponent;
import com.hawk.mvp.rx.BaseRxPresenter;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class ExtendMvpActivity<P extends BaseRxPresenter<ExtendPresenter.ExtendView, ExtendPresenter.ExtendCallbacks>,
        C extends BaseComponent<ExtendPresenter.ExtendView, ExtendPresenter.ExtendCallbacks, P>>
        extends BaseMvpActivity<ExtendPresenter.ExtendView, ExtendPresenter.ExtendCallbacks, P, C>
        implements ExtendPresenter.ExtendCallbacks, TransactionCommitter {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        // inject argument first
        super.onCreate(savedInstanceState);
        if(mPresenter instanceof ExtendPresenter) {
            ((ExtendPresenter)mPresenter).setHostCallbacks(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter instanceof ExtendPresenter) {
            ((ExtendPresenter)mPresenter).setHostCallbacks(null);
        }
    }

}
