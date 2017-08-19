package view.star.com.hellokotlin;


import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.MenuRes;
import android.support.v4.content.ContextCompat;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ActionModeHandler<T> {
    private List<T> mPendingItems;
    private int mMenuId;
    private Callback<T> mCallback;
    private ActionMode mActionMode;
    private Activity mContext;
    private ActionMode.Callback mActionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(mMenuId, menu);
            Drawable drawable = menu.findItem(R.id.action_delete).getIcon();
            if (drawable != null) {
                // If we don't mutate the drawable, then all drawable's with this id will have a color
                // filter applied to it.
                drawable.mutate();
                drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                drawable.setAlpha(255);
            }
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return mCallback.onAction(item.getItemId(), mPendingItems);
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mContext.getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            }
            mCallback.onDestroy(mPendingItems);
            mActionMode = null;
            mPendingItems = null;
        }
    };

    public ActionModeHandler(Activity activity, Callback<T> callback, @MenuRes int menuId) {
        mContext = activity;
        mMenuId = menuId;
        mCallback = callback;
    }

    public List<T> getPendingItems() {
        return mPendingItems;
    }

    /**
     *
     * @param item
     * @return true if the item is in pending list, false for others.
     */
    public boolean chooseItem(T item) {
        if (isActionMode()) {
            if (mPendingItems.contains(item)) {
                mPendingItems.remove(item);
                return false;
            } else {
                mPendingItems.add(item);
                return true;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mContext.getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
            }
            mActionMode = mContext.startActionMode(mActionCallback);
            mPendingItems = new ArrayList<>();
            mPendingItems.add(item);
            return true;
        }
    }

    public void dismiss() {
        mActionMode.finish();
    }

    public boolean isActionMode() {
        return mActionMode != null;
    }

    public interface Callback<T> {
        boolean onAction(int actionId, List<T> pendingItems);
        void onDestroy(List<T> pendingItems);
    }
}
