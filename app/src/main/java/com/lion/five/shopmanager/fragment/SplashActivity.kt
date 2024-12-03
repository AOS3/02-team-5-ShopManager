package com.lion.five.shopmanager.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.lion.five.shopmanager.MainActivity
import com.lion.five.shopmanager.R
import com.lion.five.shopmanager.data.dao.ProductDatabase
import com.lion.five.shopmanager.data.repository.ProductRepository
import com.lion.five.shopmanager.databinding.ActivitySplashBinding
import com.lion.five.shopmanager.utils.showMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {
    private val binding: ActivitySplashBinding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        initializeDatabase()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * 데이터베이스 초기화 및 데이터 로딩을 처리하는 함수
     * - 진행 상태를 표시하는 프로그레스바 표시
     * - 데이터베이스 초기화 대기
     * - 성공 시 메인 화면으로 전환
     * - 실패 시 에러 메시지 표시
     */
    private fun initializeDatabase() {
        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            runCatching {
                waitForDatabaseInitialization()
            }.onSuccess {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }.onFailure { e ->
                e.printStackTrace()
                showMessage("데이터 로딩 중 오류가 발생했습니다")
            }
        }
    }


    /**
     * 데이터베이스 초기화가 완료될 때까지 대기하는 함수
     * - 데이터베이스 인스턴스 생성
     * - 데이터가 모두 저장될 때까지 주기적으로 체크 (500ms 간격)
     * - 데이터 로딩 완료 후 최소 표시 시간(1000ms) 대기
     */
    private suspend fun waitForDatabaseInitialization() {
        withContext(Dispatchers.IO) {
            ProductDatabase.getInstance(this@SplashActivity)
            while (ProductRepository.selectProductInfoAll(this@SplashActivity).isEmpty()) {
                delay(500)
            }
            delay(1000)
        }
    }
}