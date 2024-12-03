# Avaliação N3 - Documentação do projeto 

Este projeto foca no gerenciamento inicial de um setor de Produção de uma Industria, permite o cadastro de Ordens de Produção (OP), e  Ordens de Serviço (OS), produtos e operadores. Após o cadastro devido da OS, a aplicação devolve o feedback de produtividade e demais informações de produto, operador e OP.

## Executando

Para executar o projeto:

1.  Rode o comando `docker compose up -d`  na raiz do projeto.

## Tecnologias Utilizadas

* Maven
* Spring Boot
* Spring Data JPA
* H2 DataBase
* Docker
* Render
  * https://springplanejamentodeproducao.onrender.com/operadores
  * https://springplanejamentodeproducao.onrender.com/produtos
  * https://springplanejamentodeproducao.onrender.com/oss
  * https://springplanejamentodeproducao.onrender.com/ops

## Recursos

1. Ordem de Serviso (OS).
2. Ordem de Produção (OP).
3. Produto.
4. Operador. 

## Regras de Negócio

* Com o atibuto custoMP, inserido no Produto, multiplicado pela quantidade da OS, é Calculado o custoMPProducao na OP;
* Com o atibuto tempoProcucao, inserido no Produto, e, o atributo tempoExecução inserido na OS, é calculado a produtividade;
* Os produtos contam com tempo esperado de produção para futuro calculo de produtividade na OS;
* Os produtos tem custo de MP para calculo de custo total da ordem de produção;
* Os operadsores tem data de nascimento que tem ser inferior a data atual, não pode estar no futuro;
* Os operadores tem que ter formato de email valido;
* Os tipos de OP's devem ser: `Interna` ou `Externa`.
* Os tipos de OS's devem ser: `Inteira` ou `Parcial`.
* Os tipos de produtos devem se enquadrar no seguimentos: `Rodoviario`, `Guindaste` ou `Outros`.
* As operações de criação ("POST") e atualização("PACTH) validam a quantidade de produtos da op, nome de operador,nome do produto, produtividade, tempo total esperado, tempo de execução, a depernder de qual recurso está em uso;

## Rotas

### OS

#### `GET /oss/paginacao?page=0&size=1`
`page é a pagina selecionada`
`size é o numero de pagina por elemento`

Resposta:
````json
{
	"conteudoPorPagina": [
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
	],
	"totalElementos": 5,
	"totalDePaginas": 5,
	"paginasAtual": "[0], 1, 2, 3, 4"
}
````

Respostas de erro:
* `400` - "Não há OS's disponíveis na página solicitada.";

#### OS `GET /oss/tipo/{tipo}
Lista todas as OS's disponíveis.Filtra pela `tipo` se for informado.

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

Cadastra uma nova OS.

Corpo da Mensagem:
````json

{
  "tipo": "parcial",
  "op": 1,
  "quantidadeProduzida": 1,
  "idProduto": 1,
  "idOperador": 1,
  "tempoDeExecucaoEmSegundos": 50000
}````


Corpo da Resposta:
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
* `400` - `tipo` inválido;
* `404` - Produto não encontrado; ** Se não houver produto cadastrado com a "id" enviada.
* `404` - OP não encontrada; ** Se não houver OP cadastrado com a "id" enviada.
* `404` - Operador não encontrado; ** Se não houver Operador cadastrado com a "id" enviada.
* `409` - Quantidade de OS não pode ser maior que a quantidade da OP

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

#### `GET /ops/paginacao?page=0&size=1`
`page é a pagina selecionada`
`size é o numero de pagina por elemento`

Resposta:
````json
{
	"conteudoPorPagina": [
		{
      "id": 1,
      "tipo": "interna",
      "produto": 1,
      "nomeProduto": "Caminhão11111",
      "quantidade": 1,
      "tempoProducao": "PT2H46M39S",
      "custoMPProduto": 50000.00
		}
	],
	"totalElementos": 5,
	"totalDePaginas": 5,
	"paginasAtual": "[0], 1, 2, 3, 4"
}
````

Respostas de erro:
* `400` - "Não há OP's disponíveis na página solicitada.";

#### `GET /ops/tipo/{tipo}`
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

#### `GET /produtoss/paginacao?page=0&size=1`
`page é a pagina selecionada`
`size é o numero de pagina por elemento`

Resposta:
````json
{
	"conteudoPorPagina": [
		{
      "id": 1,
      "nome": "Caminhão",
      "tipo": "outros",
      "tempoProducao": "PT2H46M39S",
      "custoMP": 999.00
		}
	],
	"totalElementos": 5,
	"totalDePaginas": 5,
	"paginasAtual": "[0], 1, 2, 3, 4"
}
````

Respostas de erro:
* `400` - "Não há Produtos disponíveis na página solicitada.";

#### `GET /produtos/tipo/{tipo}`
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
* `404` - Produto não encontrado para este tipo;

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
* `400` - O tempo de produção deve ser superior a 1 minuto;
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

#### `GET /operadores/paginacao?page=0&size=1`
`page é a pagina selecionada`
`size é o numero de pagina por elemento`

Resposta:
````json
{
	"conteudoPorPagina": [
		{
      "id": 1,
      "nome": "João",
      "email": "jorge@dominio",
      "salario": 5997.00,
      "dataNascimento": "1999-06-22"
		}
	],
	"totalElementos": 5,
	"totalDePaginas": 5,
	"paginasAtual": "[0], 1, 2, 3, 4"
}
````

Respostas de erro:
* `400` - "Não há Operadores disponíveis na página solicitada.";

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
* `400` - A data de nascimento deve ser anterior à data atual;
* `400` - O salário deve ser no máximo 99999;
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
* `400` - O salário deve ser no máximo 99999;
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
