package br.edu.ifsp.scl.ads.pdm.mycontacts.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.edu.ifsp.scl.ads.pdm.mycontacts.R
import br.edu.ifsp.scl.ads.pdm.mycontacts.databinding.ActivityContactBinding
import br.edu.ifsp.scl.ads.pdm.mycontacts.model.Constant.EXTRA_CONTACT
import br.edu.ifsp.scl.ads.pdm.mycontacts.model.Constant.VIEW_CONTACT
import br.edu.ifsp.scl.ads.pdm.mycontacts.model.Contact
import kotlin.random.Random

class ContactActivity : AppCompatActivity() {

    private val acb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)

        // preenche os campos da tela se o contato não for nulo
        val receivedContact = intent.getParcelableExtra<Contact>(EXTRA_CONTACT)
        receivedContact?.let { _receivedContact ->
//            acb.nameEt.setText(_receivedContact.name)
//            acb.addressEt.setText(_receivedContact.address)
//            acb.emailEt.setText(_receivedContact.email)
//            acb.phoneEt.setText(_receivedContact.phone)

            with(acb) {
                nameEt.setText(_receivedContact.name)
                addressEt.setText(_receivedContact.address)
                emailEt.setText(_receivedContact.email)
                phoneEt.setText(_receivedContact.phone)
            }
        }
        // informa se é só visualização ou permite edição
        val viewContact = intent.getBooleanExtra(VIEW_CONTACT, false)
        if (viewContact) {
            acb.nameEt.isEnabled = false
            acb.addressEt.isEnabled = false
            acb.phoneEt.isEnabled = false
            acb.emailEt.isEnabled = false
            acb.saveBt.visibility = View.GONE
        }

        acb.saveBt.setOnClickListener{
            val contact = Contact(
                // se o id não for nulo, usa ele, senão cria um id novo
                id = receivedContact?.id?: Random(System.currentTimeMillis()).nextInt(),
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