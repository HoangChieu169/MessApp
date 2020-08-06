package com.example.messenger.messages

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.messenger.R
import com.example.messenger.View.LatsetMessage
import com.example.messenger.models.ChatMessage
import com.example.messenger.models.User
import com.example.messenger.registerlogin.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latest_messages.*
import kotlinx.android.synthetic.main.latset_message_row.view.*

class LatestMessagesActivity : AppCompatActivity() {
    companion object{
        var currentUser : User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)
        verifyUserIsLoggedIn()
        fetchCurrentUser()
//        setupDummyRow()
        listenForLatesMessages()
        recyclerView_activity_latest_message.adapter = adapter
        recyclerView_activity_latest_message.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        adapter.setOnItemClickListener { item, view ->
            val intent = Intent(this, ChatLogActivity::class.java)
             val row = item as LatsetMessage
            intent.putExtra(NewMessageActivity.USER_KEY,row.chatPartnerUser)
            startActivity(intent)
        }
    }


    val latsetMessageMap = HashMap<String,ChatMessage>()
     private fun refeshRecyclerViewMessages(){
          adapter.clear()
            latsetMessageMap.values.forEach {
                adapter.add(LatsetMessage(it))
            }
    }
    private  fun listenForLatesMessages(){
        val formID =  FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("latest-message/$formID")
        ref.addChildEventListener(object :ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                 val chatMessage =p0 .getValue(ChatMessage::class.java) ?:return
                adapter.add(LatsetMessage(chatMessage))
                refeshRecyclerViewMessages()
                latsetMessageMap[p0.key!!] = chatMessage

            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }

        })
    }
    val adapter = GroupAdapter<ViewHolder>()
//    private fun setupDummyRow(){
//        adapter.add(LatsetMessage())
//        adapter.add(LatsetMessage())
//        adapter.add(LatsetMessage())
//
//    }


    private  fun  fetchCurrentUser()
    {
        val uid = FirebaseAuth.getInstance().uid
        val  ref = FirebaseDatabase.getInstance().getReference("/user/$uid")
         ref.addListenerForSingleValueEvent(object :ValueEventListener{
             override fun onCancelled(p0: DatabaseError) {

             }

             override fun onDataChange(p0: DataSnapshot) {
                 currentUser = p0.getValue(User::class.java)
                 Log.d("LatMessages","Current User${currentUser?.profileImageUrl}")
             }

         })
    }
    private fun verifyUserIsLoggedIn() {
        val uid =  FirebaseAuth.getInstance().uid
        if (uid==null){
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_new_message ->{
              val intent = Intent(this, NewMessageActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_sign_out ->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
