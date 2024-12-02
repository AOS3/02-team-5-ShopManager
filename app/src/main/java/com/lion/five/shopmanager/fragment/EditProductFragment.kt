package com.lion.five.shopmanager.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.lion.five.shopmanager.MainActivity
import com.lion.five.shopmanager.R
import com.lion.five.shopmanager.adapter.ProductImageAdapter
import com.lion.five.shopmanager.data.model.Product
import com.lion.five.shopmanager.data.repository.ProductRepository
import com.lion.five.shopmanager.databinding.FragmentEditProductBinding
import com.lion.five.shopmanager.listener.OnDeleteClickListener
import com.lion.five.shopmanager.utils.FileUtil
import com.lion.five.shopmanager.utils.clearAccount
import com.lion.five.shopmanager.utils.popBackstack
import com.lion.five.shopmanager.utils.showMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProductFragment : Fragment(), OnDeleteClickListener {
    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var appContext: Context
    private var isName = true
    private var isImage = true
    private var isDescription = true
    private var isPrice = true
    private var isStock = true
    private var isType = true


    private val adapter: ProductImageAdapter by lazy { ProductImageAdapter(this) }

    private val product by lazy { arguments?.getParcelable<Product>("product") }
    private val imageList = mutableListOf<Uri>()

    /**
     * 갤러리에서 선택한 이미지들을 처리하는 런처
     */
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            if (uris.isNotEmpty()) {
                handleImagesSelected(uris)
            }
        }

    /**
     * 권한 요청 결과를 처리하는 런처
     */
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when {
                isGranted -> openGallery()
                shouldShowRequestPermissionRationale(getRequiredPermission()) -> showPermissionContextPopup()
                else -> showPermissionContextPopup()
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setBottomNavigationVisibility(false)
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
        with(binding) {
            tfProductName.editText?.setText(product?.name)
            tfProductDescription.editText?.setText(product?.description)
            tfProductPrice.editText?.setText(product?.price.toString())
            tfProductStock.editText?.setText(product?.stock.toString())
            cgProductType.check(selectProductType())
        }

        setupProductNameTextField()
        setupProductDescriptionTextField()
        setupProductPriceTextField()
        setupProductStockTextField()
        setupProductTypeTextField()
    }

    private fun selectProductType(): Int {
        return when (product?.type) {
            "디즈니" -> R.id.product_type_disney
            "픽사" -> R.id.product_type_pixar
            "마블" -> R.id.product_type_marvel
            "해리포터" -> R.id.product_type_harry_potter
            "지브리" -> R.id.product_type_ghibli
            "티니핑" -> R.id.product_type_teenieping
            else -> 0
        }
    }

    /**
     * 이미지 리사이클러뷰 어댑터 설정
     */
    private fun setupRecyclerView() {
        binding.rvProductImageList.adapter = adapter
        val fileNames = product?.images

        imageList.addAll(fileNames!!.map { fileName ->
            FileUtil.loadImageUriFromFileName(requireContext(), fileName)
        })

        adapter.submitList(imageList)
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

        binding.btnProductAdd.setOnClickListener {
            showUpdateProductDialog()
        }
    }

    /*
    * 상품을 수정하는 함수
    * */
    private fun updateProduct() {
        val name = binding.tfProductName.editText?.text.toString()
        val description = binding.tfProductDescription.editText?.text.toString()
        val price = binding.tfProductPrice.editText?.text.toString().toInt()
        val stock = binding.tfProductStock.editText?.text.toString().toInt()
        val type = binding.cgProductType.findViewById<Chip>(
            binding.cgProductType.checkedChipId
        ).text.toString()

        // 이미지 파일 저장하고 파일명 리스트 생성
        val savedImageFiles = imageList.map { uri ->
            FileUtil.saveImageFromUri(requireContext(), uri)
        }

        // Product 객체 생성
        val newProduct = Product(
            id = product!!.id,
            name = name,
            description = description,
            price = price,
            stock = stock,
            type = type,
            images = savedImageFiles,
            reviewCount = 0
        )

        // 코루틴으로 DB 저장 처리
        lifecycleScope.launch(Dispatchers.IO) {
            // Repository를 통해 DB에 저장
            ProductRepository.updateProductInfo(requireContext(), newProduct)

            // UI 업데이트는 메인 스레드에서
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "상품이 수정되었습니다.", Toast.LENGTH_SHORT).show()
                popBackstack()
            }
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
        isStock = binding.tfProductStock.editText?.text.toString().toInt() != 0
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

        imageList.addAll(selectedUris)
        adapter.submitList(imageList)

        isImage = imageList.isNotEmpty()
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
        AlertDialog.Builder(appContext)
            .setTitle("권한이 필요합니다")
            .setMessage("이미지를 가져오기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setNegativeButton("취소", null)
            .setPositiveButton("동의") { _, _ ->
                permissionLauncher.launch(getRequiredPermission())
            }
            .show()
    }

    /*
    * 상품 수정 시 재확인 하는 dialog
    * */
    private fun showUpdateProductDialog() {
        AlertDialog.Builder(appContext)
            .setTitle("상품 수정")
            .setMessage("상품을 수정하면 이전 데이터는 지워집니다.")
            .setNegativeButton("취소", null)
            .setPositiveButton("수정") { _, _ ->
                updateProduct()
                appContext.showMessage("상품이 수정되었습니다.")
            }
            .show()
    }
}