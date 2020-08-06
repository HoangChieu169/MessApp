package com.example.messenger.View

import com.example.messenger.R
import com.example.messenger.models.ChatMessage
import com.example.messenger.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latset_message_row.view.*

class LatsetMessage (val chatMessage : ChatMessage): Item<ViewHolder>(){
    var chatPartnerUser : User ? = null
    override fun getLayout(): Int {
        return R.layout.latset_message_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_Latset_user.text = chatMessage.text
        val chatPartnerID : String
        if (chatMessage.formId== FirebaseAuth.getInstance().uid){

            chatPartnerID = chatMessage.toId
        }else{
            chatPartnerID = chatMessage.formId
        }
        val  ref = FirebaseDatabase.getInstance().getReference("/user/$chatPartnerID")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                chatPartnerUser = p0.getValue(User::class.java)
                viewHolder.itemView.textView_Latset_user.text = chatPartnerUser?.username
                val targetImageView = viewHolder.itemView.imageView_latset_mesage
                Picasso.get().load(chatPartnerUser?.profileImageUrl).into(targetImageView)
            }

        })

        viewHolder.itemView.textView_user_latset.text = " What the fuck"

    }

}