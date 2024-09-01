package com.aula.bancodadosvenda

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnAdicionar : ImageButton
    private lateinit var btnAtualizar : ImageButton
    private lateinit var btnApagar : ImageButton
    private lateinit var btnSelect : ImageButton

    private lateinit var sqLitehelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sqLitehelper = DatabaseHelper(this)

        btnAdicionar = findViewById(R.id.btnimginsert)
        btnAtualizar = findViewById(R.id.btnimgupdate)
        btnApagar = findViewById(R.id.btnimgdelete)
        btnSelect = findViewById(R.id.btnimgselect)

        btnAdicionar.setOnClickListener{
            adicionarvenda()
        }

        btnAtualizar.setOnClickListener{
            atualizarvenda()
        }

        btnApagar.setOnClickListener {
            apagarvenda()
        }

        btnSelect.setOnClickListener{
            listarvendas()
        }
    }

    private fun adicionarvenda(){

        var txtidvenda : EditText = findViewById(R.id.idvenda)
        var txtdescricaovenda : EditText = findViewById(R.id.txtdescricaovenda)
        var txtprodutovendido : EditText = findViewById(R.id.txtprodutovendido)


        val venda = Venda(idvenda = txtidvenda.text.toString().toInt(), descricaovenda = txtdescricaovenda.text.toString(), produtovendido = txtprodutovendido.text.toString())

        val status = sqLitehelper.insertVenda(venda)

        if (status > -1)
        {
            Toast.makeText(this,"Adicionado com sucesso", Toast.LENGTH_LONG).show()

            txtdescricaovenda.setText("")
            txtprodutovendido.setText("")
            //getVendas()
        }else{
            Toast.makeText(this, "Não Salvo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun atualizarvenda()
    {
        var txtidvenda : EditText = findViewById(R.id.idvenda)
        var txtdescricaovenda : EditText = findViewById(R.id.txtdescricaovenda)
        var txtprodutovendido : EditText = findViewById(R.id.txtprodutovendido)

        val venda = Venda(idvenda =  txtidvenda.text.toString().toInt(), descricaovenda = txtdescricaovenda.text.toString(), produtovendido = txtprodutovendido.text.toString())

        val status = sqLitehelper.UpdateVenda(venda)

        if (status > -1)
        {
            Toast.makeText(this,"Atualizado com sucesso", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"falha na atualização", Toast.LENGTH_LONG).show()
        }
    }

    private fun apagarvenda()
    {
        var txtidvenda : EditText = findViewById(R.id.idvenda)
        val idvenda = txtidvenda.text.toString().toInt()
        val status = sqLitehelper.deleteVenda(idvenda)

        if (status > -1)
        {
            Toast.makeText(this,"Excluído com sucesso", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Falha na exclusão", Toast.LENGTH_LONG).show()
        }

    }

    private fun listarvendas()
    {


        val listView = findViewById<ListView>(R.id.listView)
        val alist = sqLitehelper.selectVendas()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, alist)
        listView.adapter = adapter

    }

}