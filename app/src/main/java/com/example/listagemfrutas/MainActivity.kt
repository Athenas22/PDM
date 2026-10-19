package com.example.listagemfrutas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.listagemfrutas.ui.theme.ListagemFrutasTheme

data class Produto(
    val nome: String,
    val preco: Float,
    val imagemResId: Int
)

val produtos = arrayListOf(
    Produto(
        nome = "Banana",
        preco = 4.99f,
        imagemResId = R.drawable.banana
    ),
    Produto(
        nome = "Uva",
        preco = 9.99f,
        imagemResId = R.drawable.uva
    ),
    Produto(
        nome = "Laranja",
        preco = 3.99f,
        imagemResId = R.drawable.laranja
    ),
    Produto(
        nome = "Maçã",
        preco = 10.99f,
        imagemResId = R.drawable.maca
    ),
    Produto(
        nome = "Jaca",
        preco = 7.99f,
        imagemResId = R.drawable.jaca
    ),
    Produto(
        nome = "Ameixa",
        preco = 8.99f,
        imagemResId = R.drawable.ameixa
    ),
    Produto(
        nome = "Morango",
        preco = 7.99f,
        imagemResId = R.drawable.morango
    ),
    Produto(
        nome = "Abacaxi",
        preco = 5.99f,
        imagemResId = R.drawable.abacaxi
    ),
    Produto(
        nome = "Kiwi",
        preco = 24.99f,
        imagemResId = R.drawable.kiwi
    )
)

enum class Tela {
    LOGIN,
    LISTAGEM,
    CADASTRO
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ListagemFrutasTheme {
                Aplicacao()
            }
        }
    }
}

@Composable
fun Aplicacao() {

    var telaAtual by rememberSaveable {
        mutableStateOf(Tela.LOGIN)
    }

    val listaProdutos = remember {
        mutableStateListOf<Produto>().apply {
            addAll(produtos)
        }
    }

    when (telaAtual) {

        Tela.LOGIN -> {
            TelaLogin(
                onLoginSucesso = {
                    telaAtual = Tela.LISTAGEM
                }
            )
        }

        Tela.LISTAGEM -> {
            ListaProdutos(
                produtos = listaProdutos,
                onAdicionarProduto = {
                    telaAtual = Tela.CADASTRO
                }
            )
        }

        Tela.CADASTRO -> {
            TelaCadastro(
                onSalvar = { nome, preco ->
                    listaProdutos.add(
                        Produto(
                            nome = nome,
                            preco = preco,
                            imagemResId = R.drawable.banana
                        )
                    )

                    telaAtual = Tela.LISTAGEM
                },
                onCancelar = {
                    telaAtual = Tela.LISTAGEM
                }
            )
        }
    }
}

@Composable
fun TelaLogin(
    onLoginSucesso: () -> Unit
) {

    var login by rememberSaveable {
        mutableStateOf("")
    }

    var senha by rememberSaveable {
        mutableStateOf("")
    }

    var senhaVisivel by rememberSaveable {
        mutableStateOf(false)
    }

    var erro by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF4F8F4)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = Color(0xFF388E3C)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E5D34)
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            OutlinedTextField(
                value = login,
                onValueChange = {
                    login = it
                    erro = false
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Login")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color(0xFF388E3C)
                    )
                },
                singleLine = true,
                isError = erro
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            OutlinedTextField(
                value = senha,
                onValueChange = {
                    senha = it
                    erro = false
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Senha")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color(0xFF388E3C)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            senhaVisivel = !senhaVisivel
                        }
                    ) {
                        Icon(
                            imageVector = if (senhaVisivel) {
                                Icons.Default.VisibilityOff
                            } else {
                                Icons.Default.Visibility
                            },
                            contentDescription = null,
                            tint = Color(0xFF388E3C)
                        )
                    }
                },
                visualTransformation = if (senhaVisivel) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                singleLine = true,
                isError = erro
            )

            if (erro) {

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "Login ou senha inválidos.",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Button(
                onClick = {

                    if (
                        login == "admin" &&
                        senha == "123456"
                    ) {
                        onLoginSucesso()
                    } else {
                        erro = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
            ) {
                Text("Entrar")
            }
        }
    }
}

