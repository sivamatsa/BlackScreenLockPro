package com.example.blackscreenlock

import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.provider.Settings

class BlackScreenTileService: TileService() {
    override fun onClick() {
        super.onClick()
        if(Settings.canDrawOverlays(this)) startService(Intent(this,BlackScreenService::class.java))
        else {
            startActivityAndCollapse(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                android.net.Uri.parse("package:$packageName")))
        }
    }
}
