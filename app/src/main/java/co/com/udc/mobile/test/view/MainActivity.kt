package co.com.udc.mobile.test.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.activity_main.editTextSearch
import kotlinx.android.synthetic.main.activity_main.recyclerViewSearchResults


class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()

        recyclerViewSearchResults.layoutManager = LinearLayoutManager(this)
        recyclerViewSearchResults.setHasFixedSize(true)

        var adapter = UserAdapter()

        recyclerViewSearchResults.adapter = adapter

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        userViewModel.getAllUsers().observe(this, Observer<List<User>> {
            adapter.submitList(it)
        })

        userViewModel.getFilteredUsers().observe(this, Observer<List<User>> {
            adapter.submitList(it)
        })

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, s: Int, b: Int, c: Int) {
                val result = editTextSearch.text.toString()
                if (result.isNotEmpty())
                    userViewModel.filterByName(result)
                else userViewModel.getAllUsers()
            }

            override fun afterTextChanged(editable: Editable) {}
            override fun beforeTextChanged(cs: CharSequence, i: Int, j: Int, k: Int) {}
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
        ).attachToRecyclerView(recyclerViewSearchResults)

        adapter.setOnItemClickListener(object : UserAdapter.OnItemClickListener {
            override fun onItemClick(user: User) {
                var intent = Intent(baseContext, PostActivity::class.java)
                intent.putExtra(PostActivity.EXTRA_ID, user.id)
                intent.putExtra(PostActivity.EXTRA_NAME, user.name)
                intent.putExtra(PostActivity.EXTRA_EMAIL, user.email)
                intent.putExtra(PostActivity.EXTRA_PHONE, user.phone)
                startActivity(intent)
            }
        })
    }

    private fun initComponents() {
        userAdapter = UserAdapter()
        userRepository = UserRepository(application)
        userRepository.requestMovieReviewList()
        //init
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

}