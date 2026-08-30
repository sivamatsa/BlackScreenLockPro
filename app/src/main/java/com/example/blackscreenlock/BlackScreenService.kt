package com.example.blackscreenlock

import android.app.*
import android.content.*
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.*
import android.view.*
import android.widget.FrameLayout

class BlackScreenService: Service() {
    private var wm:WindowManager?=null
    private var overlay:View?=null
    companion object { const val CHANNEL="black_screen"; const val UNLOCK="unlock" }

    override fun onCreate() {
        super.onCreate()
        createChannel()
        startForeground(1001, notification())
        wm=getSystemService(WINDOW_SERVICE) as WindowManager
        val v=FrameLayout(this).apply{
            setBackgroundColor(Color.BLACK)
            isClickable=true
            setOnTouchListener(object:View.OnTouchListener{
                var first=0L
                override fun onTouch(view:View,event:MotionEvent):Boolean {
                    if(event.action==MotionEvent.ACTION_DOWN) {
                        val now=System.currentTimeMillis()
                        if(now-first<450) { stopSelf(); first=0 } else first=now
                    }
                    return true
                }
            })
        }
        overlay=v
        val type=WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        val flags=WindowManager.LayoutParams.FLAG_FULLSCREEN or
                  WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        wm!!.addView(v,WindowManager.LayoutParams(-1,-1,type,flags,PixelFormat.OPAQUE))
    }
    private fun notification():Notification {
        val p=PendingIntent.getService(this,1,Intent(this,BlackScreenService::class.java).setAction(UNLOCK),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        return Notification.Builder(this,CHANNEL).setContentTitle("Black Screen active")
            .setContentText("Double-tap the screen or tap Unlock")
            .setSmallIcon(android.R.drawable.ic_lock_lock).setOngoing(true)
            .addAction(Notification.Action.Builder(null,"Unlock",p).build()).build()
    }
    override fun onStartCommand(i:Intent?,f:Int,s:Int):Int { if(i?.action==UNLOCK) stopSelf(); return START_NOT_STICKY }
    override fun onDestroy(){ overlay?.let{runCatching{wm?.removeView(it)}}; overlay=null; super.onDestroy() }
    private fun createChannel(){ if(Build.VERSION.SDK_INT>=26)
        getSystemService(NotificationManager::class.java).createNotificationChannel(
            NotificationChannel(CHANNEL,"Black Screen",NotificationManager.IMPORTANCE_LOW))}
    override fun onBind(i:Intent?):IBinder?=null
}
