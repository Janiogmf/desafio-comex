#language: pt
#autor: janiogouveia@hotmail.com
@gestao_emprestimo_livro
Funcionalidade: Controlar emprestimos de livros
  Controlar cadastros de emprestimos dos livros

  @cadastro
  Cenario: Validar cadastro de emprestimo
    Dado que faca cadastro de emprestimo:
      | NOME   | EMAIL              | CPF         | IDADE | AUTOR_LIVRO     | NOME_LIVRO | TITULO   |
      | Isabel | isabel@hotmail.com | 19926225588 |    64 | Fernando Pessoa | Poemas     | Mensagem |
    Entao validar cadastro retorno 201

  @usuario-cadastrado
  Cenario: Validar retorno de usuario cadastrado
    Dado que busque estudante cadastrado com cpf "19926225588"
    Entao validar retorno do cadastro:
      | NOME   | EMAIL              | CPF         | IDADE |
      | Isabel | isabel@hotmail.com | 19926225588 |    64 |

  @cpf-cadastrado
  Cenario: Validar bloqueio de cadastro mesmo cpf
    Dado que faca cadastro de emprestimo:
      | NOME    | EMAIL               | CPF         | IDADE | AUTOR_LIVRO     | NOME_LIVRO | TITULO   |
      | Augusto | augusto@hotmail.com | 19926225588 |    25 | Fernando Pessoa | Poemas     | Mensagem |
    Entao validar cadastro retorno 400

  @atualizacao-cadastro
  Cenario: Validar atualizacao estudante cadastrado
    Dado que faca atualizacao do estudante:
      | CPF         | NOME        | EMAIL              |
      | 09705066618 | JanioUpdate | testeapi@gmail.com |
    Entao validar atualizacao de dados retorno 200

  @atualizacao-estudante-sem-cadastro
  Cenario: Validar atualizacao estudante nao cadastrado
    Dado que faca atualizacao do estudante:
      | CPF         | NOME        | EMAIL              |
      | 30226262629 | JanioUpdate | testeapi@gmail.com |
    Entao validar atualizacao de dados retorno 404

  @atualizacao-estudante-dados-incompletos
  Cenario: Validar atualizacao estudante dados incompletos
    Dado que faca atualizacao do estudante:
      | CPF         | NOME        | EMAIL |
      | 09705066618 | JanioUpdate |       |
    Entao validar atualizacao de dados retorno 400
