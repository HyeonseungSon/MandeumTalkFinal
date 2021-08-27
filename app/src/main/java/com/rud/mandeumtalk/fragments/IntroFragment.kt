package com.rud.mandeumtalk.fragments

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.rud.mandeumtalk.MainActivity
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.auth.IntroActivity
import com.rud.mandeumtalk.auth.JoinActivity
import com.rud.mandeumtalk.databinding.FragmentIntroBinding


class IntroFragment : Fragment() {

    private val TAG : String = IntroFragment::class.java.simpleName
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : FragmentIntroBinding
    private lateinit var mOAuthLoginModule : OAuthLogin

    // onBackPressed()
    private var isDouble = false

    // googleLogin 관리
    var googleSignInClient: GoogleSignInClient? = null

    //GoogleLogin
    val GOOGLE_LOGIN_CODE = 9001 // Intent Request ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_intro, container, false)


        auth = Firebase.auth

        //구글 로그인 옵션
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            //486741499598-2dhe1kflsraa90qe5g7sj3mck9vlbkbo.apps.googleusercontent.com
            .requestEmail()
            .build()
        //구글 로그인 클래스를 만듬
        googleSignInClient = GoogleSignIn.getClient(activity, gso)




        // 카카오 오류 표시
//        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
//            if (error != null) {
//                when {
//                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
//                        Toast.makeText(context, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
//                        Toast.makeText(context, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
//                        Toast.makeText(context, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
//                        Toast.makeText(context, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
//                        Toast.makeText(context, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
//                        Toast.makeText(context, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.ServerError.toString() -> {
//                        Toast.makeText(context, "서버 내부 에러", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
//                        Toast.makeText(context, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
//                    }
//                    else -> { // Unknown
//                        Toast.makeText(context, "기타 에러", Toast.LENGTH_SHORT).show()
//                        println("IntroFragment.kt.error:$error")
//                    }
//                }
//            }
//            else if (token != null) {
//                Toast.makeText(context, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
//                val intent = Intent(activity, MainActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
//            }
//        }

//        // 카카오 버튼
//        binding.kakaoLoginButton.setOnClickListener {
//            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
//                UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
//                Toast.makeText(requireContext(), "카카오톡 로그인 시도", Toast.LENGTH_SHORT).show()
//            } else {
//                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
//                Toast.makeText(requireContext(), "카카오 계정 로그인 시도", Toast.LENGTH_SHORT).show()
//            }
//        }

