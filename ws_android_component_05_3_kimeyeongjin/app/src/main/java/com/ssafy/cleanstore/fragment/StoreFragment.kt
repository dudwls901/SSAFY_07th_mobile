package com.ssafy.cleanstore.fragment

import android.Manifest
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.cleanstore.R
import com.ssafy.cleanstore.databinding.FragmentStoreBinding
import com.ssafy.cleanstore.stuff.StuffActivity

class StoreFragment : Fragment() {

    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentStoreBinding.inflate(inflater, container, false).apply {
            tvStoreName.text = "싸피벅스"
            tvStoreTel.text = "010-1234-5678"
            tvStoreLat.text = "36.10830144233874"
            tvStoreLng.text = "128.41827450414362"

            layoutStoreInfo.setOnClickListener {
                Intent(ctx, StuffActivity::class.java).apply {
                    startActivity(this)
                }
            }

            //권한 리스너
            val permissionlistener = object : PermissionListener {
                override fun onPermissionGranted() {
                    //권한 허용시
                    addContacts(
                        storeName =  tvStoreName.text.toString(),
                        storeTel = tvStoreTel.text.toString()
                    )

                }

                override fun onPermissionDenied(deniedPermissions: List<String>) {
                    Toast.makeText(requireContext(),
                        "연락처 접근 권한을 허가해주세요",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }

            //버튼 클릭시
            storeButton.setOnClickListener {

                //권한 팝업
                TedPermission.create()
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("권한을 허용해주세요. [설정] > [앱 및 알림] > [고급] > [앱 권한]")
                    .setPermissions(Manifest.permission.WRITE_CONTACTS)
                    .check()
            }


        }.root
    }

    /**
     * 아래 사이트 내용을 읽고, addContacts 코드 내용을 분석해보세요.
     * https://developer.android.com/guide/topics/providers/contacts-provider
     */
    private fun addContacts(storeName: String, storeTel: String) {
        val p = ContentValues()
        p.put(ContactsContract.RawContacts.ACCOUNT_TYPE, "com.google")
        p.put(ContactsContract.RawContacts.ACCOUNT_NAME, "ssafy")
        val rowContact = ctx.contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, p)
        val rawContactId = ContentUris.parseId(rowContact!!)

        // 연락처의 이름 지정
        val value = ContentValues()
        value.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
        value.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
        value.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, storeName)
        ctx.contentResolver.insert(ContactsContract.Data.CONTENT_URI, value)

        // 연락처의 전화번호 지정
        val ppv = ContentValues()
        ppv.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
        ppv.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
        ppv.put(ContactsContract.CommonDataKinds.Phone.NUMBER, storeTel)
        ppv.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
        ctx.contentResolver.insert(ContactsContract.Data.CONTENT_URI, ppv)

        Toast.makeText(ctx,"$storeName 연락처가 저장되었습니다.", Toast.LENGTH_SHORT).show()
    }
}