package co.com.ceiba.mobile.pruebadeingreso.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.data.Post
import kotlinx.android.synthetic.main.post_item.view.text_view_description
import kotlinx.android.synthetic.main.post_item.view.text_view_priority
import kotlinx.android.synthetic.main.post_item.view.text_view_title


class PostAdapter: ListAdapter<Post, PostAdapter.PostHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.title == newItem.title && oldItem.description == newItem.description
                        && oldItem.priority == newItem.priority
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val currentPost: Post = getItem(position)

        holder.textViewTitle.text = currentPost.title
        holder.textViewPriority.text = currentPost.priority.toString()
        holder.textViewDescription.text = currentPost.description
    }

    fun getPostAt(position: Int): Post {
        return getItem(position)
    }

    inner class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle: TextView = itemView.text_view_title
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewDescription: TextView = itemView.text_view_description
    }

    interface OnItemClickListener {
        fun onItemClick(post: Post)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}