        // KakaoTalk Login
        binding.kakaoLoginButton.setOnClickListener {

            // 가장 먼저 로그인을 시도하는 사용자의 토큰 존재 여부를 확인합니다.
            if (AuthApiClient.instance.hasToken()) {
                UserApiClient.instance.accessTokenInfo { _, error ->
                    if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                            //로그인 필요
                            kakaoLogin()
                        }
                        else {
                            //기타 에러
                        }
                    }
                    else {
                        //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                        // 토큰이 유효합니다. 로그인이 완료되었습니다.
                        Toast.makeText(requireContext(), "이미 카카오톡 로그인", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                //로그인 필요
                kakaoLogin()
            }

//            // 단말기에 카카오톡 앱이 설치되어 있는 경우입니다.
//            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
//                println("Hyeonseung : Kakao Talk app is already installed")
//                UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
//                    if (error != null) {
//                        Toast.makeText(requireContext(), "카카오톡 로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
//
//                        UserApiClient.instance.me { user, error ->
//                            if (error != null) {
//                                Toast.makeText(requireContext(), "사용자 정보 요청 실패 $error", Toast.LENGTH_SHORT).show()
//                                println("IntroFragment.kt.error:$error")
//                            }
//                            else if (user != null) {
//                                Toast.makeText(requireContext(), "이메일: ${user.kakaoAccount?.email}" +
//                                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}", Toast.LENGTH_SHORT).show()
//                            } else {
//                                Toast.makeText(requireContext(), "elseelseelseelseelseelse", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//
//                        val intent = Intent(activity, MainActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        startActivity(intent)
//                    }
//                    else if (token != null) {
//                        Toast.makeText(requireContext(), "Login Fail", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//
//            // 단말기에 카카오톡 앱이 설치되어 있지 않은 경우입니다.
//            // 웹 브라우저를 실행하여 카카오계정 로그인 화면을 출력합니다.
//            else {
//                // Web Kakao Login
//                UserApiClient.instance.loginWithKakaoAccount(requireContext()) { token, error ->
//                    if (error != null) {
//                        println("Hyeonseung : Login Ture")
//                        println("Hyeonseung.error : $error")
//                        println("Hyeonseung.token : $token")
//                    }
//                    else if (token != null) {
//                        println("Hyeonseung.token : $token")
//                    }
//                }
//            }

        }

        // 회원 가입
        binding.joinBtn.setOnClickListener {
            val intent = Intent(activity, JoinActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent. FLAG_ACTIVITY_PREVIOUS_IS_TOP
            intent.putExtra("Activity", "Intro")
            startActivity(intent)
        }

        // 네이버 로그인
        binding.naverLoginButton.setOnClickListener {
            //네이버 로그인
            mOAuthLoginModule = OAuthLogin.getInstance()
            mOAuthLoginModule.init(
                context, getString(R.string.naver_client_id)
                ,getString(R.string.naver_client_secret)
                ,getString(R.string.naver_client_name))
            mOAuthLoginModule.startOauthLoginActivity(activity, mOAuthLoginHandler);

            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // 구글 로그인
        binding.googleLoginButton.setOnClickListener {



            var signInIntent = googleSignInClient?.signInIntent
            startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
        }

        // 비회원 가입
        binding.noAcouuntBtn.setOnClickListener {
            auth.signInAnonymously()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        Toast.makeText(context, "로그인 성공", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(context, "실패", Toast.LENGTH_LONG).show()
                    }

                }
        }

        return binding.root

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_LOGIN_CODE && resultCode == Activity.RESULT_OK) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // Google Sign In was successful, authenticate with Firebase
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
            }

        }
    }


    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                    Toast.makeText(context, "로그인 성공", Toast.LENGTH_LONG).show()

                } else {

                    Toast.makeText(context, "로그인 실패" , Toast.LENGTH_LONG).show()

                }
            }
    }


    // 네이버 로그인
    private val mOAuthLoginHandler: OAuthLoginHandler = object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                val accessToken: String = mOAuthLoginModule.getAccessToken(context)
                val refreshToken: String = mOAuthLoginModule.getRefreshToken(context)
                val expiresAt: Long = mOAuthLoginModule.getExpiresAt(context)
                val tokenType: String = mOAuthLoginModule.getTokenType(context)
            } else {
                val errorCode: String =
                    mOAuthLoginModule.getLastErrorCode(context).code
                val errorDesc: String = mOAuthLoginModule.getLastErrorDesc(context)
                Toast.makeText(
                    context, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Kakao Login
    private fun kakaoLogin () {
        // 단말기에 카카오톡 앱이 설치되어 있는 경우입니다.
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            println("Hyeonseung : Kakao Talk app is already installed")
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) {
                    Toast.makeText(context, "카카오'톡' 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Toast.makeText(context, "사용자 정보 요청 실패 $error", Toast.LENGTH_SHORT).show()
                            println("IntroFragment.kt.error:$error")
                        }
                        else if (user != null) {
                            Toast.makeText(context, "이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "elseelseelseelseelseelse", Toast.LENGTH_SHORT).show()
                        }
                    }

                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                else if (token != null) {
                    Toast.makeText(requireContext(), "카카오'톡' 로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()

                    getKakaoUserInformation()
                }
            }
        }

        // 단말기에 카카오톡 앱이 설치되어 있지 않은 경우입니다.
        // 웹 브라우저를 실행하여 카카오계정 로그인 화면을 출력합니다.
        else {
            // Web Kakao Login
            UserApiClient.instance.loginWithKakaoAccount(requireContext()) { token, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), "카카오 계정 로그인 오류 발생", Toast.LENGTH_SHORT).show()
                }
                else if (token != null) {
                    Toast.makeText(requireContext(), "카카오 계정 로그인 성공", Toast.LENGTH_SHORT).show()
                    getKakaoUserInformation()
                }
            }
        }
    }

    // 카카오 계정에서 사용자 정보를 가져옵니다.
    private fun getKakaoUserInformation() {

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Toast.makeText(requireContext(), "카카오 계정 사용자 정보 가져오기 실패", Toast.LENGTH_SHORT).show()
                println()
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Toast.makeText(requireContext(), "카카오 계정 사용자 정보 가져오기 성공", Toast.LENGTH_SHORT).show()
                val kakaoUserNickname : String = user.kakaoAccount?.profile?.nickname.toString()
                val kakaoUserEmail : String = user.kakaoAccount?.email.toString()
                println("Hyeonseung.kakaoUserNickname : $kakaoUserNickname")
                println("Hyeonseung.kakaoUserEmail : $kakaoUserEmail")
                kakaoUserJoinUs(kakaoUserEmail)
            }
        }
    }

    private fun kakaoUserJoinUs (email : String) {

        auth.createUserWithEmailAndPassword(email, "12341234").addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "카카오톡 회원가입이 완료되었습니다.\n반갑습니다. 만듦톡 입니다.", Toast.LENGTH_LONG).show()

                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            } else {
                Toast.makeText(requireContext(), "카카오톡 로그인을 진행합니다.", Toast.LENGTH_LONG).show()
                auth.signInWithEmailAndPassword(email, "12341234").addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val intent = Intent(activity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        Toast.makeText(context, "카카오톡 로그인 성공", Toast.LENGTH_LONG).show()

                    } else {

                        Toast.makeText(context, "카카오톡 로그인 실패", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}