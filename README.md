# 📺 YouTube Searches App

Aplicativo Android desenvolvido em **Java** para salvar, editar, compartilhar e excluir consultas de busca favoritas do **YouTube**.  
Permite que o usuário associe um **identificador (tag)** a uma consulta, facilitando o acesso rápido a pesquisas recorrentes.

<img width="1328" height="850" alt="image" src="https://github.com/user-attachments/assets/96c517d2-e00e-4987-bb43-8477bbc001e6" />


---

## 🚀 Funcionalidades

- ✅ **Salvar consultas** com identificadores personalizados (tag + busca).  
- ✅ **Editar consultas existentes**, atualizando tag e busca.  
- ✅ **Compartilhar consultas** facilmente com outros aplicativos.  
- ✅ **Remover consultas** após confirmação do usuário.  
- ✅ **Abrir consulta no navegador/YouTube** ao clicar no item da lista.  

---

## 🛠️ Tecnologias utilizadas

- **Java** para desenvolvimento Android.  
- **RecyclerView** (`androidx.recyclerview.widget`) para exibição das consultas em lista.  
- **ImageButton** para salvar rapidamente novas buscas.  
- **SharedPreferences** para persistência de dados simples (tags e buscas).  
- **AlertDialog** para exibir opções de edição, compartilhamento e exclusão.  
- **Intents implícitos** para abrir resultados no navegador.  

---

## 📂 Estrutura básica

- `MainActivity.java` → Lógica principal do app (UI + eventos).  
- `Adapter.java` → Controla os itens exibidos no RecyclerView.  
- `SearchPreferences.java` → Classe utilitária para salvar e recuperar dados do usuário com SharedPreferences.  
- `res/layout/` → Layouts da interface (Activity principal, item da lista).  
- `res/values/strings.xml` → Textos e mensagens utilizadas no app.  

---

## 📱 Como funciona

1. O usuário insere uma **tag** e uma **consulta**.  
2. Ao clicar no botão salvar, os dados são gravados em **SharedPreferences**.  
3. As consultas aparecem em uma lista rolável (RecyclerView).  
4. Ao tocar em uma consulta:  
   - 🔍 A busca é aberta no navegador/YouTube.  
5. Ao segurar uma consulta (long press):  
   - ✏️ Editar consulta.  
   - 📤 Compartilhar consulta.  
   - ❌ Excluir consulta.  

---

## 🎯 Exemplo de uso

- Tag: `Música`  
- Consulta: `Rock anos 80`  
- Resultado: ao clicar, o app abre o YouTube já pesquisando **"Rock anos 80"**.  

---

## 📖 Base teórica

Este projeto foi desenvolvido como prática da disciplina **Programação para Dispositivos Móveis**.  
O foco é demonstrar o uso de:  
- **Persistência de dados com SharedPreferences**.  
- **Componentes Android (RecyclerView, ImageButton, AlertDialog, Intents)**.  

---

## 👨‍💻 Autor

Projeto desenvolvido no âmbito acadêmico da **Universidade Federal de Rondônia (UNIR)**,  
baseado no material do professor **Dr. Lucas Marques da Cunha**.

