package com.example.messenger.messages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.messenger.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        supportActionBar?.title = "Chat Log"

        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatItem())
        adapter.add(ChatToItem())
        adapter.add(ChatItem())
        adapter.add(ChatToItem())


        recyclerView_chat_log.adapter = adapter
    }

}
class  ChatItem : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
    }

}
class  ChatToItem : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_to_rom
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
    }

}