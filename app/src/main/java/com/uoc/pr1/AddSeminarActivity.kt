package com.uoc.pr1

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.uoc.pr1.databinding.ActivityAddSeminarBinding
import java.io.InputStream

class AddSeminarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSeminarBinding

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddSeminarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnNew.setOnClickListener {


            val l:AddSeminarResult = AddSeminarResult(binding.editTitle.text.toString(),
                binding.editDuration.text.toString(),
                binding.editLevel.text.toString(),
                "https://www.revolumedia.com/uoc/img/questions1.jpg")
            val intent = Intent()
            intent.putExtra(PARAM_ADDREQUESTRESULT_CLASS, l)
            this.setResult(Activity.RESULT_OK,intent)
            finish()


        }

    }
}