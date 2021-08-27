package com.rud.mandeumtalk.board

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rud.mandeumtalk.MainActivity
import com.rud.mandeumtalk.R

class BoardViewActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var myRef : DatabaseReference

    lateinit var builder : AlertDialog.Builder
    lateinit var alertDialog : AlertDialog

    val hateList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view)

        val title = intent.getStringExtra("Board Title").toString()
        val content = intent.getStringExtra("Board Content").toString()
        val writer = intent.getStringExtra("Board writer").toString()
        val dateTime = intent.getStringExtra("Board dateTime").toString()
        val writerUid = intent.getStringExtra("Board Writer Uid").toString()
        val key = intent.getStringExtra("Board Key").toString()

        findViewById<TextView>(R.id.titleArea).text = title
        findViewById<TextView>(R.id.contentArea).text = content
        findViewById<TextView>(R.id.writerArea).text = writer
        findViewById<TextView>(R.id.dateTimeArea).text = dateTime

        getImageData(key)

        auth = Firebase.auth

        if (writerUid == auth.currentUser!!.uid) {
            findViewById<LinearLayout>(R.id.writerLayout).isVisible = true
        } else {
            findViewById<LinearLayout>(R.id.visitorLayout).isVisible = true

            val boardKeyList = mutableListOf<String>()
            val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid.toString()

            val hateListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (dataModel in dataSnapshot.children) {
                        val item = dataModel.getValue(HateModel::class.java)

                        val boardKey : String = item?.boardKey.toString()
                        boardKeyList.add(boardKey)

                        if (boardKeyList.contains(key)) {
                            findViewById<LinearLayout>(R.id.visitorLayout).isVisible = false
                            findViewById<LinearLayout>(R.id.haterLayout).isVisible = true
                        }
                        println("HateBoardViewActivity.kt.boardKeyTest : $boardKeyList")
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("HateBoardViewActivity.kt", "loadPost::onCancelled", error.toException())
                }
            }
            Firebase.database.getReference("Hate_List").child(currentUserUid).addValueEventListener(hateListener)
        }

        findViewById<ImageView>(R.id.backIcon).setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        findViewById<Button>(R.id.editButton).setOnClickListener {
            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)
        }

        findViewById<Button>(R.id.deleteButton).setOnClickListener {
            showDeleteDialog(key)
        }

        findViewById<Button>(R.id.sueButton).setOnClickListener {

            hateDialog(key)
        }

        findViewById<Button>(R.id.sueCancelButton).setOnClickListener {
            hateCancelDialog(key)
        }
    }

    private fun getImageData(key: String) {

        val storageReference = Firebase.storage.reference.child(key + ".png")
        val imageViewFromFB = findViewById<ImageView>(R.id.boardImage)

        storageReference.downloadUrl.addOnCompleteListener({ task ->
            if (task.isSuccessful) {
                Glide.with(this).load(task.result).into(imageViewFromFB)
            } else {

            }
        })
    }

    private fun hateDialog(key : String) {

        try{
            var str_tittle = "게시물 신고"
            var str_message = "정말 해당 게시물을 신고하시겠습니까?"
            var str_buttonOK = "네"
            var str_buttonNO = "아니오"

            builder = AlertDialog.Builder(this)
            builder.setTitle(str_tittle) //팝업창 타이틀 지정
            builder.setIcon(R.drawable.accountmainpink) // 팝업창 이미지
            builder.setMessage(str_message) //팝업창 내용 지정
//            builder.setCancelable(false) //외부 레이아웃 클릭시도 팝업창이 사라지지않게 설정
            builder.setPositiveButton(str_buttonOK, DialogInterface.OnClickListener { dialog, which ->

                Toast.makeText(this, "해당 게시물이\n내가 신고한 게시물 목록에\n추가되었습니다.", Toast.LENGTH_SHORT).show()

                auth = Firebase.auth
                val currentUserUid : String = auth.currentUser?.uid.toString()

                Firebase.database.getReference("Hate_List").child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child(key).setValue(HateModel(key, currentUserUid))

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            })
            builder.setNegativeButton(str_buttonNO, DialogInterface.OnClickListener { dialog, which ->

                Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
            })

            alertDialog = builder.create()
            try {
                alertDialog.show()
            }
            catch (e : Exception){
                e.printStackTrace()
            }
        }
        catch(e : Exception){
            e.printStackTrace()
        }
    }

    private fun showDeleteDialog(key: String) {
        try{
            var str_tittle = "게시물 삭제"
            var str_message = "정말 게시물을 삭제하시겠습니까?"
            var str_buttonOK = "Yes"
            var str_buttonNO = "No"

            builder = AlertDialog.Builder(this)
            builder.setTitle(str_tittle) //팝업창 타이틀 지정
            builder.setIcon(R.drawable.accountmainpink) // 팝업창 이미지
            builder.setMessage(str_message) //팝업창 내용 지정
            builder.setCancelable(false) //외부 레이아웃 클릭시도 팝업창이 사라지지않게 설정
            builder.setPositiveButton(str_buttonOK, DialogInterface.OnClickListener { dialog, which ->

                Firebase.database.getReference("Board").child(key).removeValue()

                Toast.makeText(this, "게시물이 삭제되었습니다.", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            })
            builder.setNegativeButton(str_buttonNO, DialogInterface.OnClickListener { dialog, which ->

                Toast.makeText(this, "게시물 삭제를 취소합니다.", Toast.LENGTH_SHORT).show()
            })

            alertDialog = builder.create()
            try {
                alertDialog.show()
            }
            catch (e : Exception){
                e.printStackTrace()
            }
        }
        catch(e : Exception){
            e.printStackTrace()
        }
    }

    private fun hateCancelDialog (key : String) {
        try{
            var str_tittle = "게시물 신고 취소"
            var str_message = "해당 게시물의 신고를 취소하시겠습니까?"
            var str_buttonOK = "신고 취소"
            var str_buttonNO = "신고 유지"

            builder = AlertDialog.Builder(this)
            builder.setTitle(str_tittle) //팝업창 타이틀 지정
            builder.setIcon(R.drawable.accountmainpink) // 팝업창 이미지
            builder.setMessage(str_message) //팝업창 내용 지정
//            builder.setCancelable(false) //외부 레이아웃 클릭시도 팝업창이 사라지지않게 설정
            builder.setPositiveButton(str_buttonOK, DialogInterface.OnClickListener { dialog, which ->

                Toast.makeText(this, "신고가 취소되었습니다.", Toast.LENGTH_SHORT).show()
                Firebase.database.getReference("Hate_List").child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child(key).removeValue()

                val intent = Intent(this, MainActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
//                finish()

            })
            builder.setNegativeButton(str_buttonNO, DialogInterface.OnClickListener { dialog, which ->

                val test : String = Firebase.database.getReference("Hate_List").child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child(key).get().toString()
                Toast.makeText(this, test, Toast.LENGTH_SHORT).show()

                Toast.makeText(this, "게시물 신고를 유지합니다.", Toast.LENGTH_SHORT).show()
                finish()
            })

            alertDialog = builder.create()
            try {
                alertDialog.show()
            }
            catch (e : Exception){
                e.printStackTrace()
            }
        }
        catch(e : Exception){
            e.printStackTrace()
        }
    }
}













