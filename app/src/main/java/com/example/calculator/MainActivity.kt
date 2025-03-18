package com.example.calculator

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var primerNumero: Double = Double.NaN
    private var segundoNumero: Double = Double.NaN
    private var operacionActual: String = ""

    private lateinit var textViewTemporal: TextView
    private lateinit var textViewResultado: TextView
    private lateinit var formatoTextView: DecimalFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        formatoTextView = DecimalFormat("#.##########")
        textViewTemporal = findViewById(R.id.textViewTemporal)
        textViewResultado = findViewById(R.id.textViewResultado)
    }

    fun cambiarOperador(b: View) {
        calcular()
        val boton = b as Button
        operacionActual = when (boton.text.toString().trim()) {
            "÷" -> "/"
            "x" -> "*"
            else -> boton.text.toString().trim()
        }
        textViewResultado.text = "${formatoTextView.format(primerNumero)} $operacionActual"
        textViewTemporal.text = ""
    }

    fun seleccionarNumero(b: View) {
        val boton = b as Button
        if (textViewTemporal.text.toString().trim() == "0") {
            textViewTemporal.text = ""
        }
        textViewTemporal.append(boton.text.toString())
    }

    fun calcular() {
        if (!primerNumero.isNaN()) {
            segundoNumero = textViewTemporal.text.toString().toDouble()
            textViewTemporal.text = ""

            primerNumero = when (operacionActual) {
                "+" -> primerNumero + segundoNumero
                "-" -> primerNumero - segundoNumero
                "/" -> primerNumero / segundoNumero
                "*" -> primerNumero * segundoNumero
                "%" -> primerNumero % segundoNumero
                else -> primerNumero
            }
        } else {
            primerNumero = textViewTemporal.text.toString().toDouble()
        }
    }

    fun mostrarResultado(b: View) {
        calcular()
        textViewResultado.text = formatoTextView.format(primerNumero)
        textViewTemporal.text = "0"
    }

    fun limpiarTodo(b: View) {
        primerNumero = Double.NaN
        segundoNumero = Double.NaN
        operacionActual = ""
        textViewTemporal.text = "0"
        textViewResultado.text = "0"
    }

    fun limpiarParcialmente(b: View) {
        textViewTemporal.text = "0"
    }

    fun añadirComa(b: View) {
        if (!textViewTemporal.text.contains(".")) {
            textViewTemporal.append(".")
        }
    }
}
