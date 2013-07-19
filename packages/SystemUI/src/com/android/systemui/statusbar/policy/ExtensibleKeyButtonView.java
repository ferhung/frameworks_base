/*
 * Copyright (C) 2012 Slimroms
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.statusbar.policy;

<<<<<<< HEAD
import java.net.URISyntaxException;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.ServiceManager;
=======
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
>>>>>>> fa119ce... Frameworks: refactor, cleanup, centralize, fixes, features
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.android.internal.util.slim.ButtonsConstants;
import com.android.internal.util.slim.SlimActions;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.KeyButtonView;


public class ExtensibleKeyButtonView extends KeyButtonView {

<<<<<<< HEAD
    final static String ACTION_HOME = "**home**";
    final static String ACTION_BACK = "**back**";
    final static String ACTION_SEARCH = "**search**";
    final static String ACTION_MENU = "**menu**";
    final static String ACTION_POWER = "**power**";
    final static String ACTION_NOTIFICATIONS = "**notifications**";
    final static String ACTION_QS = "**quicksettings**";
    final static String ACTION_RECENTS = "**recents**";
    final static String ACTION_SCREENSHOT = "**screenshot**";
    final static String ACTION_IME = "**ime**";
    final static String ACTION_KILL = "**kill**";
    final static String ACTION_WIDGETS = "**widgets**";
    final static String ACTION_NULL = "**null**";

=======
>>>>>>> fa119ce... Frameworks: refactor, cleanup, centralize, fixes, features
    private static final String TAG = "Key.Ext";

    private Context mContext;
    private Handler mHandler;

    private int mInjectKeycode;
    private String mClickAction, mLongpressAction;

    public ExtensibleKeyButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, ButtonsConstants.ACTION_NULL, ButtonsConstants.ACTION_NULL);
    }

    public ExtensibleKeyButtonView(Context context, AttributeSet attrs, String clickAction, String longpressAction) {
        super(context, attrs);

        mContext = context;
        mHandler = new Handler();
        mClickAction = clickAction;
        mLongpressAction = longpressAction;

        setCode(0);
        setSupportsLongPress(false);

        if (clickAction != null){
            if (clickAction.equals(ButtonsConstants.ACTION_HOME)) {
                setCode(KeyEvent.KEYCODE_HOME);
                setId(R.id.home);
            } else if (clickAction.equals(ButtonsConstants.ACTION_BACK)) {
                setCode(KeyEvent.KEYCODE_BACK);
                setId(R.id.back);
<<<<<<< HEAD
            } else if (ClickAction.equals(ACTION_SEARCH)) {
                setCode (KeyEvent.KEYCODE_SEARCH);
            } else if (ClickAction.equals(ACTION_MENU)) {
                setCode (KeyEvent.KEYCODE_MENU);
            } else if (ClickAction.equals(ACTION_POWER)) {
                setCode (KeyEvent.KEYCODE_POWER);
            } else { // the remaining options need to be handled by OnClick;
=======
            } else if (clickAction.equals(ButtonsConstants.ACTION_SEARCH)) {
                setCode(KeyEvent.KEYCODE_SEARCH);
            } else if (clickAction.equals(ButtonsConstants.ACTION_MENU)) {
                setCode(KeyEvent.KEYCODE_MENU);
            } else {
                // the remaining options need to be handled by OnClick
>>>>>>> fa119ce... Frameworks: refactor, cleanup, centralize, fixes, features
                setOnClickListener(mClickListener);
                if (clickAction.equals(ButtonsConstants.ACTION_RECENTS)) {
                    setId(R.id.recent_apps);
                }
            }
        }

        if (longpressAction != null)
            if ((!longpressAction.equals(ButtonsConstants.ACTION_NULL)) || (getCode() !=0)) {
                // I want to allow long presses for defined actions, or if
                // primary action is a 'key' and long press isn't defined otherwise
                setSupportsLongPress(true);
                setOnLongClickListener(mLongPressListener);
            }
    }

    public void injectKeyDelayed(int keycode){
        mInjectKeycode = keycode;
        mHandler.removeCallbacks(onInjectKey_Down);
        mHandler.removeCallbacks(onInjectKey_Up);
        mHandler.post(onInjectKey_Down);
        mHandler.postDelayed(onInjectKey_Up,10); // introduce small delay to handle key press
    }

    final Runnable onInjectKey_Down = new Runnable() {
        public void run() {
            final KeyEvent ev = new KeyEvent(mDownTime, SystemClock.uptimeMillis(), KeyEvent.ACTION_DOWN, mInjectKeycode, 0,
                    0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
                    KeyEvent.FLAG_FROM_SYSTEM | KeyEvent.FLAG_VIRTUAL_HARD_KEY,
                    InputDevice.SOURCE_KEYBOARD);
            InputManager.getInstance().injectInputEvent(ev,
                    InputManager.INJECT_INPUT_EVENT_MODE_ASYNC);
        }
    };

    final Runnable onInjectKey_Up = new Runnable() {
        public void run() {
            final KeyEvent ev = new KeyEvent(mDownTime, SystemClock.uptimeMillis(), KeyEvent.ACTION_UP, mInjectKeycode, 0,
                    0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
                    KeyEvent.FLAG_FROM_SYSTEM | KeyEvent.FLAG_VIRTUAL_HARD_KEY,
                    InputDevice.SOURCE_KEYBOARD);
            InputManager.getInstance().injectInputEvent(ev,
                    InputManager.INJECT_INPUT_EVENT_MODE_ASYNC);
        }
    };

<<<<<<< HEAD
    Runnable mKillTask = new Runnable() {
        public void run() {
            String packageName = mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
            mActivityManager.forceStopPackage(packageName);
        }
    };

=======
>>>>>>> fa119ce... Frameworks: refactor, cleanup, centralize, fixes, features
    private OnClickListener mClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // the other consts were handled by keycode.
<<<<<<< HEAD

            if (mClickAction.equals(ACTION_NULL)) {
                // who would set a button with no ClickAction?
                // Stranger things have happened.
                return;

            } else if (mClickAction.equals(ACTION_RECENTS)) {
                try {
                    mBarService.toggleRecentApps();
                } catch (RemoteException e) {
                }
                return;
            } else if (mClickAction.equals(ACTION_SCREENSHOT)) {
                takeScreenshot();
                return;
            } else if (mClickAction.equals(ACTION_NOTIFICATIONS)) {
                try {
                    mBarService.toggleNotificationShade();
                } catch (RemoteException e) {
                    // wtf is this
                }
                return;
            } else if (mClickAction.equals(ACTION_QS)) {
                try {
                    mBarService.toggleQSShade();
                } catch (RemoteException e) {
                    // wtf is this
                }
                return;
            } else if (mClickAction.equals(ACTION_IME)) {
                getContext().sendBroadcast(new Intent("android.settings.SHOW_INPUT_METHOD_PICKER"));
                return;
            } else if (mClickAction.equals(ACTION_KILL)) {

                mHandler.postDelayed(mKillTask,ViewConfiguration.getGlobalActionKeyTimeout());
                return;
<<<<<<< HEAD
=======
            } else if (mClickAction.equals(ACTION_WIDGETS)) {
                try {
                    mBarService.toggleWidgets();
                } catch (RemoteException e) {
                }
                return;
            } else if (mClickAction.equals(ACTION_POWER)) {
                PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
                pm.goToSleep(SystemClock.uptimeMillis());
>>>>>>> aae6265... Frameworks: Add Widgets viewpager for all devices (2/2)
            } else {  // we must have a custom uri
                 try {
                     Intent intent = Intent.parseUri(mClickAction, 0);
                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     getContext().startActivity(intent);
                 } catch (URISyntaxException e) {
                     Log.e(TAG, "URISyntaxException: [" + mClickAction + "]");
                 } catch (ActivityNotFoundException e){
                      Log.e(TAG, "ActivityNotFound: [" + mClickAction + "]");
                 }
            }
=======
            SlimActions.processAction(mContext, mClickAction);
>>>>>>> fa119ce... Frameworks: refactor, cleanup, centralize, fixes, features
            return;
        }
    };

    private OnLongClickListener mLongPressListener = new OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            if (mLongpressAction.equals(ButtonsConstants.ACTION_HOME)) {
                injectKeyDelayed(KeyEvent.KEYCODE_HOME);
                return true;
            } else if (mLongpressAction.equals(ButtonsConstants.ACTION_BACK)) {
                injectKeyDelayed(KeyEvent.KEYCODE_BACK);
                return true;
            } else if (mLongpressAction.equals(ButtonsConstants.ACTION_SEARCH)) {
                injectKeyDelayed(KeyEvent.KEYCODE_SEARCH);
                return true;
            } else if (mLongpressAction.equals(ButtonsConstants.ACTION_MENU)) {
                injectKeyDelayed(KeyEvent.KEYCODE_MENU);
                return true;
<<<<<<< HEAD
            } else if (mLongpress.equals(ACTION_POWER)) {
                injectKeyDelayed(KeyEvent.KEYCODE_POWER);
                return true;
            } else if (mLongpress.equals(ACTION_IME)) {
                getContext().sendBroadcast(new Intent("android.settings.SHOW_INPUT_METHOD_PICKER"));
                return true;
            } else if (mLongpress.equals(ACTION_KILL)) {
                mHandler.post(mKillTask);
                return true;
<<<<<<< HEAD
=======
            } else if (mLongpress.equals(ACTION_WIDGETS)) {
                try {
                    mBarService.toggleWidgets();
                } catch (RemoteException e) {
                }
                return true;
            } else if (mLongpress.equals(ACTION_LAST_APP)) {
                toggleLastApp();
                return true;
>>>>>>> aae6265... Frameworks: Add Widgets viewpager for all devices (2/2)
            } else if (mLongpress.equals(ACTION_RECENTS)) {
                try {
                    mBarService.toggleRecentApps();
                } catch (RemoteException e) {
                    // let it go.
                }
                return true;
            } else if (mLongpress.equals(ACTION_SCREENSHOT)) {
                takeScreenshot();
                return true;
            } else if (mClickAction.equals(ACTION_NOTIFICATIONS)) {
                try {
                    mBarService.toggleNotificationShade();
                } catch (RemoteException e) {
                    // wtf is this
                }
                return true;
            } else if (mLongpress.equals(ACTION_QS)) {
                try {
                    mBarService.toggleQSShade();
                } catch (RemoteException e) {
                    // wtf is this
                }
                return true;
            } else {  // we must have a custom uri
                    try {
                        Intent intent = Intent.parseUri(mLongpress, 0);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);
                    } catch (URISyntaxException e) {
                        Log.e(TAG, "URISyntaxException: [" + mLongpress + "]");
                    } catch (ActivityNotFoundException e){
                        Log.e(TAG, "ActivityNotFound: [" + mLongpress + "]");
                    }
                    return true;
            }
        }
    };

    final Runnable mScreenshotTimeout = new Runnable() {
        @Override
        public void run() {
            synchronized (mScreenshotLock) {
                if (mScreenshotConnection != null) {
                    mContext.unbindService(mScreenshotConnection);
                    mScreenshotConnection = null;
                }
            }
        }
    };

    private void takeScreenshot() {
        synchronized (mScreenshotLock) {
            if (mScreenshotConnection != null) {
                return;
            }
            ComponentName cn = new ComponentName("com.android.systemui",
                    "com.android.systemui.screenshot.TakeScreenshotService");
            Intent intent = new Intent();
            intent.setComponent(cn);
            ServiceConnection conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    synchronized (mScreenshotLock) {
                        if (mScreenshotConnection != this) {
                            return;
                        }
                        Messenger messenger = new Messenger(service);
                        Message msg = Message.obtain(null, 1);
                        final ServiceConnection myConn = this;
                        Handler h = new Handler(H.getLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                synchronized (mScreenshotLock) {
                                    if (mScreenshotConnection == myConn) {
                                        mContext.unbindService(mScreenshotConnection);
                                        mScreenshotConnection = null;
                                        H.removeCallbacks(mScreenshotTimeout);
                                    }
                                }
                            }
                        };
                        msg.replyTo = new Messenger(h);
                        msg.arg1 = msg.arg2 = 0;

                        /*
                         * remove for the time being if (mStatusBar != null &&
                         * mStatusBar.isVisibleLw()) msg.arg1 = 1; if
                         * (mNavigationBar != null &&
                         * mNavigationBar.isVisibleLw()) msg.arg2 = 1;
                         */

                        /* wait for the dialog box to close */
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ie) {
                        }

                        /* take the screenshot */
                        try {
                            messenger.send(msg);
                        } catch (RemoteException e) {
                        }
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                }
            };
            if (mContext.bindService(intent, conn, mContext.BIND_AUTO_CREATE)) {
                mScreenshotConnection = conn;
                H.postDelayed(mScreenshotTimeout, 10000);
            }
        }
    }

    private Handler H = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

=======
            } else {
                SlimActions.processAction(mContext, mLongpressAction);
                return true;
>>>>>>> fa119ce... Frameworks: refactor, cleanup, centralize, fixes, features
            }
        }
    };

}
