package br.edu.ifsp.scl.ads.pdm.mycontacts.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.ads.pdm.mycontacts.R
import br.edu.ifsp.scl.ads.pdm.mycontacts.databinding.ActivityContactBinding
import br.edu.ifsp.scl.ads.pdm.mycontacts.model.Constant.EXTRA_CONTACT
import br.edu.ifsp.scl.ads.pdm.mycontacts.model.Contact
import kotlin.random.Random

class ContactActivity : AppCompatActivity() {

    private val acb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)

        acb.saveBt.setOnClickListener{
            val contact = Contact(
                id = Random(System.currentTimeMillis()).nextInt(),
                name = acb.nameEt.text.toString(),
                address = acb.addressEt.text.toString(),
                phone = acb.phoneEt.text.toString(),
                email = acb.emailEt.text.toString(),
            )

            // result pra receber resultado é um intent vazia
            val resultsIntent = Intent()
            resultsIntent.putExtra(EXTRA_CONTACT, contact)
            setResult(RESULT_OK, resultsIntent)
            finish()
        }

    }
}