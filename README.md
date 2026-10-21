# Cadastro de Eventos Acadêmicos (Android)

Aplicativo Android para inscrição de participantes em eventos acadêmicos. O usuário preenche um formulário, o app valida os dados e salva o cadastro no próprio dispositivo.

## Funcionalidades
- Formulário com nome, e-mail, telefone, idade, instituição de ensino e observações.
- Seleção de áreas de interesse: Desenvolvimento de Software, Inteligência Artificial, Segurança da Informação, Desenvolvimento Mobile e Ciência de Dados.
- Validações: campos obrigatórios, formato de e-mail, idade entre 1 e 120 e pelo menos uma área de interesse.
- Mensagem de confirmação após o cadastro e botão para limpar o formulário.
- Armazenamento local dos cadastros em formato JSON (SharedPreferences), sem envio para servidor.

## Tecnologias
Kotlin · Android (layouts XML, Material Design, ConstraintLayout) · Gradle (Kotlin DSL) · minSdk 24

## Estrutura

app/src/main/
├── java/com/example/cadastroeventos/MainActivity.kt   # lógica e validações
├── res/layout/activity_main.xml                       # tela do formulário
├── res/values/                                        # textos, cores e temas
└── AndroidManifest.xml


## Como executar
1. Instale o [Android Studio](https://developer.android.com/studio).
2. Clone o repositório e abra a pasta do projeto no Android Studio.
3. Aguarde o Gradle sincronizar.
4. Execute em um emulador ou em um celular com depuração USB (botão *Run*).
