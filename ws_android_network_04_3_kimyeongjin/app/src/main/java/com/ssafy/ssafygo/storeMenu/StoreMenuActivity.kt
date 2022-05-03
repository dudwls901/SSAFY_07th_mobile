package com.ssafy.ssafygo.storeMenu

import android.app.Activity
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.tech.Ndef
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.ssafygo.ApplicationClass
import com.ssafy.ssafygo.R
import com.ssafy.ssafygo.databinding.ActivityStoreMenuBinding
import com.ssafy.ssafygo.dto.StoreMenuDTO
import com.ssafy.ssafygo.receipt.ReceiptListActivity
import com.ssafy.ssafygo.service.StoreMenuService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "StoreReviewActivity_싸피"
class StoreMenuActivity : AppCompatActivity() {
    private val REGISTER = 0
    private val DELETE = 1
    private val MODIFY = 2

    private var storeId = 1

    private var storeMenuList: ArrayList<StoreMenuDTO> = arrayListOf()
    private lateinit var adapter: ArrayAdapter<StoreMenuDTO>
    private lateinit var nfcAdapter : NfcAdapter
    private lateinit var binding: ActivityStoreMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityStoreMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        storeId = intent.getIntExtra("StoreId", -1)

        // Adapter와 ListView 연결
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, storeMenuList)
        binding.listviewStoreMenu.adapter = adapter

        // Intent 사용
        val requestActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                val intent: Intent? = activityResult.data
                val storeReview = intent?.getSerializableExtra("OStoreReview") as StoreMenuDTO

                Log.d(TAG, "onCreate: $storeReview")

            }
        }

        //주문 페이지
        binding.listviewStoreMenu.setOnItemClickListener { parent, view, position, id ->
            val storeReview = storeMenuList[position]
            requestActivity.launch(Intent(this, StoreMenuDetailActivity::class.java).apply {
                putExtra("StoreReview", storeReview)
                putExtra("ActionFlag", MODIFY)
            })
        }

        //주문내역 페이지
        binding.receiptButton.setOnClickListener {
            startActivity(Intent(this, ReceiptListActivity::class.java))
        }


        //태깅관련
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        val recI = intent

        getNdefMessages(recI)

        getStoreMenuInfo(storeId)
    }

    private fun getNdefMessages(intent: Intent){
        // 1. 인텐트에서 NdefMessage 배열 데이터를 가져온다.
        val rawMsg = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)

        // 2. Data를 변환
        if(rawMsg != null){
            val msgArr = arrayOfNulls<NdefMessage>(rawMsg.size)

            for(i in rawMsg.indices){
                msgArr[i] = rawMsg[i] as NdefMessage?
            }

            // 3. NdefMessage에서 NdefRecode -> payload
            val recInfo = msgArr[0]!!.records[0]

            // Record type check : text, uri
            val data = recInfo.type
            val recType = String(data)

            if(recType == "T"){
                Log.d(TAG, "getNdefMessages: ${String(recInfo.payload, 3, recInfo.payload.size - 3)}")

            } else{
                Log.d(TAG, "getNdefMessages: XXX")
            }

        }
    }

    // 메뉴 정보 모두 가져오기
    private fun getStoreMenuInfo(storeId: Int) {
        val storeService = ApplicationClass.retrofit.create(StoreMenuService::class.java)
        // enqueue를 통해 비동기적으로 API 호출 작업 수행
        storeService.getStoreMenu(storeId).enqueue(object : Callback<List<StoreMenuDTO>> {
            override fun onResponse(call: Call<List<StoreMenuDTO>>, response: Response<List<StoreMenuDTO>>) {
                val res = response.body()
                if (response.code() == 200) {
                    storeMenuList.clear()
                    if (res != null) {
                        storeMenuList.addAll(res)
                    }
                    else {
                        Toast.makeText(this@StoreMenuActivity,
                            "메뉴를 가져올 수 없습니다.",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, "onResponse: $res")
                }
                else {
                    Log.d(TAG, "onResponse: Error Code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<StoreMenuDTO>>, t: Throwable) {
                Log.d(TAG, t.message ?: "메뉴 정보 불러오는 중 통신오류")
            }
        })
    }
}