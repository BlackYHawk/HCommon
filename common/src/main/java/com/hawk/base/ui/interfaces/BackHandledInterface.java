package com.hawk.base.ui.interfaces;

import com.hawk.base.ui.fragment.BaseMvpFragment;
import com.hawk.mvp.ui.presenter.BasePresenter;
import com.hawk.mvp.ui.view.BaseView;

/**
 * Created by lan on 2017-10-23.
 */

public interface BackHandledInterface<V extends BaseView<VC>, VC, P extends BasePresenter<V, VC>> {

    void setSelectedFragment(BaseMvpFragment<V, VC, P> backHandledFragment);

}
