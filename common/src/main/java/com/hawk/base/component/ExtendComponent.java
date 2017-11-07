package com.hawk.base.component;


import com.hawk.base.ui.presenter.ExtendPresenter;
import com.hawk.mvp.component.BaseComponent;
import com.hawk.mvp.rx.BaseRxPresenter;

/**
 * Created by heyong on 2016/11/25.
 */

public interface ExtendComponent<P extends BaseRxPresenter<ExtendPresenter.ExtendView, ExtendPresenter.ExtendCallbacks>>
        extends BaseComponent<ExtendPresenter.ExtendView, ExtendPresenter.ExtendCallbacks, P> {

}
