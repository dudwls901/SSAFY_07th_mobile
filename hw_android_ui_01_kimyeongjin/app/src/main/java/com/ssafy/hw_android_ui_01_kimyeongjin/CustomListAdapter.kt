package com.ssafy.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomListAdapter(context: Context, var items: List<Map<String, String>>, val layout: Int): ArrayAdapter<Map<String, String>>(context, layout, items) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(layout, null)
        view.findViewById<TextView>(R.id.tvIdx).text = "순번: ${ position }"
        view.findViewById<TextView>(R.id.tvId).text = items[position]["id"]
        view.findViewById<TextView>(R.id.tvName).text = items[position]["name"]
        return view
    }
}