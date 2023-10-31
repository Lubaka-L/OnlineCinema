package com.example.onlinecinema.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.onlinecinema.App
import com.example.onlinecinema.R
import com.example.onlinecinema.databinding.DialogLayoutBinding
import com.example.onlinecinema.domain.repository.Repository
import com.example.onlinecinema.ui.startScreen.StartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.component.inject(this)

        lifecycleScope.launch {
            repository.isOverflowException.collectLatest {
                if (it){
                    showAlertDialog()
                }
            }
        }
    }

    fun showAlertDialog(){
        val builder = AlertDialog.Builder(this)
        val customView = DialogLayoutBinding.inflate(layoutInflater)
        builder.setView(customView.root)
        customView.escapeButton.setOnClickListener {
            finishAffinity()
        }
        builder.setCancelable(false)
        val dialog = builder.create()

        dialog.show()
    }


}