package com.rn7417.rmaseptembar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rn7417.rmaseptembar.activity.AddPizzaActivity
import com.rn7417.rmaseptembar.adapter.OrderAdapter
import com.rn7417.rmaseptembar.fragments.CheckoutFragment
import com.rn7417.rmaseptembar.fragments.FirstDialogFragment
import com.rn7417.rmaseptembar.fragments.SecondDialogFragment
import com.rn7417.rmaseptembar.fragments.ThirdDialogFragment
import com.rn7417.rmaseptembar.model.PizzaOrderModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import java.io.Serializable


class MainActivity : AppCompatActivity(R.layout.activity_main),Serializable, SecondDialogFragment.NoticeDialogListener, CheckoutFragment.NoticeDialogListenerCh, ThirdDialogFragment.NoticeDialogListener3, FirstDialogFragment.NoticeDialogListener1  {

    var pizzaSize = ""
    var pizzaCrust = ""
    var listItems: MutableList<String> = arrayListOf()
    var items = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onOkPressed(index: String) {
        val fragment2 = SecondDialogFragment()
        fragment2.show(supportFragmentManager, "secondTag")
    }

    @SuppressLint("ResourceType")
    override fun onDialogSelect(dialog: String) {
        if (dialog == "0") pizzaSize = "Small"
        else if(dialog == "1") pizzaSize = "Medium"
        else if(dialog == "2") pizzaSize = "Large"
        else pizzaSize = "Party"

        val fragment3 = ThirdDialogFragment()
        fragment3.show(supportFragmentManager, "thirdTag")
    }

    override fun onDialogSelect3(index: String) {
        if (index == "0") pizzaCrust = "Thin"
        else if(index == "1") pizzaCrust = "Thick"
        else if(index == "2") pizzaCrust = "Deep Dish"
        else pizzaCrust = "Stuffed Crust"

        println("SIZE" + pizzaSize)
        println("CRUST" + pizzaCrust)

        val intent = Intent(this, AddPizzaActivity::class.java)
        intent.putExtra("PIZZA_SIZE", pizzaSize)
        intent.putExtra("PIZZA_CRUST", pizzaCrust)
        intent.putExtra("ALL_ORDERS", items)
        intent.putExtra("FROM", "button")
        intent.putExtra("SELECTED_ID", "-1")
        startActivity(intent)
    }
    override fun onOkPressedCheckout(index: String) {
        items.removeAll(items)
        finish();
        startActivity(getIntent());
    }

    private fun init(){
        addPizzaBtn.setOnClickListener{
            val fragment = FirstDialogFragment()
            fragment.show(supportFragmentManager, "firstTag")
        }
        initLv()
        initCheckout()
    }
    private fun initCheckout(){
        if(items.size < 1) noOrdersTv.text = "Your shopping cart is currently empty"
        val price =  getIntent().getStringExtra("PRICE")
        if(items.size < 1) totalAmountValueTv.text = "0 $"
        else if(!price.isNullOrEmpty()) totalAmountValueTv.text = price.toString() + " $"
        else totalAmountValueTv.text = "0 $"
        checkoutBtn.setOnClickListener{
            if(items.size < 1) {
                Toast.makeText(
                    applicationContext,
                    "Your shopping card must not be empty in order to proceed with checkout",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                val fragment = CheckoutFragment()
                fragment.show(supportFragmentManager, "checkoutTag")
            }
        }
    }
    private fun initLv(){
        val isSafe = getIntent().getStringExtra("ISSAFE")
        if(isSafe == "yes") {
            items = getIntent().getStringArrayListExtra("ALL_ORDERS")
            // if (items.size > 0) listItems = items.toMutableList()
            var listView = ordersLv

            /*val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
            listView.adapter = adapter*/

            var itemsList = createItemsList(items)
            val adapter = OrderAdapter(this, itemsList)
            listView.adapter = adapter

            listView.setOnItemClickListener{ parent, view, position, id ->
                val intent = Intent(this, AddPizzaActivity::class.java)
                intent.putExtra("PIZZA_SIZE",  getIntent().getStringExtra("PIZZA_SIZE").toString())
                intent.putExtra("PIZZA_CRUST", getIntent().getStringExtra("PIZZA_CRUST").toString())
                intent.putExtra("ALL_ORDERS", items)
                intent.putExtra("SELECTED_ID", position.toString())
                intent.putExtra("FROM", "selection")
                startActivity(intent)
            }
        }
    }
    private fun createItemsList(items: ArrayList<String>): ArrayList<PizzaOrderModel> {
        var listModel = ArrayList<PizzaOrderModel>()
        for(i in items){
            var splittedItem = i.split("-").toTypedArray()
            var pizzaSize = splittedItem.get(0)
            var pizzaCrust = splittedItem.get(1)
            var pizzaPart = splittedItem.get(2)
            var toppings = splittedItem.get(3)
            var image = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Pizza.svg/1200px-Pizza.svg.png"
            var model = PizzaOrderModel(pizzaSize, pizzaCrust, pizzaPart, toppings, image)
            listModel.add(model)
        }
        return listModel

    }
}
