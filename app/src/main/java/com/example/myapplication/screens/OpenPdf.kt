package com.example.myapplication.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityOpenPdfBinding
import java.io.File
import java.io.FileOutputStream

class OpenPdf : AppCompatActivity() {
    private lateinit var binding: ActivityOpenPdfBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Кнопка
        binding.bOpenPdf.setOnClickListener {
            verifyPremissions()
        }
    }

    /** Проверяем, даны ли необходимые для открытия pdf файла разрешения */
    private fun verifyPremissions(){
        val p = ActivityCompat.checkSelfPermission(this@OpenPdf, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (p == PackageManager.PERMISSION_GRANTED && Environment.isExternalStorageManager()) {
            //Если разрешения даны, открываем файл
            openPdf()
        } else {
            //Если нет, запрашиваем их
            val ps = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, ps,1)
            val intent = Intent()
            intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            val uri = Uri.fromParts("package", this.packageName, null)
            intent.setData(uri)
            startActivity(intent)
        }
    }

    @Suppress("NAME_SHADOWING")
    @SuppressLint("QueryPermissionsNeeded")
    /** Открываем pdf файл */
    private fun openPdf() {
        val inputStream = resources.openRawResource(R.raw.my_file)
        inputStream.use { inputStream ->
            val file = File(cacheDir, "my_file.pdf")
            FileOutputStream(file).use { output ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }
        val cacheFile = File(cacheDir, "my_file.pdf")
        val uri = FileProvider.getUriForFile(this, "$packageName.provider", cacheFile)
        if (uri != null) {
            val pdfViewIntent = Intent(Intent.ACTION_VIEW)
            pdfViewIntent.data = uri
            pdfViewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(Intent.createChooser(pdfViewIntent, "Choos PDF viewer"))
        }
    }

}