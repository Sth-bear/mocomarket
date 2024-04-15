package com.example.mocomarket

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mocomarket.databinding.ActivityDetailBinding
import com.example.mocomarket.databinding.ActivityLobbyBinding
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        val postImg = intent.getIntExtra("postImg",0)
//        val postTitle = intent.getStringExtra("postTitle")
//        val postIntro = intent.getStringExtra("postIntro")
//        val postUserName = intent.getStringExtra("postUserName")
//        val postArea = intent.getStringExtra("postArea")
//        val postPrice = intent.getIntExtra("postPrice", 0) parcelize로 변경
        val selectedPost = intent.getParcelableExtra("selectedData", PostItem::class.java)
        selectedPost?.let { putEachData(it) }
        binding.btnBack.bringToFront() //버튼 최상단으로 올리기
        binding.btnBack.setOnClickListener{
            finish()
        }

    }

    private fun putEachData(post: PostItem) {
        val decimal = DecimalFormat("#,###")

        binding.ivDetailImage.setImageResource(post.aIcon)
        binding.tvDetailArea.text = post.aArea
        binding.tvDetailIntro.text = post.aIntro
        binding.tvDetailUserName.text = post.aUserName
        binding.tvDetailTitle.text = post.aName
        binding.tvDetailPrice.text = "${decimal.format(post.aPrice)}원"
    }
}