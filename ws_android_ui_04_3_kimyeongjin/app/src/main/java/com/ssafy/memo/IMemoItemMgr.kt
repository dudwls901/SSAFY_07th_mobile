package com.ssafy.memo

interface IMemoItemMgr {
    var memos : ArrayList<MemoItem>
    fun getList() : ArrayList<MemoItem>
    fun size() : Int
    fun search(index : Int) : MemoItem
    fun add(m: MemoItem)
    fun update(index: Int, item : MemoItem)
    fun remove(index: Int)
    fun clear()

}