package com.example.blackscreenlock

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.*

class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val box=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL; setPadding(40,40,40,40)}
        box.addView(TextView(this).apply{text="Black Screen Lock"; textSize=28f})
        box.addView(TextView(this).apply{text="\nPlay media or run an app, then cover the display with a black overlay.\n\nUnlock options: double-tap the black screen or use the notification." ; textSize=16f})
        box.addView(Button(this).apply{text="Grant overlay permission"; setOnClickListener{
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
        }})
        box.addView(Button(this).apply{text="Start Black Screen"; setOnClickListener{
            if(Settings.canDrawOverlays(this)) { startService(Intent(this,BlackScreenService::class.java)); finish() }
            else startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
        }})
        box.addView(TextView(this).apply{text="\nQuick Settings:\nAfter installation, edit your Android Quick Settings and add “Black Screen” for one-tap access."; textSize=15f})
        setContentView(box)
    }
}
