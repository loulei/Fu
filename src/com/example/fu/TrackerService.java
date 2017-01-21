package com.example.fu;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;


public class TrackerService extends BaseAccessibilityService {
    public static final String TAG = "TrackerService";
    public static final String COMMAND = "COMMAND";
    public static final String COMMAND_OPEN = "COMMAND_OPEN";
    public static final String COMMAND_CLOSE = "COMMAND_CLOSE";
    
    private ScheduledExecutorService threadPool;
    private CheckRetry checkRetry;
    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        checkRetry = new CheckRetry();
        threadPool = Executors.newSingleThreadScheduledExecutor();
        threadPool.scheduleAtFixedRate(checkRetry, 1, 1, TimeUnit.SECONDS);
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("NewApi")
	@Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
        	String className = event.getClassName().toString();
        	String packageName = event.getPackageName().toString();
        	Log.d(TAG, className);
        	Log.d(TAG, packageName);
        	if("com.eg.android.AlipayGphone".equals(packageName) && "android.app.Dialog".equals(className)){
    			try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			AccessibilityNodeInfo nodeInfo = findViewByText2("再来一次");
    			if(nodeInfo != null){
    				performViewClick(nodeInfo);
    			}else{
    				Log.d(TAG, "nodeInfo null");
    			}
        	}
        	if("com.alipay.mobile.scan.as.main.MainCaptureActivity".equals(className) && "com.eg.android.AlipayGphone".equals(packageName)){
        		threadPool = Executors.newSingleThreadScheduledExecutor();
        		threadPool.scheduleAtFixedRate(checkRetry, 1, 1, TimeUnit.SECONDS);
        	}else{
        		if(threadPool != null){
        			threadPool.shutdown();
        		}
        	}
        }
    }
    
    public class CheckRetry implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			AccessibilityNodeInfo nodeInfo = findViewByText2("点击重试");
			if(nodeInfo != null){
				performViewClick(nodeInfo);
			}else{
				Log.d(TAG, "nodeInfo null");
			}
		}
    	
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


	@Override
	public void onInterrupt() {
		// TODO Auto-generated method stub
		
	}

}
//01-20 10:59:18.983: I/System.out(15965): com.alipay.mobile.scan.as.main.MainCaptureActivity
//01-20 11:08:48.425: D/TrackerService(21656): android.app.Dialog
//01-20 11:08:57.811: D/TrackerService(21656): com.eg.android.AlipayGphone

