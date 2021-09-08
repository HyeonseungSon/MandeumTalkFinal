package com.rud.mandeumtalk.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.rud.mandeumtalk.MainActivity
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.auth.JoinActivity
import com.rud.mandeumtalk.databinding.FragmentIntroBinding
import com.rud.mandeumtalk.firebase.Auth


class IntroFragment : Fragment() {

//    private lateinit var auth: FirebaseAuth
    private lateinit var binding : FragmentIntroBinding
//    private lateinit var mOAuthLoginModule : OAuthLogin

    // onBackPressed()
    private var isDouble = false

//    // googleLogin 관리
//    var googleSignInClient: GoogleSignInClient? = null
//    //GoogleLogin
//    val GOOGLE_LOGIN_CODE = 9001 // Intent Request ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_intro, container, false)

//        auth = Firebase.auth

//        //구글 로그인 옵션
//        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            //486741499598-2dhe1kflsraa90qe5g7sj3mck9vlbkbo.apps.googleusercontent.com
//            .requestEmail()
//            .build()
//        //구글 로그인 클래스를 만듬
//        googleSignInClient = GoogleSignIn.getClient(activity, gso)

        binding.kakaoLoginButton.setOnClickListener {
            // 가장 먼저 로그인을 시도하는 사용자의 토큰 존재 여부를 확인합니다.
            if (AuthApiClient.instance.hasToken()) {
                UserApiClient.instance.accessTokenInfo { _, error ->
                    if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                            kakaoLogin()
                        }
                        else {
                            Toast.makeText(requireContext(), "카카오 토근 접근 오류 발생\n관리자 문의", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        // 토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                        // 토큰이 유효합니다. 로그인이 완료되었습니다.
                        Toast.makeText(requireContext(), "이미 카카오톡 로그인", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(activity, MainActivity::class.java))
                        kakaoLogin()
                    }
                }
            }
            else {
                kakaoLogin()
            }
        }



        // 회원 가입
        binding.joinBtn.setOnClickListener {
            val intent = Intent(activity, JoinActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent. FLAG_ACTIVITY_PREVIOUS_IS_TOP
            intent.putExtra("Activity", "Intro")
            startActivity(intent)
        }

        binding.naverLoginButton.setOnClickListener {
            Toast.makeText(context, "네이버 로그인은 개발 중입니다.\n카카오 로그인은 가능합니다.", Toast.LENGTH_SHORT).show()
        }

        binding.googleLoginButton.setOnClickListener {
            Toast.makeText(context, "구글 로그인은 개발 중입니다.\n카카오 로그인은 가능합니다.", Toast.LENGTH_SHORT).show()
        }

        // 비회원 가입
        binding.noAcouuntBtn.setOnClickListener {
            Auth.getAuth().signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    Toast.makeText(context, "비회원 로그인에 성공하였습니다.\n반갑습니다. 만듦톡 입니다.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "비회원 로그인 오류 발생\n관리자 문의", Toast.LENGTH_LONG).show()
                }
            }
        }
        return binding.root
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == GOOGLE_LOGIN_CODE && resultCode == Activity.RESULT_OK) {
//            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
//            if (result.isSuccess) {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = result.signInAccount
//                firebaseAuthWithGoogle(account!!)
//            }
//
//        }
//    }

//    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
//        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener {task ->
//                if (task.isSuccessful) {
//                    val intent = Intent(activity, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(intent)
//
//                    Toast.makeText(context, "로그인 성공", Toast.LENGTH_LONG).show()
//
//                } else {
//
//                    Toast.makeText(context, "로그인 실패" , Toast.LENGTH_LONG).show()
//
//                }
//            }
//    }


//    // 네이버 로그인
//    private val mOAuthLoginHandler: OAuthLoginHandler = object : OAuthLoginHandler() {
//        override fun run(success: Boolean) {
//            if (success) {
//                val accessToken: String = mOAuthLoginModule.getAccessToken(context)
//                val refreshToken: String = mOAuthLoginModule.getRefreshToken(context)
//                val expiresAt: Long = mOAuthLoginModule.getExpiresAt(context)
//                val tokenType: String = mOAuthLoginModule.getTokenType(context)
//            } else {
//                val errorCode: String =
//                    mOAuthLoginModule.getLastErrorCode(context).code
//                val errorDesc: String = mOAuthLoginModule.getLastErrorDesc(context)
//                Toast.makeText(
//                    context, "errorCode:" + errorCode
//                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }

    // Kakao Login
    private fun kakaoLogin () {
        // 단말기에 카카오톡 앱이 설치되어 있는 경우입니다.
//        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
//            println("Hyeonseung : Kakao Talk app is already installed")
//            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
//                if (error != null) {
//                    Toast.makeText(context, "카카오톡 로그인 실패\n관리자 문의", Toast.LENGTH_SHORT).show()
//                }
//                else if (token != null) {
//                    Toast.makeText(requireContext(), "카카오톡 로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
//                    getKakaoUserInformation()
//                }
//            }
//        }

        // 단말기에 카카오톡 앱이 설치되어 있지 않은 경우입니다.
        // 웹 브라우저를 실행하여 카카오계정 로그인 화면을 출력합니다.
//        else {
            // Web Kakao Login

        // 카카오 계정으로
            UserApiClient.instance.loginWithKakaoAccount(requireContext()) { token, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), "카카오 계정 로그인 오류 발생\n관리자 문의", Toast.LENGTH_SHORT).show()
                }
                else if (token != null) {
                    Toast.makeText(requireContext(), "카카오 계정 로그인 성공", Toast.LENGTH_SHORT).show()
                    getKakaoUserInformation()
                }
            }
//        }
    }

    // 카카오 계정에서 사용자 정보를 가져옵니다.
    private fun getKakaoUserInformation() {

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Toast.makeText(requireContext(), "카카오 계정 사용자 정보 추출 실패\n관리자 문의", Toast.LENGTH_SHORT).show()
            }
            else if (user != null) {
                Toast.makeText(requireContext(), "카카오 계정 사용자 정보 가져오기 성공", Toast.LENGTH_SHORT).show()
                val kakaoUserNickname : String = user.kakaoAccount?.profile?.nickname.toString()
                val kakaoUserEmail : String = user.kakaoAccount?.email.toString()
                kakaoUserJoinUs(kakaoUserEmail)
            }
        }
    }

    // 카카오톡 또는 카카오 계정으로 가입한 유저를 만듦톡 회원으로 가입시키는 과정입니다.
    // 모든 카카오 로그인 회원의 비밀번호는 12341234 입니다.
    private fun kakaoUserJoinUs (email : String) {

        Auth.getAuth().createUserWithEmailAndPassword(email, "").addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                // 카카오 로그인을 통한 만듦톡 방문이 처음인 유저의 경우 -> Firebase Auth 회원가입
                // 이메일 : 카카오톡 이메일 / 비밀번호 : 12341234
                Toast.makeText(requireContext(), "카카오톡 회원가입이 완료되었습니다.\n반갑습니다. 만듦톡 입니다.", Toast.LENGTH_LONG).show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                // 카카오 로그인을 통한 만듦톡 방문이 처음이 아닌 유저의 경우 -> Firebase Auth 로그인
                Auth.getAuth().signInWithEmailAndPassword(email, "").addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        Toast.makeText(requireContext(), "카카오톡 로그인이 완료되었습니다.\n반갑습니다. 만듦톡 입니다.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "카카오톡 로그인 실패\n관리자 문의", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}