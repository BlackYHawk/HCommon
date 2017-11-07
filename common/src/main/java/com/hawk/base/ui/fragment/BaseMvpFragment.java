package com.hawk.base.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hawk.base.R;
import com.hawk.base.ui.activity.BaseMvpActivity;
import com.hawk.base.ui.activity.StartActivityDelegate;
import com.hawk.base.ui.interfaces.BackHandledInterface;
import com.hawk.mvp.ui.fragment.MvpFragment;
import com.hawk.mvp.ui.presenter.BasePresenter;
import com.hawk.mvp.ui.view.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class BaseMvpFragment<V extends BaseView<VC>, VC, P extends BasePresenter<V, VC>>
        extends MvpFragment<V, VC, P> implements TransactionCommitter {
    private final SupportFragmentTransactionDelegate mSupportFragmentTransactionDelegate
            = new SupportFragmentTransactionDelegate();
    protected BackHandledInterface<V, VC, P> mBackHandledInterface;
    private Unbinder mUnBinder;
    private Toolbar mToolbar;
    private TextView mTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if((getActivity() instanceof BackHandledInterface)){
            this.mBackHandledInterface = (BackHandledInterface)getActivity();
        }
        bindData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(shouldHaveOptionsMenu());
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTitle = (TextView) view.findViewById(R.id.tvTitle);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        bindView(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbindView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSupportFragmentTransactionDelegate.onResumed();
    }

    @Override
    public void onStart() {
        super.onStart();
        //告诉FragmentActivity，当前Fragment在栈顶
        if (mBackHandledInterface != null) {
            mBackHandledInterface.setSelectedFragment(this);
        }
    }

    @Override
    public void onStop() {
        if (mBackHandledInterface != null) {
            mBackHandledInterface.setSelectedFragment(null);
            mBackHandledInterface = null;
        }
        super.onStop();
    }

    @Override
    public boolean isCommitterResumed() {
        return isResumed();
    }

    protected boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

    protected final boolean startActivityForResultSafely(final Intent intent, final int code) {
        return StartActivityDelegate.startActivityForResultSafely(this, intent, code);
    }

    protected boolean safeCommit(@NonNull FragmentTransaction transaction) {
        return mSupportFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    protected abstract int getLayoutRes();

    protected boolean shouldHaveOptionsMenu() {
        return false;
    }

    protected boolean autoBindViews() {
        return true;
    }

    @CallSuper
    protected void bindView(final View rootView) {
        if (autoBindViews()) {
            mUnBinder = ButterKnife.bind(this, rootView);
        }
    }

    protected void bindData(){}

    protected void unbindView() {
        if (autoBindViews() && mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    @Override
    public boolean isModal() {
        return false;
    }

    public boolean onBackPressed() {
        return false;
    }

    protected final void setSupportActionBar(Toolbar toolbar) {
        ((BaseMvpActivity) getActivity()).setSupportActionBar(toolbar);
    }

}
