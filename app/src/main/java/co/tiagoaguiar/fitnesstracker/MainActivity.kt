package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var btnImc: LinearLayout
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.ic_baseline_wb_sunny_24,
                textStringId = R.string.imc,
                color = Color.GREEN
            )
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.ic_baseline_remove_red_eye_24,
                textStringId = R.string.tmb,
                color = Color.RED
            )
        )


        // 1) Layout XML
        // 2) A onde a recyclerview vai aparecer (tela principal, tela cheia)
        // 3) Logica - conectar o xml da celular dentro do recyclervuew + a sua quantidade de elementos dinamicos
        val adapter = MainAdapter(mainItems) { id ->
            when(id) {
                1 -> {
                    val intent = Intent(this@MainActivity, ImcActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(this@MainActivity, TmbActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)

        // Código padrão
        // Classe para administrar a recycleview e suas celular (os seus layouts de itens)
        // Adapter ->


//        btnImc = findViewById(R.id.btn_imc)

//        btnImc.setOnClickListener(View.OnClickListener {
//            // Navegar para proxima tela
//            val i = Intent(this, ImcActivity::class.java)
//                // Código padrão para abrir tela/atividade
//            startActivity(i)
//
//        })


    }

    private inner class MainAdapter(
        private val mainItems: List<MainItem>,
        private val onItemClickListener: (Int) -> Unit
        ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

        // 1 - Este metodos é responsavel por informar a recyclerview o layout xml especifico (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            // Criando a view para passar para o view holder
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        // 2 - Método disparado toda vez que houver uma rolagem na tela e for necessário trocar o
        // conteudo da celula
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        // 3 - Informar quantas celular essa listagem terá
        override fun getItemCount(): Int {
            return mainItems.size
        }

        // É a classe da celula em si, aqui vamos poder buscar as referenciar de cada celula
        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)
                val container: LinearLayout = itemView.findViewById(R.id.item_container)

                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)
                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }
            }
        }
    }


}