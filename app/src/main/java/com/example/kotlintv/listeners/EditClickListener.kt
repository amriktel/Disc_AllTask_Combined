package com.example.kotlintv.listeners

interface EditClickListener {
    fun isEditEnabled(enabled: Boolean)
    fun isDeleted(deleted: Boolean)
}