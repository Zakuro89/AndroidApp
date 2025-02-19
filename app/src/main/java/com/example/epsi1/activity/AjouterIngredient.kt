package com.example.epsi1.activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.epsi1.R
import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AjouterIngredient : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_ingredient)

        val editIngredientName = findViewById<EditText>(R.id.editIngredientName)
        val editIngredientQuantity = findViewById<EditText>(R.id.editIngredientQuantity)
        val editIngredientUnit = findViewById<EditText>(R.id.editIngredientUnit)
        val buttonAddIngredient = findViewById<Button>(R.id.buttonAddIngredient)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        buttonAddIngredient.setOnClickListener {
            val name = editIngredientName.text.toString()
            val quantity = editIngredientQuantity.text.toString().toDoubleOrNull()
            val unit = editIngredientUnit.text.toString()

            if (name.isNotEmpty() && quantity != null && unit.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("ingredient_name", name)
                resultIntent.putExtra("ingredient_quantity", quantity)
                resultIntent.putExtra("ingredient_unit", unit)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs correctement", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}


