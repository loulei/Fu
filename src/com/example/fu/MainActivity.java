package com.example.fu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.provider.Settings;

public class MainActivity extends Activity {
	
	private Button btn_start;
	private Button btn_stop;
	
	private static final int REQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		AccessibilityUtil.checkAccessibility(getApplicationContext());
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_stop = (Button) findViewById(R.id.btn_stop);
		
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startService(new Intent(MainActivity.this, TrackerService.class));
			}
		});
		
		btn_stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				stopService(new Intent(MainActivity.this, TrackerService.class));
			}
		});
	}

//	private void checkOverlayPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (!Settings.canDrawOverlays(this)) {
//                startActivityForResult(
//                        new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()))
//                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
//                        REQUEST_CODE
//                );
//                Toast.makeText(this, "请先授予 \"Activity 栈\" 悬浮窗权限", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
