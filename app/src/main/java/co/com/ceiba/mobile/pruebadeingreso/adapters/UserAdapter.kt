package co.com.ceiba.mobile.pruebadeingreso.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.data.User
import kotlinx.android.synthetic.main.user_item.view.text_view_username
import kotlinx.android.synthetic.main.user_item.view.text_view_priority
import kotlinx.android.synthetic.main.user_item.view.text_view_name


class UserAdapter: ListAdapter<User, UserAdapter.UserHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.name == newItem.name && oldItem.username == newItem.username
                        && oldItem.priority == newItem.priority
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val currentUser: User = getItem(position)

        holder.textViewTitle.text = currentUser.name
        holder.textViewPriority.text = currentUser.priority.toString()
        holder.textViewDescription.text = currentUser.username
    }

    fun getUserAt(position: Int): User {
        return getItem(position)
    }

    inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle: TextView = itemView.text_view_name
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewDescription: TextView = itemView.text_view_username
    }

    interface OnItemClickListener {
        fun onItemClick(user: User)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}


