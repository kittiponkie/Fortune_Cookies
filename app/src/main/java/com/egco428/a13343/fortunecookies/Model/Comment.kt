package com.egco428.a13343.fortunecookies.Model

class Comment {
    var id:Long = 0
    var commentData: String? = null
    var commentType: Int? = null

    override fun toString(): String {
        return commentData.toString()
    }
}