package co.com.udc.mobile.test.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.udc.mobile.test.R
import co.com.udc.mobile.test.adapters.UserAdapter
import co.com.udc.mobile.test.data.User
import co.com.udc.mobile.test.data.UserRepository
import co.com.udc.mobile.test.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.buttonAddUser
import kotlinx.android.synthetic.main.activity_main.recycler_view

class MainActivity : AppCompatActivity() {
    companion object {
        const val ADD_POST_REQUEST = 1
        const val EDIT_POST_REQUEST = 2
    }

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()

        buttonAddUser.setOnClickListener {
            startActivityForResult(
                    Intent(this, AddEditUserActivity::class.java),
                    ADD_POST_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        var adapter = UserAdapter()

        recycler_view.adapter = adapter

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        userViewModel.getAllUsers().observe(this, Observer<List<User>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                userViewModel.delete(adapter.getUserAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "User Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : UserAdapter.OnItemClickListener {
            override fun onItemClick(user: User) {
                var intent = Intent(baseContext, AddEditUserActivity::class.java)
                intent.putExtra(AddEditUserActivity.EXTRA_ID, user.id)
                intent.putExtra(AddEditUserActivity.EXTRA_NAME, user.name)
                intent.putExtra(AddEditUserActivity.EXTRA_USERNAME, user.username)
                intent.putExtra(AddEditUserActivity.EXTRA_EMAIL, user.email)
                intent.putExtra(AddEditUserActivity.EXTRA_PHONE, user.phone)

                startActivityForResult(intent, EDIT_POST_REQUEST)
            }
        })
    }

    private lateinit var userAdapter: UserAdapter
    private lateinit var userRepository: UserRepository

    private fun initComponents() {
        userAdapter = UserAdapter()
        userRepository = UserRepository(application)
        userRepository.requestMovieReviewList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_users -> {
                userViewModel.deleteAllUsers()
                Toast.makeText(this, "All users deleted!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_POST_REQUEST && resultCode == Activity.RESULT_OK) {
            val newUser = User(
                    data!!.getStringExtra(AddEditUserActivity.EXTRA_NAME),
                    data.getStringExtra(AddEditUserActivity.EXTRA_USERNAME),
                    data.getStringExtra(AddEditUserActivity.EXTRA_EMAIL),
                    data.getStringExtra(AddEditUserActivity.EXTRA_PHONE)
            )
            userViewModel.insert(newUser)

            Toast.makeText(this, "User saved!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_POST_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditUserActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Could not update! Error!", Toast.LENGTH_SHORT).show()
            }

            val updateUser = User(
                    data!!.getStringExtra(AddEditUserActivity.EXTRA_NAME),
                    data.getStringExtra(AddEditUserActivity.EXTRA_USERNAME),
                    data.getStringExtra(AddEditUserActivity.EXTRA_EMAIL),
                    data.getStringExtra(AddEditUserActivity.EXTRA_PHONE)
            )
            updateUser.id = data.getIntExtra(AddEditUserActivity.EXTRA_ID, -1)
            userViewModel.update(updateUser)

        } else {
            Toast.makeText(this, "User not saved!", Toast.LENGTH_SHORT).show()
        }
    }
}