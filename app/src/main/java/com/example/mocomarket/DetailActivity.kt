package com.example.mocomarket

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mocomarket.databinding.ActivityDetailBinding
import com.example.mocomarket.databinding.ActivityLobbyBinding
import com.google.android.material.snackbar.Snackbar
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

        val selectedPost = intent.getParcelableExtra("selectedData", PostItem::class.java)
        selectedPost?.let { putEachData(it) }
        val selectedaIndex = selectedPost?.aIndex ?: -1
        binding.btnBack.bringToFront() //버튼 최상단으로 올리기
        binding.ivBtnLike.setOnClickListener {
            if(PostItemList.pushLike(selectedaIndex)) {
                val snackbar = Snackbar.make(it,"관심 목록에 추가되었습니다.",Snackbar.LENGTH_SHORT)
                snackbar.show()
                binding.ivBtnLike.setImageResource(R.drawable.icon_love_full)
            } else {
                binding.ivBtnLike.setImageResource(R.drawable.icon_love_empty)
            }
        }
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
        if (post.press) { //좋아요처리 추가
            binding.ivBtnLike.setImageResource(R.drawable.icon_love_full)
        } else {
            binding.ivBtnLike.setImageResource(R.drawable.icon_love_empty)
        }
    }

}