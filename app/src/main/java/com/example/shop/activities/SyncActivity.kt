package com.example.shop.activities

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shop.R
import com.example.shop.data.MyShopService
import com.example.shop.data.Product
import com.example.shop.data.ProductDAO
import com.example.shop.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class SyncActivity : AppCompatActivity() {

    companion object{
        const val TIME_IN_MILLIS_24 = 24 * 60 * 60 * 1000
    }

    lateinit var session: SessionManager
    lateinit var productDAO: ProductDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_sync)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        session = SessionManager(this)
        productDAO = ProductDAO(this)

        if (session.getLastDownload() + TIME_IN_MILLIS_24 < Calendar.getInstance().timeInMillis){
            searchProductsByName()
        }else {

        }
    }

    fun searchProductsByName() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                productDAO.deleteAll()

                val service = MyShopService.getInstance()
                val result = service.findShopByName("")

                productDAO.insertAll(result.products)

                CoroutineScope(Dispatchers.Main).launch {
                    session.setLastDownload(Calendar.getInstance().timeInMillis)
                   navigateToMainActivity()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun navigateToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) // crea un flujo de navegacion para q no quede un activity vacio
        startActivity(intent)
    }
}