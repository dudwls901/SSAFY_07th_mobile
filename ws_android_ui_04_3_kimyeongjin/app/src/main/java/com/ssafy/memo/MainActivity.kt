package com.ssafy.memo

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.memo.databinding.ActivityMainBinding
import com.ssafy.memo.util.Utils

private val TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var memoItemMgr: MemoItemMgr

    private lateinit var adapter: ArrayAdapter<String>

//    var itemActionType = ""
//    var itemIdx = -1
//    var itemHead = ""
//    var itemContent = ""
//    var itemDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        memoItemMgr = MemoItemMgr()

        updateListData()
        initViews()
        registerForContextMenu(binding.listView)
    }

    //option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.option_menu_create) {
            val intent = Intent(Intent(this@MainActivity, MemoEditActivity::class.java))
            intent.putExtra("actionType", "save")
            memoEditActivityListener.launch(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.menu_context, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info: AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
//        Log.d(TAG, "onContextItemSelected: ${item.itemId} ${item}")
        when(item.itemId){
             R.id.context_menu_delete->{
//                Log.d(TAG, "onContextItemSelected: ${info.position}")
                //삭제
                memoItemMgr.remove(info.position)
                //갱신
                updateListData()
            }
        }

        return super.onContextItemSelected(item)
    }


    private fun updateListData() {
        val strData = Array(memoItemMgr.size()) { "" }
        for (i in memoItemMgr.getList().indices) {
            strData[i] = "${memoItemMgr.getList()[i].title} ${memoItemMgr.getList()[i].date}"
        }

        adapter = ArrayAdapter(
            this@MainActivity, android.R.layout.simple_list_item_1,
            strData
        )
        binding.listView.adapter = adapter
    }

    private fun initViews() {

        initListView()


    }

    private fun initListView() = with(binding) {

        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(Intent(this@MainActivity, MemoEditActivity::class.java))
            intent.putExtra("actionType", "edit")
            intent.putExtra("idx", position)
            intent.putExtra("head", memoItemMgr.getList()[position].title)
            intent.putExtra("content", memoItemMgr.getList()[position].content)
            intent.putExtra("date", memoItemMgr.getList()[position].date)
            memoEditActivityListener.launch(intent)
        }

    }

    //람다
    private val memoEditActivityListener: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = it.data
                val returnType = intent!!.getStringExtra("actionType")
                val head = intent!!.getStringExtra("head")
                val content = intent!!.getStringExtra("content")
                val date = intent!!.getStringExtra("date")
                val idx = intent!!.getIntExtra("idx", -1)

                when (returnType) {
                    "save" -> {
                        memoItemMgr.add(
                            MemoItem(
                                title = head!!,
                                content = content!!,
                                date = date!!
                            )
                        )

                    }
                    "edit" -> {
                        Log.d(TAG, "$idx")
                        if (idx >= 0) {
                            memoItemMgr.update(
                                idx, MemoItem(
                                    title = head!!,
                                    content = content!!,
                                    date = date!!
                                )
                            )
                        }
                    }
                    "delete" -> {
                        if (idx >= 0) {
                            memoItemMgr.remove(idx)
                        }
                    }
                    //cancel은 할 거 없음
                }
//            (binding.listView.adapter as ArrayAdapter<MemoItem>).notifyDataSetChanged()
                updateListData()
            }
        }
}