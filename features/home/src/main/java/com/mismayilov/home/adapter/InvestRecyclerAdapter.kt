package com.mismayilov.home.adapter

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mismayilov.core.utils.MyDiffUtil
import com.mismayilov.domain.entities.InvestModel
import com.mismayilov.home.R

class InvestRecyclerAdapter() : ListAdapter<InvestModel, InvestRecyclerAdapter.InvestViewHolder>(
    MyDiffUtil<InvestModel>(
        itemsTheSame = { oldItem, newItem -> oldItem == newItem },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.invest_item_design, parent, false)
        return InvestViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class InvestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val investName: TextView = itemView.findViewById(R.id.investName)
        private val investRating: TextView = itemView.findViewById(R.id.investRating)
        private val investOpenPrice: TextView = itemView.findViewById(R.id.investOpenPrice)
        private val investHighPrice: TextView = itemView.findViewById(R.id.investHighPrice)
        private val investLowPrice: TextView = itemView.findViewById(R.id.investLowPrice)
        private val investLastPrice: TextView = itemView.findViewById(R.id.investLastPrice)
        private val investImage: ImageView = itemView.findViewById(R.id.investImage)

        fun bind(investModel: InvestModel) {
            investName.text = investModel.companyName
            investRating.text = investModel.rating.toString()
            investOpenPrice.text = investModel.openPrice
            investHighPrice.text = investModel.highPrice
            investLowPrice.text = investModel.lowPrice
            investLastPrice.text = investModel.lastPrice
            if (investModel.direction == "down") investImage.setImageResource(R.drawable.down)
        }
    }
}