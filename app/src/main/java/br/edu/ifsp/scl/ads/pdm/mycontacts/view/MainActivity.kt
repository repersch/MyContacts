package br.edu.ifsp.scl.ads.pdm.mycontacts.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.mycontacts.R
import br.edu.ifsp.scl.ads.pdm.mycontacts.adapter.ContactAdapter
import br.edu.ifsp.scl.ads.pdm.mycontacts.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.pdm.mycontacts.model.Constant.EXTRA_CONTACT
import br.edu.ifsp.scl.ads.pdm.mycontacts.model.Constant.VIEW_CONTACT
import br.edu.ifsp.scl.ads.pdm.mycontacts.model.Contact

class MainActivity : AppCompatActivity() {
        private val amb: ActivityMainBinding by lazy {
            ActivityMainBinding.inflate(layoutInflater)
        }

        // Data source
        private val contactList: MutableList<Contact> = mutableListOf()


//        private val contactAdapter: ArrayAdapter<String> by lazy {
//            ArrayAdapter(
//                this,
//                android.R.layout.simple_list_item_1,
//                contactList.map { it.toString() }
//            )
//        }

        private lateinit var contactAdapter: ContactAdapter

        private lateinit var carl: ActivityResultLauncher<Intent>


        override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)
            setContentView(amb.root)

            fillContactList()
            contactAdapter = ContactAdapter(this, contactList)
            amb.contactsLv.adapter = contactAdapter

            carl = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                // quando a tela chamada for fechada, faz tudo isso
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val contact = result.data?.getParcelableExtra<Contact>(EXTRA_CONTACT)
                    contact?.let { _contact ->
                        // se existe qualquer objeto na lista de contato com o mesmo id do contato recebido
                        if (contactList.any { it.id == _contact.id }) {
                            // alterar na posição
                            val position = contactList.indexOfFirst { it.id == _contact.id }
                            contactList[position] = _contact
                        } else {
                            contactList.add(_contact)
                        }
                        contactAdapter.notifyDataSetChanged()
                    }
                }
            }

            // informa que é na lista que vai aparecer o menu de contexto
            registerForContextMenu(amb.contactsLv)

            amb.contactsLv.onItemClickListener = object: AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val contact = contactList[position]
                    val contactIntent = Intent(this@MainActivity, ContactActivity::class.java)
                    contactIntent.putExtra(EXTRA_CONTACT, contact)
                    // dessa forma a intent é somente para visualização
                    contactIntent.putExtra(VIEW_CONTACT, true)
                    startActivity(contactIntent)
                }
            }
        }



        // menu de criação de novos itens
        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_main, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                // quando clica no botão add
                R.id.addContactMi -> {
                    // abre a tela de contato
                    carl.launch(Intent(this, ContactActivity::class.java))
                    true
                }
                else -> {
                    false
                }
            }
        }



        // menu de contexto (clicar e manter pressionado) - apagar e editar
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
           menuInflater.inflate(R.menu.context_menu_main, menu)
        }

        override fun onContextItemSelected(item: MenuItem): Boolean {
            val position = (item.menuInfo as AdapterContextMenuInfo).position
            return when(item.itemId) {
                R.id.removeContactMi -> {
                    // remove o contato
                    contactList.removeAt(position)
                    contactAdapter.notifyDataSetChanged()
                    true
                }
                R.id.editContactMi -> {
                    // chama a tela para editar o contato
                    val contact = contactList[position]
                    val contactIntent = Intent(this, ContactActivity::class.java)
                    // abre a mesma intent que adiciona contatos com os dados do contato selecionado
                    contactIntent.putExtra(EXTRA_CONTACT, contact)
                    // dessa forma indica para a intent que é possível a edição dos dados
                    contactIntent.putExtra(VIEW_CONTACT, false)
                    carl.launch(contactIntent)
                    true
                }
                else -> { false }
            }
        }

        private fun fillContactList() {
            for (i in 1..13) {
                contactList.add(
                    Contact(
                        id = i,
                        name = "Name $i",
                        address = "Address $i",
                        phone = "Phone $i",
                        email = "Email $i",
                    )
                )
            }
        }
    }
