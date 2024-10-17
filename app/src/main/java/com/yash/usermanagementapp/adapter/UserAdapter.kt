package com.yash.usermanagementapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yash.usermanagementapp.R
import com.yash.usermanagementapp.user.User

class UserAdapter(
    private var userList:List<User>,
    private val onAction:(User,String)->Unit
)
    :  RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
class UserViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    private  val userNameTextView: TextView=itemView.findViewById(R.id.userNameTextView)
    private  val userAgeTextView: TextView=itemView.findViewById(R.id.userAgeTextView)
    private  val editImageView: ImageView =itemView.findViewById(R.id.editImageView)
    private  val deleteImageView: ImageView =itemView.findViewById(R.id.deleteImageView)
    private val userMobileNumberTextView:TextView=itemView.findViewById(R.id.userMobileNumberTextView)

//    @SuppressLint("SetTextI18n")
    fun bind(user: User, onAction: (User, String) -> Unit) {
        val position = 0
       val userId = user.id
        userNameTextView.text = user.name
        userAgeTextView.text = "Age: ${user.age}"
        userMobileNumberTextView.text= "Number: ${user.number}"



        // Edit and Delete button actions
        editImageView.setOnClickListener { onAction(user, "edit") }
        deleteImageView.setOnClickListener { onAction(user, "delete") }
    }
}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
    return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position], onAction)
    }
}