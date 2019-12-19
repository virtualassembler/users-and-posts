package co.com.udc.mobile.test.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import co.com.udc.mobile.test.R
import kotlinx.android.synthetic.main.activity_add_edit_user.edit_text_email
import kotlinx.android.synthetic.main.activity_add_edit_user.edit_text_name
import kotlinx.android.synthetic.main.activity_add_edit_user.edit_text_username
import kotlinx.android.synthetic.main.activity_add_edit_user.number_picker_priority

class AddEditUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "co.com.ceiba.mobile.pruebadeingreso.view.EXTRA_ID"
        const val EXTRA_NAME = "co.com.ceiba.mobile.pruebadeingreso.view.EXTRA_NAME"
        const val EXTRA_USERNAME = "co.com.ceiba.mobile.pruebadeingreso.view.EXTRA_USERNAME"
        const val EXTRA_EMAIL = "co.com.ceiba.mobile.pruebadeingreso.view.EXTRA_EMAIL"
        const val EXTRA_PHONE = "co.com.ceiba.mobile.pruebadeingreso.view.EXTRA_PRIORITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_user)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit User"
            edit_text_name.setText(intent.getStringExtra(EXTRA_NAME))
            edit_text_username.setText(intent.getStringExtra(EXTRA_USERNAME))
            edit_text_email.setText(intent.getStringExtra(EXTRA_EMAIL))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PHONE, 1)
        } else {
            title = "Add User"
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_user -> {
                saveUser()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveUser() {
        if (edit_text_name.text.toString().trim().isBlank() || edit_text_username.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Can not insert empty user!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_NAME, edit_text_name.text.toString())
            putExtra(EXTRA_USERNAME, edit_text_username.text.toString())
            putExtra(EXTRA_EMAIL, "emaildeprueba@gmail.com")
            putExtra(EXTRA_PHONE, number_picker_priority.value)
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }



}
