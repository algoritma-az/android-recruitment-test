package com.mismayilov.investment.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.mismayilov.core.listener.ConnectivityViewModel

class InternetBroadcastReceiver(val connectivityViewModel: ConnectivityViewModel) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            connectivityViewModel.checkInternetConnection(context!!)
        }
    }

}