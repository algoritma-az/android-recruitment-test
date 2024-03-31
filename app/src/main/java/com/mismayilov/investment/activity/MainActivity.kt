package com.mismayilov.investment.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mismayilov.core.listener.ConnectivityViewModel
import com.mismayilov.investment.R
import com.mismayilov.investment.broadcast.InternetBroadcastReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var connectivityViewModel: ConnectivityViewModel
    private lateinit var broadcastReceiver:InternetBroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
        connectivityViewModel = ViewModelProvider(this)[ConnectivityViewModel::class.java]
        broadcastReceiver = InternetBroadcastReceiver(connectivityViewModel)
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        connectivityViewModel.isConnected.removeObservers(this)
        super.onDestroy()
    }

}