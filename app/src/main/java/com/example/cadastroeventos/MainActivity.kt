package com.example.cadastroeventos

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtNome = findViewById<EditText>(R.id.edtNome)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtTelefone = findViewById<EditText>(R.id.edtTelefone)
        val edtIdade = findViewById<EditText>(R.id.edtIdade)
        val edtInstituicao = findViewById<EditText>(R.id.edtInstituicao)
        val edtObservacoes = findViewById<EditText>(R.id.edtObservacoes)

        val chkSoftware = findViewById<CheckBox>(R.id.chkSoftware)
        val chkIA = findViewById<CheckBox>(R.id.chkIA)
        val chkSeguranca = findViewById<CheckBox>(R.id.chkSeguranca)
        val chkMobile = findViewById<CheckBox>(R.id.chkMobile)
        val chkDados = findViewById<CheckBox>(R.id.chkDados)

        val campos = listOf(
            edtNome, edtEmail, edtTelefone, edtIdade, edtInstituicao, edtObservacoes
        )
        val caixas = listOf(chkSoftware, chkIA, chkSeguranca, chkMobile, chkDados)

        fun limparFormulario() {
            campos.forEach { campo ->
                campo.text.clear()
                campo.error = null
            }
            caixas.forEach { caixa -> caixa.isChecked = false }
            edtNome.clearFocus()
        }

        findViewById<Button>(R.id.btnLimpar).setOnClickListener {
            limparFormulario()
            Toast.makeText(this, "Campos limpos!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnCadastrar).setOnClickListener {
            val nome = edtNome.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val telefone = edtTelefone.text.toString().trim()
            val idadeTexto = edtIdade.text.toString().trim()
            val instituicao = edtInstituicao.text.toString().trim()
            val observacoes = edtObservacoes.text.toString().trim()

            val obrigatorios = listOf(
                edtNome to "Informe seu nome.",
                edtEmail to "Informe seu e-mail.",
                edtTelefone to "Informe seu telefone.",
                edtIdade to "Informe sua idade.",
                edtInstituicao to "Informe sua instituição."
            )
            val faltando = obrigatorios.firstOrNull { (campo, _) ->
                campo.text.toString().isBlank()
            }
            if (faltando != null) {
                faltando.first.error = faltando.second
                faltando.first.requestFocus()
                Toast.makeText(this, faltando.second, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.error = "Digite um e-mail válido."
                edtEmail.requestFocus()
                return@setOnClickListener
            }

            val idade = idadeTexto.toIntOrNull()
            if (idade == null || idade !in 1..120) {
                edtIdade.error = "Informe uma idade válida."
                edtIdade.requestFocus()
                return@setOnClickListener
            }

            val interesses = caixas.filter { it.isChecked }.map { it.text.toString() }
            if (interesses.isEmpty()) {
                Toast.makeText(this, "Selecione pelo menos uma área de interesse.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Salva os cadastros apenas neste dispositivo (não envia para um servidor).
            val preferencias = getSharedPreferences("CadastroEventos", MODE_PRIVATE)
            val registros = try {
                JSONArray(preferencias.getString("cadastros", "[]"))
            } catch (_: JSONException) {
                JSONArray()
            }
            val cadastro = JSONObject().apply {
                put("nome", nome)
                put("email", email)
                put("telefone", telefone)
                put("idade", idade)
                put("instituicao", instituicao)
                put("observacoes", observacoes)
                put("interesses", JSONArray(interesses))
                put("dataCadastro", System.currentTimeMillis())
            }
            registros.put(cadastro)
            preferencias.edit().putString("cadastros", registros.toString()).apply()

            AlertDialog.Builder(this)
                .setTitle("Cadastro realizado!")
                .setMessage(
                    "Participante: $nome\n" +
                            "E-mail: $email\n" +
                            "Áreas de interesse: ${interesses.joinToString(", ")}\n\n" +
                            "Cadastro salvo neste dispositivo."
                )
                .setPositiveButton("OK") { _, _ -> limparFormulario() }
                .show()
        }
    }
}