@Composable
fun ListaProdutos(
    produtos: List<Produto>,
    modifier: Modifier = Modifier,
    onAdicionarProduto: (() -> Unit)? = null
) {

    var textSearch by remember {
        mutableStateOf("")
    }

    val produtosFiltrados = produtos.filter { produto ->
        produto.nome.contains(
            textSearch,
            ignoreCase = true
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFFF4F8F4),
        floatingActionButton = {
            if (onAdicionarProduto != null) {
                FloatingActionButton(
                    onClick = onAdicionarProduto,
                    containerColor = Color(0xFF43A047),
                    contentColor = Color.White,
                    modifier = Modifier.size(52.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar produto"
                    )
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            OutlinedTextField(
                value = textSearch,
                onValueChange = {
                    textSearch = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 14.dp
                    ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFF388E3C)
                    )
                },
                placeholder = {
                    Text(
                        text = "Pesquisar"
                    )
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            if (produtosFiltrados.isEmpty()) {

                Text(
                    text = "Nenhum produto encontrado.",
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF555555)
                )

            } else {

                LazyColumn {

                    items(produtosFiltrados) { produto ->

                        ItemProduto(produto)

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemProduto(produto: Produto) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 12.dp,
                end = 12.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(
                    id = produto.imagemResId
                ),
                contentDescription = produto.nome,
                modifier = Modifier.size(68.dp)
            )

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column {

                Text(
                    text = produto.nome,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E5D34)
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "R$ %.2f".format(produto.preco),
                    color = Color(0xFF388E3C)
                )
            }
        }
    }
}

@Composable
fun TelaCadastro(
    onSalvar: (String, Float) -> Unit,
    onCancelar: () -> Unit
) {

    var nome by rememberSaveable {
        mutableStateOf("")
    }

    var preco by rememberSaveable {
        mutableStateOf("")
    }

    var erroNome by rememberSaveable {
        mutableStateOf(false)
    }

    var erroPreco by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF4F8F4)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {

            Text(
                text = "Cadastro de Produto",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E5D34)
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                    erroNome = false
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Nome")
                },
                singleLine = true,
                isError = erroNome
            )

            if (erroNome) {

                Text(
                    text = "Informe o nome do produto.",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            OutlinedTextField(
                value = preco,
                onValueChange = {
                    preco = it
                    erroPreco = false
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Preço")
                },
                singleLine = true,
                isError = erroPreco
            )

            if (erroPreco) {

                Text(
                    text = "Informe um preço válido.",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                OutlinedButton(
                    onClick = onCancelar,
                    modifier = Modifier.height(44.dp)
                ) {
                    Text("Cancelar")
                }

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Button(
                    onClick = {

                        val precoNumerico = preco
                            .replace(",", ".")
                            .toFloatOrNull()

                        erroNome = nome.isBlank()
                        erroPreco =
                            precoNumerico == null ||
                                    precoNumerico <= 0f

                        if (
                            !erroNome &&
                            !erroPreco &&
                            precoNumerico != null
                        ) {

                            onSalvar(
                                nome.trim(),
                                precoNumerico
                            )
                        }
                    },
                    modifier = Modifier.height(44.dp)
                ) {
                    Text("Salvar")
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun TelaLoginPreview() {

    ListagemFrutasTheme {

        TelaLogin(
            onLoginSucesso = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ListaProdutosPreview() {

    ListagemFrutasTheme {

        ListaProdutos(
            produtos = produtos
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun TelaCadastroPreview() {

    ListagemFrutasTheme {

        TelaCadastro(
            onSalvar = { _, _ -> },
            onCancelar = {}
        )
    }
}
