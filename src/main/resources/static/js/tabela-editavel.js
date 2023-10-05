document.querySelectorAll(".coluna-editavel").forEach((cell) => {
  cell.addEventListener("click", function () {
    this.setAttribute("contenteditable", "true");
  });
  // Ao sair do foco, desabilita a edição e chama a função para salvar
  cell.addEventListener("blur", function () {
    this.setAttribute("contenteditable", "false");
    saveChanges(this);
  });
});

function saveChanges(cell) {
  const newValue = cell.textContent; // valor editado
  const rowId = cell.closest("tr").dataset.id; // supondo que cada linha tem um 'data-id' correspondente ao ID do movimento

  var activePage = document.getElementById('activePage').getAttribute('data-page');
  var fetchUrlBase;

  if (activePage == 'detalhesAtividades') {
    fetchUrlBase = 'movimentos';
  } else if(activePage == 'categorias') {
    fetchUrlBase = activePage;
  } else if(activePage == 'tags') {
    fetchUrlBase = activePage;
  }

  // Chama o endpoint do servidor para salvar a alteração
  fetch(`/${fetchUrlBase}/${rowId}`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ descricao: newValue }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.redirectUrl) {
        window.location.href = data.redirectUrl;
      }
    })
    .catch((error) => {
      alert("Erro ao salvar a alteração!");
    });
}
