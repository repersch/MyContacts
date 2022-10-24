package br.edu.ifsp.scl.ads.pdm.mycontacts.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.pdm.mycontacts.R
import br.edu.ifsp.scl.ads.pdm.mycontacts.databinding.TileContactBinding
import br.edu.ifsp.scl.ads.pdm.mycontacts.model.Contact

// quando coloca val ou var antes ele vira um parâmetro e um atributo da classe
// nesse caso context é só um parâmetro
class ContactAdapter(
    context: Context,
    private val contactList: MutableList<Contact>
): ArrayAdapter<Contact>(context, R.layout.tile_contact, contactList) {

    private data class TileContactHorder(val nameTv: TextView, val emailTv: TextView)
    private lateinit var tcb: TileContactBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contact = contactList[position]
        var contactTileView = convertView

        // outra forma de fazer
//        var view: View = null

        // quando sai desse if a contactTileView não é mais nula
        if (contactTileView == null) {
            // inflo uma nova célula
            tcb = TileContactBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            // outra forma de fazer a mesma coisa, utilizando o método antigo (com viewBinding)
//            val view = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
//                R.layout.tile_contact,
//                parent,
//                false
//            )

            contactTileView = tcb.root
            val tileContactHolder = TileContactHorder(tcb.nameTv, tcb.emailTv)
            contactTileView.tag = tileContactHolder
        }

        // melhorando o desempenho do app, dessa forma não é necessário buscar quem é o textview de cada atributo
        with (contactTileView.tag as TileContactHorder) {
            nameTv.text = contact.name
            emailTv.text = contact.email
        }

        tcb.nameTv.text = contact.name
        tcb.emailTv.text = contact.email

        // segunda forma de fazer, é a mesma coisa que o de cima, com viewBinding
        // nesse caso o return é a view
//        view.findViewById<TextView>(R.id.nameTv).text = contact.name
//        view.findViewById<TextView>(R.id.emailTv).text = contact.email



        return contactTileView
    }

}