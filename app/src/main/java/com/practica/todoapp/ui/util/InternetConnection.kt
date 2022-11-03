package com.practica.todoapp.ui.util


import java.io.IOException
import java.net.UnknownHostException


object InternetConnection {

    private const val URL_CONECTION = "64.233.177.139"//ip de google


    fun isConnectToInternet(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 $URL_CONECTION")
            val exitValue = ipProcess.waitFor()
            ipProcess.destroy()
            return exitValue == 0
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }

}