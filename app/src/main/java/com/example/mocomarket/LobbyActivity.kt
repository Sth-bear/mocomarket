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
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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

//        binding.lobbyRecycler.apply {
//            addItemDecoration(DividerItemDecoration(context,LinearLayout.VERTICAL))
//            layoutManager = LinearLayoutManager(context)
//            adapter = PostAdapter(PostItemList.postItemList)
//        }

        val adapter = PostAdapter(PostItemList.postItemList)
        binding.lobbyRecycler.adapter = adapter
        binding.lobbyRecycler.layoutManager = LinearLayoutManager(this)
        binding.lobbyRecycler.addItemDecoration(DividerItemDecoration(this,LinearLayout.VERTICAL))
        adapter.itemClick = object : PostAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
//                val postImg: Int = PostItemList.postItemList[position].aIcon
//                val postTitle:String = PostItemList.postItemList[position].aName
//                val postIntro: String = PostItemList.postItemList[position].aIntro
//                val postUserName: String = PostItemList.postItemList[position].aUserName
//                val postPrice: Int = PostItemList.postItemList[position].aPrice
//                val postArea:String = PostItemList.postItemList[position].aArea
//                val intent = Intent(this@LobbyActivity, DetailActivity::class.java)
//                intent.putExtra("postImg", postImg)
//                intent.putExtra("postTitle", postTitle)
//                intent.putExtra("postIntro", postIntro)
//                intent.putExtra("postUserName", postUserName)
//                intent.putExtra("postPrice", postPrice)
//                intent.putExtra("postArea", postArea)
//                startActivity(intent) //parcelize로 변경
                val selectedPost = PostItemList.postItemList[position]
                val intent = Intent(this@LobbyActivity, DetailActivity::class.java)
                intent.putExtra("selectedData", selectedPost)
                startActivity(intent)
            }
        }


        binding.ivBell.setOnClickListener {
            notification()
        }


    }

    override fun onBackPressed() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("종료")
        builder.setMessage("정말 종료하시겠습니까?")
        builder.setIcon(R.drawable.nooo)
        builder.setPositiveButton("확인") {
            dialog, which -> super.onBackPressed()
        }
        builder.setNegativeButton("아니요") {
            dialog, which -> dialog.dismiss()
        }
        builder.show()
    }

    fun notification() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder
        if(!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE,packageName)
            }
            startActivity(intent)
        }
        val channelId = "one-channel"
        val channelName = "My Channel One"
        val channel = NotificationChannel (
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
        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.noti_icon)
        val intent = Intent(this, LobbyActivity::class.java) // 추후 연결될 하면 추가
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  //연결될 task 확인할것.
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
        manager.notify(11,builder.build())
    }
}