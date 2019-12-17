package co.com.ceiba.mobile.pruebadeingreso.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import co.com.ceiba.mobile.pruebadeingreso.R
import kotlinx.android.synthetic.main.activity_add_edit_post.edit_text_description
import kotlinx.android.synthetic.main.activity_add_edit_post.edit_text_title
import kotlinx.android.synthetic.main.activity_add_edit_post.number_picker_priority

class AddEditPostActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "co.com.ceiba.mobile.pruebadeingreso.view.EXTRA_ID"
        const val EXTRA_TITLE = "co.com.ceiba.mobile.pruebadeingreso.view.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "co.com.ceiba.mobile.pruebadeingreso.view.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "co.com.ceiba.mobile.pruebadeingreso.view.EXTRA_PRIORITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_post)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Post"
            edit_text_title.setText(intent.getStringExtra(EXTRA_TITLE))
            edit_text_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
        } else {
            title = "Add Post"
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_post_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_post -> {
                savePost()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun savePost() {
        if (edit_text_title.text.toString().trim().isBlank() || edit_text_description.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Can not insert empty post!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_TITLE, edit_text_title.text.toString())
            putExtra(EXTRA_DESCRIPTION, edit_text_description.text.toString())
            putExtra(EXTRA_PRIORITY, number_picker_priority.value)
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }



}
