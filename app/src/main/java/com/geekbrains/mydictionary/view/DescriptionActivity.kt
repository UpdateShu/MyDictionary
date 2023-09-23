package com.geekbrains.mydictionary.view

import android.content.Context
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import coil.ImageLoader
import coil.request.LoadRequest
import coil.transform.CircleCropTransformation

import com.geekbrains.mydictionary.R
import com.geekbrains.mydictionary.databinding.ActivityDescriptionBinding

class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        binding.descriptionHeader.text = bundle?.getString(WORD_EXTRA)
        binding.descriptionTextview.text = bundle?.getString(DESCRIPTION_EXTRA)

        val imageLink = bundle?.getString(URL_EXTRA)
        if (!imageLink.isNullOrBlank()) {
            useCoilToLoadPhoto(binding.descriptionImageview, imageLink)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String) {
        val request = LoadRequest.Builder(this)
            .data(imageLink)
            .target(
                onStart = {},
                onSuccess = { result ->
                    imageView.setImageDrawable(result)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val blurEffect = RenderEffect.createBlurEffect(15f, 0f, Shader.TileMode.MIRROR)
                        imageView.setRenderEffect(blurEffect)
                    }
                },
                onError = {
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                }
            )
            .transformations(
                CircleCropTransformation(),
            )
            .build()

        ImageLoader(this).execute(request)
    }


    companion object {

        private const val DIALOG_FRAGMENT_TAG = "8c7dff51-9769-4f6d-bbee-a3896085e76e"

        private const val WORD_EXTRA = "f76a288a-5dcc-43f1-ba89-7fe1d53f63b0"
        private const val DESCRIPTION_EXTRA = "0eeb92aa-520b-4fd1-bb4b-027fbf963d9a"
        private const val URL_EXTRA = "6e4b154d-e01f-4953-a404-639fb3bf7281"

        fun getIntent(
            context: Context,
            word: String,
            description: String,
            url: String?
        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(URL_EXTRA, url)
        }
    }
}