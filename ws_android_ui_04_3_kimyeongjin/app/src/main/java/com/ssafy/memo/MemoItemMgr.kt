package com.ssafy.memo

class MemoItemMgr: IMemoItemMgr {
    override var memos: ArrayList<MemoItem> = arrayListOf()

    override fun getList(): ArrayList<MemoItem> {
        return memos
    }

    override fun size(): Int {
        return memos.size
    }

    override fun search(index: Int): MemoItem {
        return memos[index]
    }

    override fun add(m: MemoItem) {
        memos.add(m)
    }

    override fun update(index: Int, item: MemoItem) {
        memos[index] = item
    }

    override fun remove(index: Int) {
        memos.removeAt(index)
    }

    override fun clear() {
        memos.clear()
    }

}