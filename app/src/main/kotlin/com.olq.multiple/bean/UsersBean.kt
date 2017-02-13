package com.olq.multiple.bean

/**
 * Created by Administrator on 2017/2/10.
 */
data class UsersBean (
        var username:String="",
        var password:String="",
        var email:String="",
//        var createdAt:String="",
//        var objectId:String="",
        var sessionToken:String?=null
)
