package co.tiagoaguiar.fitnesstracker

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

class ImcActivity : AppCompatActivity() {
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.edit_imc_weight)
        editHeight = findViewById(R.id.edit_imc_height)

        var btnSend: Button = findViewById(R.id.btn_imc_send)
        btnSend.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_LONG).show()
                // Esse return indica que o Kotlin precisa "matar" esse bloco de função
                return@setOnClickListener
            }

            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()

            val result = calculateImc(weight, height)

            val imcResponseId = imcResponse(result)

            // Vai pegar a string e formatar com o parametro que estou passando
            val title = getString(R.string.imc_response, result)

            // Criando dialog - classe construtora do dialog
            // Criei na mesma ideia do shared values, usando apply
            AlertDialog.Builder(this).apply {
                // Atribuindo propriedades
                setTitle(title)
                setMessage(imcResponseId)
                setPositiveButton(android.R.string.ok) { _, _ ->

                }
                create()
            }.show()

            // Service é usado para manipular recursos do android
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        }
    }

    private fun calculateImc(weight: Int, height: Int): Double {
        // peso / (altura * altura)
        return weight / ( (height / 100.0) * (height / 100.0) )
    }

    // Esta anotação serve para indicar queo retorno deve ser de um recurso string
    @StringRes
    private fun imcResponse(imc: Double): Int {
        /*
        * O retorno é um Inteiro pois ele é uma referencia transformada em inteiro, exemplo:
        *
        *    class R {
        *      class string {
        *          imc_severely_low_weight = 56416545
        *          imc_very_low_weight = 1688787941
        *          ...
        *      }
        *      class layout {
        *          ...
        *      }
        *   }
        */
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }
    }

    private fun validate(): Boolean {
        // Não pode inserir valores nulos/vazios
        // Não pode inserir/começar com 0

        return (
               editWeight.text.toString().isNotEmpty()
            && editHeight.text.toString().isNotEmpty()
            && !editWeight.text.toString().startsWith("0")
            && !editHeight.text.toString().startsWith("0")
        )
    }
}