package com.ssafy.ssafygo.receipt

import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NfcA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ssafy.ssafygo.ApplicationClass
import com.ssafy.ssafygo.databinding.ActivityReceiptListBinding
import com.ssafy.ssafygo.dto.ReceiptDTO
import com.ssafy.ssafygo.dto.StoreMenuDTO
import com.ssafy.ssafygo.service.ReceiptService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceiptListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceiptListBinding
    private lateinit var receiptService: ReceiptService
    private var receiptList: ArrayList<ReceiptDTO> = arrayListOf()
    private lateinit var adapter: ReceiptAdapter

    private lateinit var alertDialog: AlertDialog

    private lateinit var nfcAdcapter: NfcAdapter
    private lateinit var pendingIntent: PendingIntent
    private lateinit var tagFilter: IntentFilter

    private lateinit var tagType: String
    private lateinit var tagData: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiptListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initList()

        nfcAdcapter = NfcAdapter.getDefaultAdapter(this)
        if(nfcAdcapter==null){
            finish()
        }

        tagFilter = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        binding.saveLastReceiptButton.setOnClickListener {
            ApplicationClass.writeMode=true
            getLastReceipt()
        }

        //태그 정보가 포함된 인텐트를 처리할 액티비티 지정
        val intent = Intent(this, ReceiptListActivity::class.java)
        //SingleTop설정
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        pendingIntent = PendingIntent.getActivity(this,0,intent,0)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e("INFO", "onNewIntent: ", )
        tagType="T"
        val action = intent!!.action
        if(action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            //태그에 데이터를 write하는 작업을 수행해야 함
            //1. 태그 detect... Tag 객체
            val detectTag = intent!!.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            writeTag(makeNdefMessage(tagType, tagData), detectTag!!)
        }

    }

    override fun onResume() {
        super.onResume()
        nfcAdcapter.enableForegroundDispatch(this,pendingIntent,arrayOf(tagFilter),null )
    }

    override fun onPause() {
        super.onPause()
        nfcAdcapter.disableForegroundDispatch(this)
    }

    private fun makeNdefMessage(type: String, data: String): NdefMessage {

        var ndefM: NdefMessage? = null
        var ndefR: NdefRecord? = null

        if(type.equals("T")){
            //TextRecord
            ndefR = NdefRecord.createTextRecord("en",data)
        }
        else if(type.equals("U")){
            ndefR = NdefRecord.createUri(data)
        }
        else{ }

        return NdefMessage(arrayOf(ndefR))

    }

    //NFC tag 데이터를 write하는 코드...
    private fun writeTag(msg: NdefMessage, tag: Tag){

        //Ndef 객체를 얻는다 : Ndef.get(tag)
        val ndef = Ndef.get(tag)
        val size = msg.toByteArray().size

        if(ndef!=null) {
            ndef.connect()
            //ndef 객체를 이용해서 connect
            if(!ndef.isWritable){
                Toast.makeText(this, "write를 지원하지 않습니다..", Toast.LENGTH_SHORT).show()
                return
            }
            if(ndef.maxSize<size){
                Toast.makeText(this, "wirte할 데이터가 태그 크기보다 큽니다..", Toast.LENGTH_SHORT).show()
                return
            }
            Toast.makeText(this, "태그에 데이터를 write 합니다...", Toast.LENGTH_SHORT).show()
            //ndef 객체의 writeNdefMessage(msg) 태그에 write 한다...
            ndef.writeNdefMessage(msg)
            alertDialog.hide()
//            ApplicationClass.writeMode = false
//            nfcAdcapter.disableForegroundDispatch(this)
        }

    }

    private fun getLastReceipt(){
        val receiptService = ApplicationClass.retrofit.create(ReceiptService::class.java)

        receiptService.getLastReceipt().enqueue(object: Callback<ReceiptDTO>{
            override fun onResponse(call: Call<ReceiptDTO>, response: Response<ReceiptDTO>) {
                val result = response.body()
                tagData=result.toString()
                val builder = AlertDialog.Builder(this@ReceiptListActivity)
                builder.setTitle("마지막 주문내용 저장")
                    .setMessage("주문내용을 저장할 NFC를 태깅해주세요.")
                    .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which -> })
                alertDialog = builder.create()
                alertDialog.show()
            }

            override fun onFailure(call: Call<ReceiptDTO>, t: Throwable) {
                Log.d("?????", t.message ?: "주문 정보 통신 오류")
            }

        })

    }

    private fun initList(){
        //        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_2, receiptList)
        adapter = ReceiptAdapter()
        adapter.setHasStableIds(true)
        binding.listView.adapter = adapter
        receiptService = ApplicationClass.retrofit.create(ReceiptService::class.java)


        receiptService.getAllReceipt().enqueue(object : Callback<List<ReceiptDTO>> {
            override fun onResponse(
                call: Call<List<ReceiptDTO>>,
                response: Response<List<ReceiptDTO>>
            ) {
                val result = response.body()
                Log.d("?????", "onResponse: $result")
                if (response.code() == 200) {
                    receiptList.clear()
                    if (result != null) {
                        receiptList.addAll(result)
                    } else {
                        Toast.makeText(
                            this@ReceiptListActivity,
                            "주문 내역을 가져올 수 없습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    adapter.notifyDataSetChanged()
                } else {

                }
                adapter.submitList(receiptList)
            }

            override fun onFailure(call: Call<List<ReceiptDTO>>, t: Throwable) {
                Log.d("Receipt", t.message ?: "주문 정보 불러오는 중 통신오류")
            }
        })

    }
}
