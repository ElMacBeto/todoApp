package com.practica.todoapp.data.model

data class ErrorTaskMessage(
    var name:String?=null,
    var description:String?=null,
    var type:String?=null,
    var date:String?=null,
    var time:String?=null,
    var status:Boolean=false
)