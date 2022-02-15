package com.example.prmanager.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.prmanager.utils.Constants.Companion.PR_UI_DATE_FORMAT
import java.util.*

object AndroidUtils {

  fun isNetworkActive(context: Context): Boolean {

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
  }

  fun formatDate(dateStr: String, currFormat: SimpleDateFormat): String {
    currFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = currFormat.parse(dateStr)

    val retFormat = SimpleDateFormat(PR_UI_DATE_FORMAT, Locale.ENGLISH)
    retFormat.timeZone = TimeZone.getDefault()
    return retFormat.format(date)
  }

}