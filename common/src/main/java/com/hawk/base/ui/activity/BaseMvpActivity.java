package com.hawk.base.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hawk.base.R;
import com.hawk.base.module.ActModule;
import com.hawk.base.ui.fragment.BaseMvpFragment;
import com.hawk.base.ui.fragment.SupportFragmentTransactionDelegate;
import com.hawk.base.ui.fragment.TransactionCommitter;
import com.hawk.base.ui.interfaces.BackHandledInterface;
import com.hawk.mvp.component.BaseComponent;
import com.hawk.mvp.ui.activity.MvpDiActivity;
import com.hawk.mvp.ui.presenter.BasePresenter;
import com.hawk.mvp.ui.view.BaseView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class BaseMvpActivity<V extends BaseView<VC>, VC, P extends BasePresenter<V, VC>,
        C extends BaseComponent<V, VC, P>> extends MvpDiActivity<V, VC, P, C> implements TransactionCommitter,
        BackHandledInterface<V, VC, P> {
    private final SupportFragmentTransactionDelegate mSupportFragmentTransactionDelegate
            = new SupportFragmentTransactionDelegate();
    private volatile boolean mIsResumed;
    private BaseMvpFragment<V, VC, P> mBackHandedFragment;
    private boolean hadIntercept;
    private Unbinder mUnBinder;
    private Toolbar mToolbar;
    private TextView mTitle;

    private static final TypedValue sTypedValue = new TypedValue();
    private int mColorPrimaryDark;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        // inject argument first
        super.onCreate(savedInstanceState);
        mIsResumed = true;
        setContentView(getLayoutRes());
        bindData();
        bindView();
        initSystemBarTint();
    }

    @TargetApi(19)
    private void initSystemBarTint(){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
            SystemBarTintManager tintManager = null;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);

            getTheme().resolveAttribute(R.attr.colorPrimaryDark, sTypedValue, true);
            mColorPrimaryDark = sTypedValue.data;

            tintManager.setStatusBarTintColor(ContextCompat.getColor(this, mColorPrimaryDark));
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsResumed = false;
    }

    protected boolean safeCommit(@NonNull final FragmentTransaction transaction) {
        return mSupportFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    protected void setDisplayBack() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setTitle(String text) {
        if(mTitle != null) {
            mTitle.setText(text);
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        mIsResumed = true;
        mSupportFragmentTransactionDelegate.onResumed();
    }

    @Override
    public boolean isCommitterResumed() {
        return mIsResumed;
    }

    @Override
    public void setSelectedFragment(BaseMvpFragment<V, VC, P> backHandledFragment) {
        this.mBackHandedFragment = backHandledFragment;
    }

    @Override
    public void onBackPressed() {
        if(mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()){
            if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                super.onBackPressed();
            }else{
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindView();
    }

    protected boolean autoBindViews() {
        return true;
    }

    @CallSuper
    protected void bindView() {
        if (autoBindViews()) {
            mUnBinder = ButterKnife.bind(this);
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.tvTitle);

        if (mToolbar != null) {
            if (display != null) {
                display.setSupportActionBar(mToolbar);
            } else {
                setSupportActionBar(mToolbar);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                }
            }
        }
    }

    protected void unbindView() {
        if (autoBindViews() && mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    protected void bindData() {}

    protected abstract int getLayoutRes();

    public final boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent, provideOptionsBundle(null));
    }

    public final boolean startActivitySafely(final Intent intent, Pair<View, String>[] pairs) {
        return StartActivityDelegate.startActivitySafely(this, intent, provideOptionsBundle(pairs));
    }

    public final void finishTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.finishAfterTransition();
        }
        else {
            this.finish();
        }
    }

    protected ActModule getActModule() {
        return new ActModule(this);
    }

    private Bundle provideOptionsBundle(Pair<View, String>[] pairs) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs).toBundle();
    }

}
