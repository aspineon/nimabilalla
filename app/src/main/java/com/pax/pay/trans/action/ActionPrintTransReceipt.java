/*
 * ============================================================================
 * COPYRIGHT
 *              Pax CORPORATION PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or
 *   nondisclosure agreement with Pax Corporation and may not be copied
 *   or disclosed except in accordance with the terms in that agreement.
 *      Copyright (C) 2016 - ? Pax Corporation. All rights reserved.
 * Module Date: 2016-11-25
 * Module Author: Steven.W
 * Description:
 *
 * ============================================================================
 */
package com.pax.pay.trans.action;

import android.content.Context;

import com.pax.abl.core.AAction;
import com.pax.abl.core.ActionResult;
import com.pax.pay.app.FinancialApplication;
import com.pax.pay.trans.TransResult;
import com.pax.pay.trans.model.TransData;
import com.pax.pay.trans.receipt.PrintListenerImpl;
import com.pax.pay.trans.receipt.ReceiptPrintTrans;
import com.pax.pay.utils.ContextUtils;

public class ActionPrintTransReceipt extends AAction {

    private TransData transData;

    public ActionPrintTransReceipt(ActionStartListener listener) {
        super(listener);
    }

    private ActionPrintTransReceipt(ActionStartListener listener, TransData transData) {
        super(listener);
        this.transData = transData;
    }

    public void setTransData(TransData transData) {
        this.transData = transData;
    }

    @Override
    protected void process() {

        FinancialApplication.mApp.runInBackground(new Runnable() {

            @Override
            public void run() {
                Context context = ContextUtils.getActyContext();
                ReceiptPrintTrans receiptPrintTrans = ReceiptPrintTrans.getInstance();
                PrintListenerImpl listener = new PrintListenerImpl(context);
                receiptPrintTrans.print(transData, false, listener);
                setResult(new ActionResult(TransResult.SUCC, transData));
            }
        });
    }

    public static class Builder {

        private ActionStartListener startListener;
        private TransData transData;

        public Builder startListener(ActionStartListener startListener) {
            this.startListener = startListener;
            return this;
        }

        public Builder transData(TransData transData) {
            this.transData = transData;
            return this;
        }

        public ActionPrintTransReceipt create() {
            return new ActionPrintTransReceipt(startListener, transData);
        }
    }
}
