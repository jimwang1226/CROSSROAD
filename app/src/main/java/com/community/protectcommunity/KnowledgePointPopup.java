package com.community.protectcommunity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import razerdp.basepopup.BasePopupWindow;

public class KnowledgePointPopup extends BasePopupWindow{
    private View popupWindow;
    public KnowledgePointPopup(Context context) {
        super(context);
    }

    @Override
    public View onCreateContentView() {
        popupWindow = createPopupById(R.layout.pop_up_window_knowledge_point);
        return popupWindow;
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation(true);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }

    //close the pop up window
    public void dismissPopupLayout() {
        popupWindow = null;
        this.dismiss();
        System.gc();
    }
}
