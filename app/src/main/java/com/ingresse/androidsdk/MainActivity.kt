package com.ingresse.androidsdk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ingresse.sdk.model.request.GuestList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val request = GuestList("27914", "42753", null, 1, 3000, "697918-fbf195d8e8a4bd0135dc422bd5312bbd7088f422")
        ServiceManager.service.entrance.getGuestList(request, onSuccess = {
            Toast.makeText(this, "Total: ${it.paginationInfo.totalResults}", Toast.LENGTH_LONG).show()
        }, onError = {
            Toast.makeText(this, "Error: ${it.description}", Toast.LENGTH_LONG).show()
        }, onNetworkFail = {
            Toast.makeText(this, "Error: $it", Toast.LENGTH_LONG).show()
        })
    }
}
