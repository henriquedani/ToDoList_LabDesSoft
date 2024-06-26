import React, { useState } from "react";

function TaskLivreForm({ addTarefa }) {
  const [descricao, setDescricao] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (descricao) {
      const novaTarefa = {
        description: descricao,
      };

      const response = await fetch("http://localhost:8080/api/task/create/livre", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(novaTarefa),
      });

      if (response.ok) {
        setDescricao("");
        const data = await response.json();
        addTarefa(data);

      } else {
        console.error("Erro ao criar o Tarefa:", response.statusText);

      }

    } else {
      alert("A descrição deve ser preenchida");
    }

  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="input-form">
        <p>Descrição da tarefa:</p>
        <input
          type="text"
          placeholder="Descrição"
          value={descricao}
          onChange={(e) => setDescricao(e.target.value)}
          required
        />
      </div>
      <button className="button-criar-tarefa" type="submit">
        Criar tarefa
      </button>
      
    </form>
  );
}

export default TaskLivreForm;