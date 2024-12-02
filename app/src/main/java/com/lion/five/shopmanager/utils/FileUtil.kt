package com.lion.five.shopmanager.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object FileUtil {
    // 앱 내부 저장소의 images 디렉토리
    private fun getImageDirectory(context: Context): File {
        val dir = File(context.filesDir, "images")
        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir
    }

    // Uri로부터 이미지를 저장하고 파일명 반환
    fun saveImageFromUri(context: Context, uri: Uri): String {
        val fileExtension = getFileExtensionFromUri(context, uri)
        val fileName = "img_${UUID.randomUUID()}.$fileExtension"
        val file = File(getImageDirectory(context), fileName)

        // Uri에서 비트맵으로 변환
        val bitmap = context.contentResolver.openInputStream(uri)?.use { input ->
            BitmapFactory.decodeStream(input)
        } ?: throw IllegalStateException("이미지를 불러올 수 없습니다")

        // 비트맵을 파일로 저장
        FileOutputStream(file).use { out ->
            when (fileExtension) {
                "jpg", "jpeg" -> bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                "png" -> bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                else -> throw IllegalArgumentException("지원하지 않는 파일 형식입니다")
            }
        }

        return fileName
    }

    // 파일명으로부터 이미지 로드
    fun loadImageFile(context: Context, fileName: String): File {
        return File(getImageDirectory(context), fileName)
    }

    // 파일 삭제
    fun deleteImage(context: Context, fileName: String) {
        val file = File(getImageDirectory(context), fileName)
        if (file.exists()) {
            file.delete()
        }
    }

    // drawable 리소스를 파일로 저장하고 파일명 반환
    fun saveImageFromDrawable(context: Context, drawableId: Int): String {
        // 파일 이름에 확장자 포함
        val fileExtension = "jpg" // 기본적으로 JPG로 저장
        val fileName = "${context.resources.getResourceEntryName(drawableId)}.$fileExtension"
        val file = File(getImageDirectory(context), fileName)

        if (!file.exists()) {
            val bitmap = BitmapFactory.decodeResource(context.resources, drawableId)
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }
        }

        return fileName
    }

    // Uri에서 파일 확장자를 추출하는 함수
    private fun getFileExtensionFromUri(context: Context, uri: Uri): String {
        // URI가 "file://" 인 경우
        if (uri.scheme == "file") {
            val filePath = uri.path ?: throw IllegalArgumentException("URI 경로를 가져올 수 없습니다")
            return filePath.substring(filePath.lastIndexOf(".") + 1)
        }

        // URI가 content:// 형식일 경우
        val mimeType = context.contentResolver.getType(uri)
            ?: throw IllegalArgumentException("지원하지 않는 MIME 타입입니다.")

        return when (mimeType) {
            "image/jpeg" -> "jpg"
            "image/png" -> "png"
            else -> throw IllegalArgumentException("지원하지 않는 MIME 타입입니다: $mimeType")
        }
    }


    // FileName을 찾아 Uri를 반환하는 함수
    fun loadImageUriFromFileName(context: Context, fileName: String): Uri {
        val file = File(getImageDirectory(context), fileName)
        return Uri.fromFile(file)
    }

}