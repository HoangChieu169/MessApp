package com.example.messenger.models

class ChatMessage ( val text: String, val id:String ,val formID: String , val toID: String, val timetamp : Long){
    constructor():this("","","","",-1)
}