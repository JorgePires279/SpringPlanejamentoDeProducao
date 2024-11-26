# Avaliação N3 - Documentação do projeto 

Este projeto foca no gerenciamento inicial de um setor de Produção de uma Industria, permite o cadastro de Ordens de Produção (OP), e ordens de Ordens de Serviço (OS), produtos e operadores. Após o cadastro devido da OS, a aplicação devolve o feedback de produtividade e demais informações de produto, operador e OP.

## Executando

Para executar o projeto:

1. Rode o comando `docker compose up -d` na raiz do projeto.

## Tecnologias Utilizadas

* Spring Boot
* Spring Data JPA
* H2 DataBase
* Docker
* Maven
* Render

## Recursos

1. Ordem de Serviso (OS).
2. Ordem de Produção (OP).
3. Produto.
4. Operador. 

## Regras de Negócio

* Os produtos contam com tempo esperado de produção para futuro calculo de prosutividade na OS;
* Os produtos tem custo de MP para calculo de custo total da ordem de produção;
* Os operadsores tem data de nascimento que tem ser inferior a data atual, não pode estar no futuro;
* Os operadores tem que ter formato de email valido;
* Os tipos de OP's devem ser: `Interna` ou `Externa`.
* Os tipos de OS's devem ser: `Inteira` ou `Parcial`.
* Os tipos de produtos devem se enquadrar no seguimentos: `Rodoviario`, `Guindaste` ou `Outros`.
* As operações de criação ("POST") e atualização("PACTH) validam a quantidade de produtos da op, nome de operador,nome do produto, produtividade, tempo total esperado, tempo de execução, a depernder de qual recurso está em uso;

## Rotas

### OS
#### OS `GET /oss[?tipo=:tipo]`
Lista todas as OS's disponíveis.Filtra pela `tipo` se for informado.



Resposta
{
	"id": 3,
	"tipo": "parcial",
	"op": 1,
	"quantidadeOp": 1,
	"quantidadeOS": 1,
	"idProduto": 1,
	"nomeProduto": "Caminhão",
	"idOperador": 1,
	"nomeOperador": "João",
	"tempoDeExecucao": "PT13H53M20S",
	"produtividade": "A produtividade da OS foi: 20,00 %"
}
````

Respostas de erro:
* `400` - `tipo` inválido;
* `404` - Produto não encontrado; ** Se não houver produto cadastrado com a "id" enviada.
* `404` - OP não encontrada; ** Se não houver OP cadastrado com a "id" enviada.
* `404` - Operador não encontrado; ** Se não houver Operador cadastrado com a "id" enviada.

#### `GET /oss/{id}`
Lista uma semente específica pelo `id`.

Resposta:
````json
{
	"id": 3,
	"tipo": "parcial",
	"op": 1,
	"quantidadeOp": 1,
	"quantidadeOS": 1,
	"idProduto": 1,
	"nomeProduto": "Caminhão",
	"idOperador": 1,
	"nomeOperador": "João",
	"tempoDeExecucao": "PT13H53M20S",
	"produtividade": "A produtividade da OS foi: 20,00 %"
}
````

Respostas de erro:
* `404` - "OS não encontrada"; ** Se não houver os cadastrada com essa "id".

#### `POST /oss`

Cadastra uma nova semente.

Corpo da Mensagem:
````json
[
  {
  "tipo": "parcial",
  "op": 1,
  "quantidadeProduzida": 1,
  "idProduto": 1,
  "idOperador": 1,
  "tempoDeExecucaoEmSegundos": 50000
}
]

Resposta
{
	"id": 3,
	"tipo": "parcial",
	"op": 1,
	"quantidadeOp": 1,
	"quantidadeOS": 1,
	"idProduto": 1,
	"nomeProduto": "Caminhão",
	"idOperador": 1,
	"nomeOperador": "João",
	"tempoDeExecucao": "PT13H53M20S",
	"produtividade": "A produtividade da OS foi: 20,00 %"
}
````

Respostas de erro:
* `400` - `tipo` inválido;
* `404` - Produto não encontrado; ** Se não houver produto cadastrado com a "id" enviada.
* `404` - OP não encontrada; ** Se não houver OP cadastrado com a "id" enviada.
* `404` - Operador não encontrado; ** Se não houver Operador cadastrado com a "id" enviada.

#### `PATCH /oss/{id}`

Atualiza as informações de uma OS específica pelo `id`.

Corpo da Requisição:
````json
{
  "tipo": "parcial",
}
````

Corpo da Resposta:
````json
[
  {
	"id": 4,
	"tipo": "parcial",
	"op": 1,
	"quantidadeOp": 1,
	"quantidadeOS": 111,
	"idProduto": 1,
	"nomeProduto": "Caminhão11111",
	"idOperador": 1,
	"nomeOperador": "João",
	"tempoDeExecucao": "PT13H53M20S",
	"produtividade": "A produtividade da OS foi: 20,00 %"
}
]
````

Respostas de erro:
* `404` - Produto não encontrado; ** Se não houver produto cadastrado com a "id" enviada.
* `404` - OP não encontrada; ** Se não houver OP cadastrado com a "id" enviada.
* `404` - Operador não encontrado; ** Se não houver Operador cadastrado com a "id" enviada.
* `400` - "Tipo de OS inválido";
* `404` - "OS não encontrada";

#### `DELETE /oss/{id}`

Deleta uma semente específica pelo `id`.

Corpo da Resposta:
````json
[
  {
	"id": 4,
	"tipo": "parcial",
	"op": 1,
	"quantidadeOp": 1,
	"quantidadeOS": 111,
	"idProduto": 1,
	"nomeProduto": "Caminhão11111",
	"idOperador": 1,
	"nomeOperador": "João",
	"tempoDeExecucao": "PT13H53M20S",
	"produtividade": "A produtividade da OS foi: 20,00 %"
}
]
````

Respostas de erro:
* `400` - `id` inválido;
* `404` - Produto não encontrado;
* `409` - ***OS em uso;

### OP

#### `GET /ops[?tipo=:tipo]`
Lista todas as OP's disponíveis. Filtra pela `tipo` se for informado.

Resposta:
````json
[
  {
    "id": 1,
    "tipo": "interna",
    "produto": 1,
    "nomeProduto": "Caminhão11111",
    "quantidade": 1,
    "tempoProducao": "PT2H46M39S",
    "custoMPProduto": 50000.00
  }
]
````

Respostas de erro:
* `400` - `tipo` inválido;
* `204` - nenhuma OP encontrada;

#### `GET /ops/{id}`
Lista uma op específica pelo `id`.

Resposta:
````json
{
	"id": 1,
	"tipo": "interna",
	"produto": 1,
	"nomeProduto": "Caminhão11111",
	"quantidade": 1,
	"tempoProducao": "PT2H46M39S",
	"custoMPProduto": 50000.00
}
````

Respostas de erro:
* `400` - `id` inválido;
* `404` - op não encontrada;

#### `POST /ops`

Cadastra uma nova op.

Corpo da Requisição:
````json
[
  {
    "tipo": "interna",
    "produto": 1,
    "quantidade": 1,
    "custoMPProduto": 50000.00
  }
]
````

Corpo da Resposta:
````json
[
  {
    "id": 1,
    "tipo": "interna",
    "produto": 1,
    "nomeProduto": "Caminhão11111",
    "quantidade": 1,
    "tempoProducao": "PT2H46M39S",
    "custoMPProduto": 50000.00
  }
]
````

Respostas de erro:
* `400` - data de expiração inválida;
* `400` - tempo de germinação inválido;
* `400` - tipo inválido;
* `404` - op não encontrada;
* `409` - op já cadastrada;

#### `PATCH /ops/{id}`

Atualiza as informações de uma op específica pelo `id`.

Corpo da Requisição:
````json
{
  "tipo": "externa",
}
````

Corpo da Resposta:
````json
[
  {
    "id": 1,
    "tipo": "externa",
    "produto": 1,
    "nomeProduto": "Caminhão11111",
    "quantidade": 1,
    "tempoProducao": "PT2H46M39S",
    "custoMPProduto": 50000.00
  }
]
````

Respostas de erro:
* `400` - `id` inválido;
* `400` - tipo inválido;
* `404` - OP não encontrada;

#### `DELETE /ops/{id}`

Deleta uma op específica pelo `id`.

Corpo da Resposta:
````json
[
  {
    "id": 1,
    "tipo": "externa",
    "produto": 1,
    "nomeProduto": "Caminhão11111",
    "quantidade": 1,
    "tempoProducao": "PT2H46M39S",
    "custoMPProduto": 50000.00
  }
]
````

Respostas de erro:
* `400` - `id` inválido;
* `404` - OP não encontrada;
* `409` - OP em uso;

### Produtos

#### `GET /produtos[?tipo=:tipo]`
Lista todas as produtos disponíveis. Filtra pela `tipo` se for informado.

Resposta:
````json
[
  {
    "id": 1,
    "nome": "Caminhão",
    "tipo": "outros",
    "tempoProducao": "PT2H46M39S",
    "custoMP": 999.00
  }
]
````

Respostas de erro:
* `400` - `tipo` inválido;
* `404` - Produto não encontrado;

#### `GET /produtos/{id}`
Lista uma produto específica pelo `id`.

Resposta:
````json
{
	"id": 1,
	"nome": "Caminhão",
	"tipo": "outros",
	"tempoProducao": "PT2H46M39S",
	"custoMP": 999.00
}
````

Respostas de erro:
* `400` - `id` inválido;
* `404` - Produto não encontrado;

#### `POST /produtos`

Cadastra uma nova produto.

Corpo da Requisição:
````json
{
  "nome": "Caminhão",
  "tipo": "outros",
  "tempoProducaoEmSegundos": 9999,
  "custoMP": 999.00
}
````

Corpo da Resposta:
````json
[
  {
    "id": 1,
    "nome": "Caminhão",
    "tipo": "outros",
    "tempoProducao": "PT2H46M39S",
    "custoMP": 999.00
  }
]
````

Respostas de erro:
* `400` - Tipo inválido;
* `404` - Produto não encontrado;
* `409` - Produto já cadastrada;

#### `PATCH /produtos/{id}`

Atualiza as informações de uma produto específica pelo `id`.

Corpo da Requisição:
````json
{
   "tipo": "Roviarios",
}
````

Corpo da Resposta:
````json
[
  {
    "id": 1,
    "nome": "Caminhão",
    "tipo": "Roviarios",
    "tempoProducao": "PT2H46M39S",
    "custoMP": 999.00
  }
]
````

Respostas de erro:
* `400` - `id` inválido;
* `400` - Tipo inválido;
* `404` - Produto não encontrado;

#### `DELETE /produtos/{id}`

Deleta uma produto específica pelo `id`.

Corpo da Resposta:
````json
[
  {
    "id": 1,
    "nome": "Caminhão",
    "tipo": "Roviarios",
    "tempoProducao": "PT2H46M39S",
    "custoMP": 999.00
  }
]
````

Respostas de erro:
* `400` - `id` inválido;
* `400` - Tipo inválido;
* `404` - Produto não encontrado;
* `409` - Produto em uso;

### Operadores

#### `GET /operadores[?tipo=:email]`
Lista todas os operadores disponíveis. Filtra pela `email` se for informado.

Resposta:
````json
[
  {
    "id": 1,
    "nome": "João",
    "email": "jorge@dominio",
    "salario": 5997.00,
    "dataNascimento": "1999-06-22"
  }
]
````

Respostas de erro:
* `400` - `tipo` inválido;
* `204` - Nenhuma operador encontrado;

#### `GET /operadores/{id}`
Lista uma operador específica pelo `id`.

Resposta:
````json
{
	"id": 1,
	"nome": "João",
	"email": "jorge@dominio",
	"salario": 5997.00,
	"dataNascimento": "1999-06-22"
}
````

Respostas de erro:
* `400` - `id` inválido;
* `404` - Operador não encontrado;

#### `POST /operadores`

Cadastra uma nova operadore.

Corpo da Requisição:
````json
{
	"nome": "João",
  "email": "jorge@dominio",
  "salario": 5997.00,
	"dataNascimento": "1999-06-23"
}
````

Corpo da Resposta:
````json
[
  {
    "id": 1,
    "nome": "João",
    "email": "jorge@dominio",
    "salario": 5997.00,
    "dataNascimento": "1999-06-22"
  }
]
````

Respostas de erro:
* `400` - tipo inválido;
* `400` - A data de nascimento deve ser anterior à data atual
* `404` - Operadore não encontrado;
* `409` - Operadore já cadastrada;

#### `PATCH /operadores/{id}`

Atualiza as informações de uma operadore específica pelo `id`.

Corpo da Requisição:
````json
{
  "dataNascimento": "2000-10-22"
}
````

Corpo da Resposta:
````json
[
  {
    "id": 1,
    "nome": "João",
    "email": "jorge@dominio",
    "salario": 5997.00,
    "dataNascimento": "2000-10-22"
  }
]
````

Respostas de erro:
* `400` - tipo inválido;
* `400` - A data de nascimento deve ser anterior à data atual
* `404` - Operadore não encontrado;
* `409` - Operadore já cadastrada;

#### `DELETE /operadores/{id}`

Deleta uma operador específico pelo `id`.

Corpo da Resposta:
````json
[
  {
    "id": 1,
    "nome": "João",
    "email": "jorge@dominio",
    "salario": 5997.00,
    "dataNascimento": "2000-10-22"
  }
]
````

Respostas de erro:
* `400` - `id` inválido;
* `404` - Operador não encontrado;
* `409` - Operador em uso;

#### melhoria para apresentação

não poder editar alguma coisa que está sendo usado; tem que dar erro;
peguntou dobre o dto de alguma coisa;
cascade
relacionamento bydirecional;
regra de negocio o meu tem;