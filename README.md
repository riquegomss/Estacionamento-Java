# 🚗 Sistema de Gestão de Estacionamento

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/Eclipse%20IDE-2C2255?style=for-the-badge&logo=eclipse&logoColor=white" alt="Eclipse" />
</p>

---

## 📝 Descrição do Projeto

Projeto final Materia LTP-(lingaguem é Técnicas de programação), é um **Sistema de Gestão de Estacionamento** desenvolvido utilizando a linguagem **Java**. O sistema foi projetado para controlar de forma eficiente o fluxo de veículos, gerir a ocupação de vagas e calcular tarifas de permanência com base em categorias configuradas.

---

## 🛠️ Tecnologias e Recursos Utilizados

O projeto foi desenvolvido de forma nativa, utilizando apenas os recursos essenciais do ecossistema Java:

* **Linguagem:** Java (Versão 8 ou superior)
* **Interface:** Console / Terminal de texto (Interação via teclado)
* **Persistência de Dados:** Arquivo binário (`EST.DAT`) para salvar os dados no computador sem precisar de um banco de dados externo.
* **IDE Recomendada:** Eclipse IDE
---

## 🚀 Funcionalidades Principais

O sistema apresenta um menu interativo com as seguintes operações:

1. **[1] Entrada de Veículo:** Registo de novos veículos com validação estrita do formato de matrícula (7 caracteres), marca e categoria.
2. **[2] Saída de Veículo:** Processamento de saída com cálculo automático do valor a pagar com base no tempo de permanência.
3. **[3] Consultar:** Procura rápida do estado e dados de um veículo no ficheiro.
4. **[4] Exclusão:** Remoção lógica/física de registos do sistema.
5. **[5] Relatório de Faturamento:** Módulo financeiro que exibe o total faturado pelo estabelecimento de forma acumulada.
6. **[0] Sair:** Encerramento seguro da aplicação guardando o estado atual.

---

