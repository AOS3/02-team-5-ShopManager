package com.lion.five.shopmanager.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lion.five.shopmanager.adapter.ProductImageAdapter
import com.lion.five.shopmanager.databinding.FragmentAddProductBinding
import com.lion.five.shopmanager.listener.OnDeleteClickListener
import com.lion.five.shopmanager.utils.popBackstack

class AddProductFragment : Fragment(), OnDeleteClickListener {
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private var isName = false
    private var isImage = false
    private var isDescription = false
    private var isPrice = false
    private var isStock = false
    private var isType = false

    private val imageList = mutableListOf<Uri>()

    private val adapter: ProductImageAdapter by lazy { ProductImageAdapter(this) }

    /**
     * 갤러리에서 선택한 이미지들을 처리하는 런처
     */
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        if (uris.isNotEmpty()) {
            handleImagesSelected(uris)
        }
    }

    /**
     * 권한 요청 결과를 처리하는 런처
     */
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        when {
            isGranted -> openGallery()
            shouldShowRequestPermissionRationale(getRequiredPermission()) -> showPermissionContextPopup()
            else -> showPermissionContextPopup()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupLayout()
        setupRecyclerView()
    }

    /**
     * 뷰 바인딩 해제 및 메모리 누수 방지
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 이미지 삭제 버튼 클릭 시 호출되는 콜백
     */
    override fun onDelete(position: Int) {
        handleImageDelete(position)
    }

    /**
     * 각종 입력 필드 설정
     */
    private fun setupLayout() {
        setupProductNameTextField()
        setupProductDescriptionTextField()
        setupProductPriceTextField()
        setupProductStockTextField()
        setupProductTypeTextField()
    }

    /**
     * 이미지 리사이클러뷰 어댑터 설정
     */
    private fun setupRecyclerView() {
        binding.rvProductImageList.adapter = adapter
    }

    /**
     * 상품 추가 버튼의 활성화 상태 업데이트
     */
    private fun updateAddButtonState() {
        binding.btnProductAdd.isEnabled = isName && isDescription && isStock && isType && isImage
    }

    /**
     * 클릭 리스너 설정
     */
    private fun setupListeners() {
        binding.toolbarAddProduct.setNavigationOnClickListener {
            popBackstack()
        }

        binding.viewAddProductImage.setOnClickListener {
            checkPermissionAndOpenGallery()
        }

        binding.ivAddProductImageView.setOnClickListener {
            checkPermissionAndOpenGallery()
        }
    }

    /**
     * 상품명 입력 필드 설정 및 유효성 검사
     */
    private fun setupProductNameTextField() {
        binding.tfProductName.editText?.doAfterTextChanged { text ->
            isName = text?.isNotBlank() ?: false
            updateAddButtonState()
        }
    }

    /**
     * 상품 설명 입력 필드 설정 및 유효성 검사
     */
    private fun setupProductDescriptionTextField() {
        binding.tfProductDescription.editText?.doAfterTextChanged { text ->
            isDescription = text?.isNotBlank() ?: false
            updateAddButtonState()
        }
    }

    /**
     * 재고 수량 입력 필드 설정 및 유효성 검사
     */
    private fun setupProductStockTextField() {
        binding.tfProductStock.editText?.doAfterTextChanged { text ->
            isStock = validateNumberInput(text)
            updateAddButtonState()
        }
    }

    /**
     * 상품 가격 입력 필드 설정 및 유효성 검사
     */
    private fun setupProductPriceTextField() {
        binding.tfProductPrice.editText?.doAfterTextChanged { text ->
            isPrice = validateNumberInput(text)
            updateAddButtonState()
        }
    }

    /**
     * 숫자 입력값 유효성 검사 (0보다 커야 함)
     */
    private fun validateNumberInput(text: Editable?): Boolean {
        if (text.isNullOrBlank()) return false

        val number = text.toString().toIntOrNull() ?: return false
        return number > 0
    }

    /**
     * 상품 종류 선택 필드 설정
     */
    private fun setupProductTypeTextField() {
        binding.cgProductType.setOnCheckedStateChangeListener { group, checkedIds ->
            isType = checkedIds.isNotEmpty()
            updateAddButtonState()
        }
    }

    /**
     * 갤러리 권한 체크 및 권한 요청
     */
    private fun checkPermissionAndOpenGallery() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                getRequiredPermission()
            ) == PackageManager.PERMISSION_GRANTED -> {
                openGallery()
            }

            shouldShowRequestPermissionRationale(getRequiredPermission()) -> {
                showPermissionContextPopup()
            }

            else -> {
                permissionLauncher.launch(getRequiredPermission())
            }
        }
    }

    /**
     * 선택된 이미지 처리
     */
    private fun handleImagesSelected(selectedUris: List<Uri>) {
        binding.groupAddProductView.visibility = View.INVISIBLE
        binding.groupAddMoreProductImage.visibility = View.VISIBLE

        imageList.clear()
        imageList.addAll(selectedUris)
        adapter.submitList(imageList)

        isImage = true
        updateAddButtonState()
    }


    /**
     * 이미지 삭제 처리
     */
    private fun handleImageDelete(position: Int) {
        imageList.removeAt(position)
        adapter.submitList(imageList)

        isImage = imageList.isNotEmpty()
        updateAddButtonState()

        if (imageList.isEmpty()) {
            binding.groupAddMoreProductImage.visibility = View.INVISIBLE
            binding.groupAddProductView.visibility = View.VISIBLE
        }
    }

    /**
     * 안드로이드 버전에 따른 권한 반환
     */
    private fun getRequiredPermission(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    /**
     * 갤러리 실행
     */
    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    /**
     * 권한 요청 다이얼로그 표시
     */
    private fun showPermissionContextPopup() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("권한이 필요합니다")
            .setMessage("이미지를 가져오기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                permissionLauncher.launch(getRequiredPermission())
            }
            .setNegativeButton("취소", null)
            .show()
    }
}