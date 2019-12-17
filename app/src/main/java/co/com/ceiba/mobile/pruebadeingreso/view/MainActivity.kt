package co.com.ceiba.mobile.pruebadeingreso.view

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
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.adapters.PostAdapter
import co.com.ceiba.mobile.pruebadeingreso.data.Post
import co.com.ceiba.mobile.pruebadeingreso.viewmodels.PostViewModel
import kotlinx.android.synthetic.main.activity_main.buttonAddPost
import kotlinx.android.synthetic.main.activity_main.recycler_view

class MainActivity : AppCompatActivity() {
    companion object {
        const val ADD_POST_REQUEST = 1
        const val EDIT_POST_REQUEST = 2
    }

    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonAddPost.setOnClickListener {
            startActivityForResult(
                    Intent(this, AddEditPostActivity::class.java),
                    ADD_POST_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        var adapter = PostAdapter()

        recycler_view.adapter = adapter

        postViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)

        postViewModel.getAllPosts().observe(this, Observer<List<Post>> {
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
                postViewModel.delete(adapter.getPostAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Post Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : PostAdapter.OnItemClickListener {
            override fun onItemClick(post: Post) {
                var intent = Intent(baseContext, AddEditPostActivity::class.java)
                intent.putExtra(AddEditPostActivity.EXTRA_ID, post.id)
                intent.putExtra(AddEditPostActivity.EXTRA_TITLE, post.title)
                intent.putExtra(AddEditPostActivity.EXTRA_DESCRIPTION, post.description)
                intent.putExtra(AddEditPostActivity.EXTRA_PRIORITY, post.priority)

                startActivityForResult(intent, EDIT_POST_REQUEST)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_posts -> {
                postViewModel.deleteAllPosts()
                Toast.makeText(this, "All posts deleted!", Toast.LENGTH_SHORT).show()
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
            val newPost = Post(
                    data!!.getStringExtra(AddEditPostActivity.EXTRA_TITLE),
                    data.getStringExtra(AddEditPostActivity.EXTRA_DESCRIPTION),
                    data.getIntExtra(AddEditPostActivity.EXTRA_PRIORITY, 1)
            )
            postViewModel.insert(newPost)

            Toast.makeText(this, "Post saved!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_POST_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditPostActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Could not update! Error!", Toast.LENGTH_SHORT).show()
            }

            val updatePost = Post(
                    data!!.getStringExtra(AddEditPostActivity.EXTRA_TITLE),
                    data.getStringExtra(AddEditPostActivity.EXTRA_DESCRIPTION),
                    data.getIntExtra(AddEditPostActivity.EXTRA_PRIORITY, 1)
            )
            updatePost.id = data.getIntExtra(AddEditPostActivity.EXTRA_ID, -1)
            postViewModel.update(updatePost)

        } else {
            Toast.makeText(this, "Post not saved!", Toast.LENGTH_SHORT).show()
        }


    }
}