package com.example.epsi1.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.epsi1.R

class AjouterEtape : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ajouter_etape)

        val editStep = findViewById<EditText>(R.id.editStep)
        val buttonAddStep = findViewById<Button>(R.id.addStepButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        buttonAddStep.setOnClickListener{
            val description = editStep.text.toString()

            if(description.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("step_description", description)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()

            } else {
                Toast.makeText(this, "Veuillez remplir le champs !", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}