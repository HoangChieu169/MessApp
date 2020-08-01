package com.example.messenger.messages

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.messenger.R
import com.example.messenger.models.ChatMessage
import com.example.messenger.models.User
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_rom.view.*

class ChatLogActivity:AppCompatActivity() {
    val  adapter = GroupAdapter<ViewHolder>()
    var toUser: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        recyclerView_chat_log.adapter = adapter
        toUser = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = toUser?.username
//        setupDummyData()
        listFormMessage()


        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        button_send_chatlog.setOnClickListener {
            Log.d("chieuhv", " Đã bấm đang send")
            perfromSendMessage()
        }

    }

    private fun listFormMessage() {
        val ref = FirebaseDatabase.getInstance().getReference("/mess")
        ref.addChildEventListener(object : ChildEventListener {
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
                val chatMessage = p0.getValue(ChatMessage::class.java)
                if (chatMessage !=null){
                    if (chatMessage.formID == FirebaseAuth.getInstance().uid){
                        val current = LatestMessagesActivity.currentUser ?: return
                        adapter.add(ChatItem(chatMessage.text,current!!))
                    }else{

                        adapter.add(ChatToItem(chatMessage.text, toUser!!))
                    }
                Log.d("chieuhv", chatMessage?.text)

                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun perfromSendMessage() {
        val text = editText_ChatLog.text.toString()
        val formID = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toID = user.uid
        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        if (formID == null) return

        val chatMessage = ChatMessage(
            reference.key!!, text, formID!!, toID, System.currentTimeMillis() / 1000
        )
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d("chieuhv", "truyen text : ${reference.key}")
            }

    }

//    private fun setupDummyData(){
//
//        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
//        supportActionBar?.title = user.uid
//        val adapter = GroupAdapter<ViewHolder>()
//
//        adapter.add(ChatItem())
//        adapter.add(ChatToItem())
//        adapter.add(ChatItem())
//        adapter.add(ChatToItem())
//
//
//        recyclerView_chat_log.adapter = adapter
//    }
//
//}
}
class  ChatItem(val text:String, val user: User) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind   (viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_from_row.text = "From Message ......"
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.imageView_chat_form_row
        Picasso.get().load(uri).into(targetImageView)
    }

}
class  ChatToItem(val text: String, val user: User) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        Log.d("chieuhv","Huylol")
        return R.layout.chat_to_rom
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_to_row.text="This is the to row text message is longer ..."
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.imageView_chat_to_rom
        Picasso.get().load(uri).into(targetImageView)
    }

}