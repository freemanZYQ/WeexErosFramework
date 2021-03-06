package com.eros.framework.event.modal;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.eros.framework.R;
import com.eros.framework.manager.ManagerFactory;
import com.eros.framework.manager.impl.ModalManager;
import com.eros.framework.manager.impl.ParseManager;
import com.eros.framework.model.ModalBean;
import com.eros.framework.model.WeexEventBean;
import com.eros.wxbase.EventGate;
import com.taobao.weex.bridge.JSCallback;

/**
 * Created by Carry on 2017/8/23.
 */

public class EventAlert extends EventGate {

    @Override
    public void perform(Context context, WeexEventBean weexEventBean) {
        String params = weexEventBean.getJsParams();
        if (TextUtils.isEmpty(params)) return;
        alert(params, weexEventBean.getJscallback(), context);
    }

    public void alert(String options, final JSCallback callback, Context Context) {
        ParseManager parseManager = ManagerFactory.getManagerService(ParseManager.class);
        ModalBean bean = parseManager.parseObject(options, ModalBean.class);
        String okTitle = bean.getOkTitle();
        if (TextUtils.isEmpty(okTitle)) {
            okTitle = Context.getResources().getString(R.string.str_ensure);
        }
        ModalManager.BmAlert.showAlert(Context, bean.getTitle(), bean
                .getMessage(), okTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.invoke(null);
                }
            }
        }, null, null, bean.getTitleAlign(), bean.getMessageAlign());
    }
}
