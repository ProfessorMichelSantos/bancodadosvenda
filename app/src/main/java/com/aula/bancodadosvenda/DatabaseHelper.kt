package com.aula.bancodadosvenda

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "vendas.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE vendas (idvenda INTEGER PRIMARY KEY, descricaovenda TEXT, produtovendido TEXT)"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS vendas")
        onCreate(db)
    }

     // MÉTODOS PARA ADICIONAR NO BANCO DE DADOS

    fun insertVenda(venda: Venda) : Long {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put("idvenda", venda.idvenda)
            put("descricaovenda", venda.descricaovenda)
            put("produtovendido", venda.produtovendido)
        }
        val status = db.insert("vendas", null, values)

        return status
    }

    fun UpdateVenda(venda: Venda) : Int {

        val db = this.writableDatabase

        val contentvalues = ContentValues()

        //contentvalues.put("idvenda", venda.idvenda)
        contentvalues.put("descricaovenda", venda.descricaovenda)
        contentvalues.put("produtovendido", venda.produtovendido)

        val success = db.update("vendas", contentvalues, "idvenda="+venda.idvenda, null)

        db.close()
        return success
    }

    fun deleteVenda(idvenda: Int): Int{

        val db = this.writableDatabase

        //val contentvalues = ContentValues()
        //contentvalues.put("idvenda", idvenda)

        val success = db.delete("vendas", "idvenda="+idvenda.toString(), null )
        db.close()

        return success
    }

    fun selectVendas(): ArrayList<Venda> {

        val vendas = ArrayList<Venda>()
        val selectQuery = "SELECT * FROM vendas"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)


        if (cursor.moveToFirst()) {
            do {
                val venda = Venda(
                    idvenda = cursor.getInt(cursor.getColumnIndexOrThrow("idvenda")),
                    descricaovenda = cursor.getString(cursor.getColumnIndexOrThrow("descricaovenda")),
                    produtovendido = cursor.getString(cursor.getColumnIndexOrThrow("produtovendido"))
                )
                vendas.add(venda)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return vendas
    }

}
