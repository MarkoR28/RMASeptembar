package com.rn7417.rmaseptembar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.rn7417.rmaseptembar.R
import com.rn7417.rmaseptembar.model.PizzaOrderModel
import com.squareup.picasso.Picasso

class OrderAdapter(private val context: Context,
                   private val dataSource: ArrayList<PizzaOrderModel>): BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_order, p2, false)

        // Get title element
        val titleTextView = rowView.findViewById(R.id.recipe_list_title) as TextView

// Get subtitle element
        val subtitleTextView = rowView.findViewById(R.id.recipe_list_subtitle) as TextView

// Get detail element
        val detailTextView = rowView.findViewById(R.id.recipe_list_detail) as TextView

// Get thumbnail element
        val thumbnailImageView = rowView.findViewById(R.id.recipe_list_thumbnail) as ImageView

        // 1
        val pizza = getItem(p0) as PizzaOrderModel

// 2
        titleTextView.text = "Size - " +pizza.pizzaSize + ", Crust - " + pizza.pizzaCrust
        subtitleTextView.text ="Toppings: " + pizza.toppings
        detailTextView.text = pizza.pizzaPart

// 3
        //Picasso.get().load(pizza.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)
        Picasso.get().load(pizza.imageUrl).placeholder(R.drawable.ic_pizza_foreground).into(thumbnailImageView)


        return rowView
    }

    override fun getItem(p0: Int): Any {
        return dataSource[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}