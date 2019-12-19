package co.com.udc.mobile.test.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.com.udc.mobile.test.R

class PoActivity : AppCompatActivity() {


    companion object {
        const val EXTRA_ID = "co.com.udc.mobile.test.view.EXTRA_ID"
        const val EXTRA_NAME = "co.com.udc.mobile.test.view.EXTRA_NAME"
        const val EXTRA_PHONE = "co.com.udc.mobile.test.view.EXTRA_PHONE"
        const val EXTRA_EMAIL = "co.com.udc.mobile.test.view.EXTRA_EMAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
    }
}
