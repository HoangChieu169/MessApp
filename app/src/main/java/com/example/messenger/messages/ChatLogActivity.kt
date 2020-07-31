package com.example.messenger.messages

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.messenger.R
import com.example.messenger.models.User
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_rom.view.*

class ChatLogActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        supportActionBar?.title = "Chat Log"
        setupDummyData()
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
 button_send_chatlog.setOnClickListener {
     Log.d("chieuhv"," Đã bấm đang send")
     perfromSendMessage()
 }

    }
    class ChatMessage ( val text: String, val id:String ,val formID: String , val toID: String, val timetamp : Long)
    private fun perfromSendMessage(){
        val text = editText_ChatLog.text.toString()
        val formID = FirebaseAuth.getInstance().uid
        val  toID= intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
         if (formID ==null)return

        val chatMessage = ChatMessage(reference.key!!,text,formID!!,toID)
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d("chieuhv","truyen text : ${reference.key}")
            }

    }

    private fun setupDummyData(){

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = user.uid
        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatItem())
        adapter.add(ChatToItem())
        adapter.add(ChatItem())
        adapter.add(ChatToItem())


        recyclerView_chat_log.adapter = adapter
    }

}
class  ChatItem(val text:String) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind   (viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_from_row.text = "From Message ......"
    }

}
class  ChatToItem(val text: String) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        Log.d("chieuhv","Huylol")
        return R.layout.chat_to_rom
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_to_row.text="This is the to row text message is longer ..."
    }

}