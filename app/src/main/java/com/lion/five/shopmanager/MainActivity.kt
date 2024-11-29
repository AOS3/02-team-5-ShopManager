package com.lion.five.shopmanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.lion.five.shopmanager.data.Storage
import com.lion.five.shopmanager.data.repository.ProductRepository
import com.lion.five.shopmanager.databinding.ActivityMainBinding
import com.lion.five.shopmanager.fragment.HomeFragment
import com.lion.five.shopmanager.fragment.SignInFragment
import com.lion.five.shopmanager.fragment.SalesFragment
import com.lion.five.shopmanager.utils.FileUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNavigation()

        lifecycleScope.launch(Dispatchers.IO) {
            val currentProducts = ProductRepository.selectProductInfoAll(this@MainActivity)

            // 저장된 데이터가 없을 경우에만 데이터 초기화 및 ProgressBar 표시
            if (currentProducts.isEmpty()) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.VISIBLE
                }

                // runCatching을 사용하여 예외 처리
                runCatching {
                    initializeData() // 데이터 초기화
                }.onFailure { e ->
                    // 예외가 발생하면 에러 출력
                    e.printStackTrace()
                }.onSuccess {
                    // 데이터 로딩 완료 후 ProgressBar를 숨김 (UI 스레드에서 실행)
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            } else {
                // 저장된 데이터가 있을 경우 바로 프래그먼트 부착
                withContext(Dispatchers.Main) {
                    supportFragmentManager.commit {
                        replace(R.id.container_main, HomeFragment())
                        setReorderingAllowed(true)
                    }
                }
            }
        }

    }

    // 데이터 초기화 작업
    private fun initializeData() {
        // Repository를 통해 현재 저장된 제품 데이터 확인
        val currentProducts = ProductRepository.selectProductInfoAll(this)

        // 제품 데이터가 비어있으면, 초기 데이터를 삽입
        if (currentProducts.isEmpty()) {
            try {
                // 이미지를 파일로 저장하는 로직
                listOf(
                    R.drawable.ghibli_1,
                    R.drawable.ghibli_2,
                    R.drawable.ghibli_3,
                    R.drawable.ghibli_4,
                    R.drawable.ghibli_5,
                    R.drawable.ghibli_6,
                    R.drawable.ghibli_7,
                    R.drawable.ghibli_8,
                    R.drawable.ghibli_9,
                    R.drawable.ghibli_10,
                    R.drawable.ghibli_11,
                    R.drawable.ghibli_12,
                    R.drawable.marvel_1,
                    R.drawable.marvel_2,
                    R.drawable.marvel_3,
                    R.drawable.marvel_4,
                    R.drawable.marvel_5,
                    R.drawable.marvel_6,
                    R.drawable.marvel_7,
                    R.drawable.marvel_8,
                    R.drawable.marvel_9,
                    R.drawable.marvel_10,
                    R.drawable.marvel_11,
                    R.drawable.pixar_1,
                    R.drawable.pixar_2,
                    R.drawable.pixar_3,
                    R.drawable.pixar_4,
                    R.drawable.pixar_5,
                    R.drawable.pixar_6,
                    R.drawable.pixar_7,
                    R.drawable.pixar_8,
                    R.drawable.pixar_9,
                    R.drawable.disney_1,
                    R.drawable.disney_2,
                    R.drawable.disney_3,
                    R.drawable.disney_4,
                    R.drawable.disney_5,
                    R.drawable.disney_6,
                    R.drawable.disney_7,
                    R.drawable.disney_8,
                    R.drawable.disney_9,
                    R.drawable.disney_10,
                    R.drawable.harrypotter_1,
                    R.drawable.harrypotter_2,
                    R.drawable.harrypotter_3,
                    R.drawable.harrypotter_4,
                    R.drawable.harrypotter_5,
                    R.drawable.harrypotter_6,
                    R.drawable.harrypotter_7,
                    R.drawable.harrypotter_8,
                    R.drawable.harrypotter_9,
                    R.drawable.harrypotter_10,
                    R.drawable.teenieping_1,
                    R.drawable.teenieping_2,
                    R.drawable.teenieping_3,
                    R.drawable.teenieping_4,
                    R.drawable.teenieping_5
                ).forEach { drawableId ->
                    // 각 이미지를 파일로 저장
                    FileUtil.saveImageFromDrawable(this, drawableId)
                }

                // 제품 데이터를 Storage에서 가져와 삽입
                Storage.products.forEach { product ->
                    ProductRepository.insertProductInfo(this, product)
                }

                // 데이터 삽입 완료 후 HomeFragment를 동적으로 추가
                lifecycleScope.launch(Dispatchers.Main) {
                    supportFragmentManager.commit {
                        replace(R.id.container_main, HomeFragment()) // HomeFragment를 container에 교체
                        setReorderingAllowed(true)
                    }
                }

            } catch (e: Exception) {
                // 예외 발생 시 로그 출력
                e.printStackTrace()
            }
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.commit {
                        replace(R.id.container_main, HomeFragment())
                        setReorderingAllowed(true)
                    }
                    true
                }
                R.id.navigation_sales -> {
                    supportFragmentManager.commit {
                        replace(R.id.container_main, SalesFragment())
                        setReorderingAllowed(true)
                    }
                    true
                }
                R.id.navigation_login -> {
                    supportFragmentManager.commit {
                        replace(R.id.container_main, SignInFragment())
                        setReorderingAllowed(true)
                    }
                    true
                }
                else -> false
            }
        }
    }
}

