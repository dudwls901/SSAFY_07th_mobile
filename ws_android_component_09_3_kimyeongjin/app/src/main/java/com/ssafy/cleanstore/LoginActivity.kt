package com.ssafy.cleanstore

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.ssafy.cleanstore.databinding.ActivityLoginBinding
private const val TAG = "LoginActivity_싸피"
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    // firebase authentication 관련
    private var mAuth: FirebaseAuth? = null
    //구글 로그인하는 화면이 GoogleSignInClient객체를 통해 뜨게 되는 거임
    var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //자동 로그인 안 돼있으면 다시 true 처리
        binding.loginButton.isEnabled=false

        binding.loginButton.setOnClickListener {
            initAuth()
        }

        initAuth()

    }

    // 인증 초기화
    //로그인하면 토큰을 줌
    //추후 로그인할 때마다 토큰 사용
    //토큰으로 어스에따가 사용자 닉네임달라고요청, 썸네일 이미지 등도 요청
    //토ㅓ큰 요청하기위해 default_web_client_id를 사용함
    private fun initAuth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // default_web_client_id 값은 build 타임에 values.xml 파일에 자동 생성
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail() // 인증 방식: gmail
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        //파이어베이스 어스 객체 얻어오기
        //굳이 미리 얻어올 필요는 없지만 그냥 미리 얻어옴

        mAuth = FirebaseAuth.getInstance()

        //intent생성해서 GoogleSignInClient액티비티를 호출한 것과 같음
        // Google에서 제공되는 signInIntent를 이용해서 인증 시도
        val signInIntent = mGoogleSignInClient!!.signInIntent

        //콜백함수 부르며 launch
        requestActivity.launch(signInIntent)
    }
    // 구글 인증 결과 획득 후 동작 처리
    private val requestActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        Log.d(TAG, "firebaseAuthWithGoogle: Activity.RESULT_OK): ${RESULT_OK}, activityResult.resultCode:${activityResult.resultCode}")
        if (activityResult.resultCode == Activity.RESULT_OK) {

            // 인증 결과 획득
            val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle: account: ${account}")
                firebaseAuthWithGoogle(account!!.idToken)
            }
            catch (e: ApiException) {
                Log.w(TAG, "google sign in failed: " ,e)
            }
        }
    }

    // 구글 인증 결과 성공 여부에 따른 처리
    private fun firebaseAuthWithGoogle(idToken: String?) {
        Log.d(TAG, "firebaseAuthWithGoogle: idToken: ${idToken}")
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth!!.currentUser
                    updateUI(user)
                }
                else {
                    updateUI(null)
                }
            }
    }

    // 인증 성공 여부에 따른 화면 처리
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Log.d(TAG, "updateUI: 사용자의 사진 : ${user.photoUrl}")
            Log.d(TAG, "updateUI: 사용자의 이메일 : ${user.email}")
            val intent = Intent(this@LoginActivity, MainActivity::class.java)

            intent.putExtra("userImageUrl",user.photoUrl.toString())
            intent.putExtra("userName",user.displayName)
            startActivity(intent)
            finish()
        }
        else {
            binding.loginButton.isEnabled =true
            Toast.makeText(this, "로그인하세요.", Toast.LENGTH_SHORT).show()
        }
    }
}