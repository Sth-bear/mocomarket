package com.example.mocomarket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mocomarket.databinding.ActivityLobbyBinding

class LobbyActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLobbyBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val adapter = PostAdapter(PostItemList.postItemList)
        binding.lobbyRecycler.adapter = adapter
        binding.lobbyRecycler.layoutManager = LinearLayoutManager(this)
        binding.lobbyRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        adapter.itemClick = object : PostAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val selectedPost = PostItemList.postItemList[position]
                val intent = Intent(this@LobbyActivity, DetailActivity::class.java)
                intent.putExtra("selectedData", selectedPost)
                startActivity(intent)
            }
            override fun onLongClick(view: View, position: Int) {
                relly(adapter, position)
            }
        }

        binding.ivBell.setOnClickListener {
            notification()
        }

        //상단이동 버튼
        val fadeIn = AlphaAnimation(0F, 1F).apply { duration = 700 }
        val fadeOut = AlphaAnimation(1F, 0F).apply { duration = 500 }
        var isTop = false

        binding.lobbyRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.lobbyRecycler.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) { //상단에 오면 사라짐처리 위로 스크롤할수없음상태 << 상단에서 당기면 표시됨. 조건 추가처리 고민필요
                    binding.btnTop.startAnimation(fadeOut)
                    binding.btnTop.visibility = View.GONE
                    isTop = true
                } else {
                    if (isTop) {
                        binding.btnTop.visibility = View.VISIBLE
                        binding.btnTop.startAnimation(fadeIn)
                        isTop = false
                    }
                }
            }
        })

        binding.btnTop.setOnClickListener {
            binding.lobbyRecycler.smoothScrollToPosition(0)
        }

    }

    override fun onRestart() {
        super.onRestart() // 새로고침을 위해 재할당 및 클릭처리부분을 추가. 맘에들진않음 모듈화 하면 깔끔해지긴할듯.
        val adapter = PostAdapter(PostItemList.postItemList)
        binding.lobbyRecycler.adapter = adapter
        binding.lobbyRecycler.layoutManager = LinearLayoutManager(this)
        binding.lobbyRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        adapter.itemClick = object : PostAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val selectedPost = PostItemList.postItemList[position]
                val intent = Intent(this@LobbyActivity, DetailActivity::class.java)
                intent.putExtra("selectedData", selectedPost)
                startActivity(intent)
            }
            override fun onLongClick(view: View, position: Int) {
                relly(adapter, position)
            }
        }
    }

    fun relly(adapter: PostAdapter, position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("상품 삭제")
        builder.setMessage("정말 삭제하겠습니까?")
        builder.setPositiveButton("확인") { dialog, which ->
            PostItemList.postItemList.removeAt(position)
            adapter.notifyItemRemoved(position) //새로고침처리
            adapter.notifyItemRangeChanged(position, PostItemList.postItemList.size - position) //해당 코드가 없으면 삭제이후, 아래에 있는 정보들이 맞지않음. 포지션 이후의 정보들또한 재할당.
            // adapter.notifyDataSetChanged << 변경점이 있을때 새로고침처리. 원인을 모를떄에도 되기에 최대한 명시적으로 변경.
        }
        builder.setNegativeButton("아니요") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onBackPressed() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("종료")
        builder.setMessage("정말 종료하시겠습니까?")
        builder.setIcon(R.drawable.nooo)
        builder.setPositiveButton("확인") { dialog, which ->
            super.onBackPressed()
        }
        builder.setNegativeButton("아니요") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    fun notification() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }
            startActivity(intent)
        }
        val channelId = "one-channel"
        val channelName = "My Channel One"
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "My Channel One Description"
            setShowBadge(true)
            val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            setSound(uri, audioAttributes)
            enableVibration(true)
        }
        manager.createNotificationChannel(channel)
        builder = NotificationCompat.Builder(this, channelId)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.noti_icon)
        val intent = Intent(this, LobbyActivity::class.java) // 추후 연결될 화면 추가
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  //연결될 task 확인할것.
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        builder.run {
            setSmallIcon(R.mipmap.ic_launcher_round)
            setWhen(System.currentTimeMillis())
            setContentTitle("키워드알림")
            setContentText("두둥!")
            setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("설정한 키워드에 대한 알림이 도착했습니다!")
            )

            setLargeIcon(bitmap)
            addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
        }
        manager.notify(11, builder.build())
    }

}