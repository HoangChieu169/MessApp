package com.example.messenger.messages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.messenger.R
import com.example.messenger.registerlogin.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.title = "Select User"

//      val adapter = GroupAdapter<ViewHolder>()
//
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//
//        recyclerView_newmessage.adapter=adapter
         fetchUser()
    }

    private fun fetchUser() {
        val ref = FirebaseDatabase.getInstance().getReference("/user")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                p0.children.forEach {
                    Log.d("NewMessage",it.toString())
                    val user = it.getValue(User::class.java)
                    if (user != null){
                    adapter.add(UserItem(user))
                    }
                }
                adapter.setOnItemClickListener { item, view ->
                    val intent = Intent(view.context)
                    
                }
                recyclerView_newmessage.adapter = adapter
            }
        })
    }

}
    class UserItem(val user: User): Item<ViewHolder>(){
        override fun getLayout(): Int {
            return R.layout.user_row_new_message
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
          viewHolder.itemView.textView_user_newmessger.text = user.username
            Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageView_newMessage)
        }

    }


