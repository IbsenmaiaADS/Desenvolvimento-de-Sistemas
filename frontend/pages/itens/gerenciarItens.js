function deletarItem(id) {
  axios
    .delete(`http://localhost:8080/itens/deletar/${id}`)
    .then((response) => {
      if (response.status === 200) {
        buscarItens();
      }
    })
    .catch((error) => console.error("Erro ao deletar o item: ", error));
}

function buscarItens() {
  axios
    .get("http://localhost:8080/itens")
    .then((response) => {
      const itemsContainer = document.getElementById("items-container");
      itemsContainer.innerHTML = "";

      response.data.forEach((item) => {
        const card = document.createElement("div");
        card.classList.add("col-md-4", "mb-4");

        card.innerHTML = `
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">${item.nome}</h5>
                <p class="card-text">Quantidade em Estoque: ${item.quantidadeEstoque}</p>
                <a href="#" data-id="${item.id}" class="btn btn-primary">Editar</a>
                <a href="#" data-id="${item.id}" class="btn btn-danger deletar">Deletar</a>
              </div>
            </div>
          `;

        itemsContainer.appendChild(card);

        const botaoDeletar = card.querySelector(".deletar");
        botaoDeletar.addEventListener("click", () => {
          const id = item.id;
          deletarItem(id);
        });
      });
    })
    .catch((error) => console.error("Erro ao buscar os itens: ", error));
}

buscarItens();

function cadastrarItem() {
  const nome = document.getElementById("nome").value;
  const quantidadeEstoque = parseInt(
    document.getElementById("quantidade").value
  );

  axios
    .post("http://localhost:8080/itens/cadastrar", {
      nome: nome,
      quantidadeEstoque: quantidadeEstoque,
    })
    .then((response) => {
      if (response.status === 200) {
        $("#cadastrarModal").modal("hide");
        buscarItens();
      }
    })
    .catch((error) => console.error("Erro ao cadastrar o item: ", error));
}

document
  .getElementById("cadastrarBtn")
  .addEventListener("click", cadastrarItem);
