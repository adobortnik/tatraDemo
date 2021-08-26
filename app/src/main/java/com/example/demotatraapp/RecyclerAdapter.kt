package com.example.demotatraapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demotatraapp.data.User
import com.squareup.picasso.Picasso


class RecyclerAdapter(private val dataSet: List<User>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var onItemClick: ((User) -> Unit)? = null
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val avatar: ImageView
        val rootView: LinearLayout
        init {
            name = view.findViewById(R.id.name)
            avatar = view.findViewById(R.id.avatar)
            rootView = view.findViewById(R.id.root_view)
            rootView.setOnClickListener {
                onItemClick?.invoke(dataSet[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_user, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = dataSet[position]
        viewHolder.name.text = user.first_name + user.last_name
        Picasso.get().load(user.avatar).into(viewHolder.avatar)
    }


    override fun getItemCount() = dataSet.size

}