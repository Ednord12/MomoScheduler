package com.example.ea_create.momosheduler.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.ea_create.momosheduler.Models.Operation
import com.example.ea_create.momosheduler.R

class Operations_Adapter():BaseAdapter() {

   lateinit var operation:ArrayList<Operation>
    lateinit var context: Context

    constructor(context: Context, operationListe:ArrayList<Operation> ) : this() {
        this.context=context
        this.operation=operationListe

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater=LayoutInflater.from(context)

        var view=inflater.inflate(R.layout.operation_view,null)

        var Txt_Client=view.findViewById<TextView>(R.id.txt_lv_client)
        var Txt_Montant=view.findViewById<TextView>(R.id.txt_lv_montant)
        var Txt_Action=view.findViewById<TextView>(R.id.txt_lv_actionM)

        Txt_Client.text=operation[position].customer
        Txt_Montant.text=operation[position].solde
        when(operation[position].actionMode)
        {
            "Dépôt"->{Txt_Action.text="D"}
            "Retrait"->{Txt_Action.text="R"}
        }

        return  view


        }

    override fun getItem(position: Int): Any {

        return operation[position]
         }

    override fun getItemId(position: Int): Long {

        return position.toLong()

    }

    override fun getCount(): Int {
        return operation.size
      }
}