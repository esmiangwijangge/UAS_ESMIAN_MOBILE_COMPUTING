package com.example.projectesmian

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ClubAdapter (var mContext: Context, var klubList: List<DataClub>):
    RecyclerView.Adapter<ClubAdapter.ListViewHolder>() {

    inner class ListViewHolder(var v: View) : RecyclerView.ViewHolder(v) {

        val imgTT = v.findViewById<ImageView>(R.id.logoclub)
        val  judulTT = v.findViewById<TextView>(R.id.JudulText)
        val asalTT = v.findViewById<TextView>(R.id.asalText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubAdapter.ListViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.databola, parent,false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int {
        return klubList.size
    }

    override fun onBindViewHolder(holder: ClubAdapter.ListViewHolder, position: Int) {
        val newList = klubList[position]
        holder.imgTT.loadImage(newList.gambarurl)
        holder.judulTT.text = newList.nmklub
        holder.asalTT.text = newList.asal

        holder.v.setOnClickListener {

            val desk = newList.deskripsi
            val judul = newList.nmklub
            val img = newList.gambarurl
            val asal = newList.asal

            val mIntent = Intent(mContext,  Deskripsi::class.java)
            mIntent.putExtra("DESKT", desk)
            mIntent.putExtra("JUDULT", judul)
            mIntent.putExtra("IMGT", img)
            mIntent.putExtra("ASALT", asal)
            mContext.startActivity(mIntent)
        }
    }



}