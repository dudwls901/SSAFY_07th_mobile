package com.ssafy.memo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // MemoItemMgr
//    private lateinit var memoItemMgr: IMemoItemMgr

    // ListView에 들어갈 String List
    private lateinit var listViewItems: ArrayList<String>

    // ListView
    private lateinit var listView: ListView

    //DB 선언부
    private var memoDao = MemoDao()

    // 등록버튼
    //private lateinit var btnCreate: Button  // 기능 제거

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //helper 인스턴스 생성 -> helper() 생성자가 실행되어 memos 파일이 생성된다.
        memoDao.dbOpenHelper(this)
        memoDao.open()

        // 앱 최초 실행 시, 권한 설정
        requirePermission()

        listView = findViewById(R.id.listView)

        updateListViewItems()
        updateListView()

        // 등록 버튼과 리스트 항목을 터치할 때 사용할 Intent 객체 생성
        val intent = Intent(this, MemoEditActivity::class.java)



        // ListView에서 클릭한 Item에 들어있는 메모 데이터를 MemoEditActivity로 넘기기
        listView.setOnItemClickListener { parent, view, position, id ->
            val memo = memoDao.selectMemo(position)
            Log.d("clicked memo", "onCreate: ${memo}")
            intent.putExtra("Title", memo.title)
            intent.putExtra("Content", memo.content)
            intent.putExtra("Date", memo.date)
            intent.putExtra("Num", position)
            memoEditActivityLauncher.launch(intent)
        }
        
        // 2-4. ContextMenu를 ListView와 연결하기
        registerForContextMenu(listView)

        // 새 Activity를 실행할 때는 onCreate만 실행되기 때문에
        // 앱 실행 전 메시지 수신 시 따로 실행해 줘야한다.
        createSMSMemo(this.intent)
    }

    // memoItemMgr 내용을 가지고 listViewItems 내용 업데이트
    private fun updateListViewItems() {
        // 새 리스트 만들기
        listViewItems = arrayListOf()

        val memoItemList = memoDao.selectAllMemos()

        // 메모 매니저에 있는 메모의 제목과 날짜를 조합하여 listViewItems에 담는다.
        for (memo in memoItemList) {
            val title = memo.title
            val date = memo.date
            listViewItems.add("$title $date")
        }
    }

    // ListView와 Adapter 연결하고 ListView 내용 갱신하기
    private fun updateListView() {
        // Adapter 생성
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listViewItems)

        // ListView와 Adapter 연결
        listView.adapter = adapter

        // ListView 변경 적용
        adapter.notifyDataSetChanged()
    }

    // MemoEditActivity에서 MainActivity로 돌아올 때 수행할 작업
    private val memoEditActivityLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val intent: Intent? = it.data

            val title = intent!!.getStringExtra("OTitle").toString()
            val content = intent.getStringExtra("OContent").toString()
            val date = intent.getStringExtra("ODate").toString()
            val num = intent.getIntExtra("ONum",0)

            //state 0 : 등록 1 : 수정 2 : 삭제
            val state = intent.getIntExtra("OState",0)

            // 받아온 값으로 MemoItem 생성
            val m = MemoDto(title, content, date)

            // 수정 일 때 아닐때
            when (state) {
                0 -> memoDao.insertMemo(m)//등록
                1 -> memoDao.updateMemo(num, m)//수정
                2 -> memoDao.deleteMemo(num)
            }

            updateListViewItems()
            updateListView()
        }
    }

    // 2-4. Context 메뉴 구현
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context, menu)
    }

    // 길게 누를 시 실행
    override fun onContextItemSelected(item: MenuItem): Boolean {
        // ListView에서 길게 누른 item의 정보를 가져오기
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo  // 타입캐스팅 해야 item의 getPosition 함수 사용가능

        // position 값을 이용하여 memoItemMgr에서 제거
        Log.d("aaaaa", "onContextItemSelected: ${info.position}")
        memoDao.deleteMemo(info.position)
        Toast.makeText(applicationContext,"삭제되었습니다.", Toast.LENGTH_SHORT).show()

        // 초기화
        updateListViewItems()
        updateListView()

        return super.onContextItemSelected(item)
    }

    // 2-3. 옵션 메뉴 구현
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuCreate -> {
                // 등록
                val intent = Intent(this, MemoEditActivity::class.java)

                // 값의 초기화
                intent.putExtra("Title", "")
                intent.putExtra("Content", "")
                intent.putExtra("Date", "")
                intent.putExtra("Num", -1)
                memoEditActivityLauncher.launch(intent)
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // SMS 수신 Permission 함수
    private fun requirePermission() {
        val permissions = arrayOf(Manifest.permission.RECEIVE_SMS)
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, permissions, 1)
        }
    }

    // 메세지 MEMO 추가
    private fun createSMSMemo(intent: Intent?) {
        if (intent != null) {
            // MemoReceiver에서 온 Data
            val smsSender = intent.getStringExtra("SMSSender").toString()
            val smsContents = intent.getStringExtra("SMSContents").toString()
            val smsDate = intent.getStringExtra("SMSDate").toString()
            val smsState = intent.getBooleanExtra("SMSstate",false)
            val memo = MemoDto(smsSender, smsContents, smsDate)

            // 메시지 수신으로 앱이 실행되는 것과 아닐 때를 구분해 주기 위해
            if (smsState) {
                memoDao.insertMemo(memo)
            }

            updateListViewItems()
            updateListView()
        }
    }

    // Activity를 계속 새로 실행하지 않고 재사용할 경우 onCreate가 아닌 onNewIntent를 불러온다.
    // Activity를 새로 시작하지 않고 재사용하기 위해 onNewIntent를 만들어준다.
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        createSMSMemo(intent)
    }
}