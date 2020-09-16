package com.rn7417.rmaseptembar.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.rn7417.rmaseptembar.MainActivity
import com.rn7417.rmaseptembar.R
import kotlinx.android.synthetic.main.activity_add_pizza.*
import java.io.Serializable

class AddPizzaActivity : AppCompatActivity(R.layout.activity_add_pizza), Serializable {

    val priceCheese = 3
    val priceMushrooms = 4
    val priceBacon = 8
    val priceOnion = 2
    val priceOlives = 3
    val priceChicken = 10
    val priceTomato = 4
    val priceHam = 8

    var arr = arrayOf<String>()

    var selectedRadio = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }
    fun init(){
        fillPassedData()
        checkboxListener()
    }
    private fun fillPassedData(){
        pizzaSizeEt.setText(getIntent().getStringExtra("PIZZA_SIZE"), TextView.BufferType.EDITABLE)
        pizzaCrustEt.setText(getIntent().getStringExtra("PIZZA_CRUST"), TextView.BufferType.EDITABLE)
        val index = getIntent().getStringExtra("SELECTED_ID")
        if(index.toInt() > -1){
            val items = getIntent().getStringArrayListExtra("ALL_ORDERS")
            fillForEdit(items)
        }
    }
    private fun checkboxListener(){
        wholerb.setOnClickListener{
            if(selectedRadio != "WHOLE") {
                selectedRadio = "WHOLE"
                leftTf.text = ""
                rightTf.text = ""
                arr = arrayOf<String>()
                uncheckBoxes()
            }
        }
        leftrb.setOnClickListener{
            if(selectedRadio != "LEFT") {
                selectedRadio = "LEFT"
                wholeTf.text = ""
                rightTf.text = ""
                arr = arrayOf<String>()
                uncheckBoxes()
            }
        }
        rightrb.setOnClickListener{
            if(selectedRadio != "RIGHT") {
                selectedRadio = "RIGHT"
                wholeTf.text = ""
                leftTf.text = ""
                arr = arrayOf<String>()
                uncheckBoxes()
            }
        }
        cheeseCb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) addToPizza(selectedRadio, "cheese", true)
            else removeFromPizza(selectedRadio, "cheese")
        }
        mushroomsCb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) addToPizza(selectedRadio, "mushrooms", true)
            else removeFromPizza(selectedRadio, "mushrooms")
        }
        baconCb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) addToPizza(selectedRadio, "bacon", true)
            else removeFromPizza(selectedRadio, "bacon")
        }
        onionCb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) addToPizza(selectedRadio, "onion", true)
            else removeFromPizza(selectedRadio, "onion")
        }
        olivesCb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) addToPizza(selectedRadio, "olives", true)
            else removeFromPizza(selectedRadio, "olives")
        }
        chickenCb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) addToPizza(selectedRadio, "chicken", true)
            else removeFromPizza(selectedRadio, "chicken")
        }
        tomatoCb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) addToPizza(selectedRadio, "tomato", true)
            else removeFromPizza(selectedRadio, "tomato")
        }
        hamCb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) addToPizza(selectedRadio, "ham", true)
            else removeFromPizza(selectedRadio, "ham")
        }
        addToChartBtn.setOnClickListener{
            if(selectedRadio == "") Toast.makeText(applicationContext, "You must select one pizza option",Toast.LENGTH_SHORT).show()
            else {
                if (arr.size > 2) {
                    val intent = Intent(this, MainActivity::class.java)

                    var toppings = "";
                    for(i in arr) toppings += i + ", "

                    intent.putExtra("PIZZA_SIZE", pizzaSizeEt.text.toString())
                    intent.putExtra("PIZZA_CRUST", pizzaCrustEt.text.toString())
                    intent.putExtra("PIZZA_PART", selectedRadio)
                    intent.putExtra("PIZZA_TOPPINGS", toppings)

                    val items = getIntent().getStringArrayListExtra("ALL_ORDERS")

                    val index = getIntent().getStringExtra("SELECTED_ID")
                    if(index.toInt() == -1){
                        items.add(pizzaSizeEt.text.toString() + " - " + pizzaCrustEt.text.toString() + " - " + selectedRadio + " - " + toppings)
                        intent.putExtra("ALL_ORDERS", items)
                    }else{
                        items.removeAt(index.toInt())
                        items.add(pizzaSizeEt.text.toString() + " - " + pizzaCrustEt.text.toString() + " - " + selectedRadio + " - " + toppings)
                        intent.putExtra("ALL_ORDERS", items)
                    }

                    val price = calculateAmount(items)
                    intent.putExtra("PRICE", price.toString())

                    intent.putExtra("ISSAFE", "yes")

                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "You must select at least 3 toppings",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        cancelOrderBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ISSAFE", "yes")

            val items = getIntent().getStringArrayListExtra("ALL_ORDERS")
            val index = getIntent().getStringExtra("SELECTED_ID")
            if(index.toInt() > -1)
                items.removeAt(index.toInt())
            intent.putExtra("ALL_ORDERS", items)
            println(items)
            val price = calculateAmount(items)
            intent.putExtra("PIZZA_SIZE", pizzaSizeEt.text.toString())
            intent.putExtra("PIZZA_CRUST", pizzaCrustEt.text.toString())
            intent.putExtra("PRICE", price.toString())
            startActivity(intent)
        }
    }
    private fun addToPizza(selectedRadio: String, selectedCheckBox: String, showToast: Boolean){
        if(showToast)
            Toast.makeText(applicationContext, "Added " + selectedCheckBox,Toast.LENGTH_SHORT).show()
        arr = append(arr, selectedCheckBox)
        var textViewText = ""
        for(i in arr){
            textViewText+=i + ", "
        }
        if(selectedRadio == "WHOLE"){
            wholeTf.text =textViewText
        }else if(selectedRadio == "LEFT"){
            leftTf.text = textViewText
        }else if(selectedRadio == "RIGHT"){
            rightTf.text = textViewText
        }
    }
    private fun removeFromPizza(selectedRadio: String, selectedCheckBox: String){
        Toast.makeText(applicationContext, "Removed " + selectedCheckBox,Toast.LENGTH_SHORT).show()
        arr = remove(arr, selectedCheckBox)
        var textViewText = ""
        for(i in arr){
            textViewText+=i + ", "
        }
        if(selectedRadio == "WHOLE"){
            wholeTf.text =textViewText
        }else if(selectedRadio == "LEFT"){
            leftTf.text = textViewText
        }else if(selectedRadio == "RIGHT"){
            rightTf.text = textViewText
        }
    }
    private fun append(arr: Array<String>, element: String): Array<String> {
        val list: MutableList<String> = arr.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }
    private fun remove(arr: Array<String>, element: String): Array<String> {
        val list: MutableList<String> = arr.toMutableList()
        list.remove(element)
        return list.toTypedArray()
    }
    private fun uncheckBoxes(){
        cheeseCb.isChecked = false
        mushroomsCb.isChecked = false
        baconCb.isChecked = false
        onionCb.isChecked = false
        olivesCb.isChecked = false
        chickenCb.isChecked = false
        tomatoCb.isChecked = false
        hamCb.isChecked = false
    }

    private fun calculateAmount(items: ArrayList<String>): Int {
        var totalPrice = 0
        for(i in items){
            var price = 0
            if(i.contains("cheese", true)) price+= priceCheese
            if(i.contains("mushrooms", true)) price+= priceMushrooms
            if(i.contains("bacon", true)) price+= priceBacon
            if(i.contains("onion", true)) price+= priceOnion
            if(i.contains("olives", true)) price+= priceOlives
            if(i.contains("chicken", true)) price+= priceChicken
            if(i.contains("tomato", true)) price+= priceTomato
            if(i.contains("ham", true)) price+= priceHam
            if(!i.contains("WHOLE", true)) price /= 2
            totalPrice+=price
        }
        return totalPrice
    }
    private fun fillForEdit(items: ArrayList<String>){
        val index = getIntent().getStringExtra("SELECTED_ID")
        var item = items.get(index.toInt())
        println(item.contains("WHOLE", true))
        if(item.contains("WHOLE", true)) {
            radiobrnGroup.check(wholerb.id)
            selectedRadio = "WHOLE"
        }
        else if(item.contains("LEFT", true)) {
            radiobrnGroup.check(leftrb.id)
            selectedRadio = "LEFT"
        }
        else if(item.contains("RIGHT", true)) {
            radiobrnGroup.check(rightrb.id)
            selectedRadio = "RIGHT"
        }

        if(item.contains("cheese", true)) {
            cheeseCb.isChecked = true
            addToPizza(selectedRadio, "cheese", false)
        }
        if(item.contains("mushrooms", true)){
            mushroomsCb.isChecked = true
            addToPizza(selectedRadio, "mushrooms", false)
        }
        if(item.contains("bacon", true)){
            baconCb.isChecked = true
            addToPizza(selectedRadio, "bacon", false)
        }
        if(item.contains("onion", true)){
            onionCb.isChecked = true
            addToPizza(selectedRadio, "onion", false)
        }
        if(item.contains("olives", true)){
            olivesCb.isChecked = true
            addToPizza(selectedRadio, "olives", false)
        }
        if(item.contains("chicken", true)){
            chickenCb.isChecked = true
            addToPizza(selectedRadio, "chicken", false)
        }
        if(item.contains("tomato", true)){
            tomatoCb.isChecked = true
            addToPizza(selectedRadio, "tomato", false)
        }
        if(item.contains("ham", true)){
            hamCb.isChecked = true
            addToPizza(selectedRadio, "ham", false)
        }
    }


}