# Manual Técnico - TP2 AED 2026/27

**Aluno:** Vasco Matos
**Número:** 85234

## 1. Estrutura das Classes

O projeto está organizado varias classes cada uma com uma responsabilidade:

### `Main`

É o ponto de entrada da aplicação. Recebe o nome do ficheiro binário pela linha
de comandos, carrega os registos, apresenta o menu e coordena as operações de
listagem, inserção e gravação.

### `Pessoas\Pessoa`

Representa uma pessoa e guarda:

- o nome completo;
- o primeiro nome;
- os apelidos;
- a data de nascimento;
- o número identificador.

Ao definir o nome completo, a classe separa automaticamente o primeiro nome
dos apelidos. A idade é calculada a partir da data de nascimento através da
classe `DataNascimento`.

### `Pessoas\DataNascimento`

É uma classe responsável por validar e converter datas no formato `DD/MM/AAAA`, bem como calcular a idade. Aceita datas entre 01/01/1900 e a data de referência definida no programa (`27/11/2026`) e rejeita datas
impossíveis ou futuras.

### `Pessoas\ListaPessoas`

Encapsula a coleção de pessoas numa `ArrayList<Pessoa>`. Implementa as operações de adicionar, remover, consultar por índice, obter o tamanho e listar os elementos.

### `Pessoas\Genero`

Cria e mantém três listas: mulheres, homens e pessoas não classificadas.
Inicialmente lê os ficheiros de nomes femininos e masculinos existentes em
`Pessoas/listas`. A divisão é feita pelo primeiro nome: é verificado o primeiro nome de cada pessoa e dependendo da terminaçãosão `a` e `o` é guardado como mulher ou homem. No caso de nomes incomuns que nao acabam em `a` e `o` é verificado atravez de uma lista de nomes em que lista o nome avaliado se iria encaixar. Quando não há correspondência, a pessoa fica na lista de não classificadas.

Também grava as listas de mulheres e homens em dois ficheiros binarios distintos. `output/mulheres.bin` e `output/homens.bin`.

### `R_W\Leitura`

Lê sequencialmente o ficheiro binário de entrada através de
`DataInputStream`. Reconstrói cada registo, valida a data e adiciona a pessoa
à `ListaPessoas`.

## Classes auxiliares

### `input\ConversorBinario`

Converte um ficheiro de texto para o formato binário utilizado pelo programa.
Lê três linhas por pessoa (nome, data e número), valida os valores e escreve o
registo com `DataOutputStream`.
Esta classe tem como objetivo converter o ficheiro original dados.txt para binário sendo então possivel correr o programa em binário

### `listas\LimparListas`

É uma ferramenta auxiliar para limpar as listas de nomes usadas na classificação. Remove os nomes que podem ser classificados pela terminação criando os ficheiros `feminino_limpo.txt` e `masculino_limpo.txt`.

### Relação entre as classes

`Main` utiliza `Leitura` para obter uma `ListaPessoas`. Cada elemento dessa lista é um objeto `Pessoa`. A classe `Género` percorre a lista original e
distribui as pessoas pelas listas correspondentes. `DataNascimento`, e a separação por género, são usadas na primeira leitura do ficheiro `dados.bin` e quando é adicionado uma nova pessoa.

## 2. Funcionamento do Programa

### Carregamento inicial

1. O programa recebe o nome do ficheiro como argumento:

   ```text
   java Main input\dados.bin
   ```

2. Se o caminho indicado não existir, o programa procura o ficheiro dentro da
   pasta `input`.
3. `Leitura` abre o ficheiro binário e lê registos até chegar ao fim do
   ficheiro.
4. Para cada registo, reconstrói o nome, a data e o número, valida a data e
   cria um objeto `Pessoa`.
5. A lista é entregue a `Genero`, que cria as listas de mulheres, homens e não
   classificadas.

### Menu principal

Depois do carregamento, o programa disponibiliza:

- **Listar:** abre as opções de listar todas as pessoas, dividir por género,
  listar maiores de 18 anos ou listar menores de 18 anos;
- **Inserir:** pede o nome, a data de nascimento e o número, valida a data e
  adiciona a nova pessoa à lista geral e à lista de género correspondente;
- **Imprimir:** grava as listas de mulheres e homens em ficheiros binários na
  pasta `output`;
- **Sair:** termina o programa.

### Critérios de divisão

Para classificar uma pessoa, o programa converte o primeiro nome para
minúsculas e aplica as regras pela seguinte ordem:

1. se o primeiro nome terminar em `a` ou estiver na lista de nomes femininos,
   é colocado na lista de mulheres;
2. caso contrário, se terminar em `o` ou estiver na lista de nomes masculinos,
   é colocado na lista de homens;
3. se nenhuma regra for satisfeita, é colocado na lista de não classificadas.

As listas mantêm a ordem de inserção.

### Pesquisa binária dos nomes

Os nomes das listas femininas e masculinas são carregados a partir dos ficheiros
`feminino_limpo.txt` e `masculino_limpo.txt` e organizados por ordem alfabética
antes da classificação. Para verificar se um primeiro nome pertence a uma das
listas, o programa utiliza pesquisa binária:

1. Determina o nome situado no meio da parte atual da lista;
2. Compara esse nome com o primeiro nome da pessoa;
3. Se forem iguais, encontra uma correspondência;
4. Se o nome procurado for menor, continua a pesquisa na metade anterior;
5. Caso contrário, continua a pesquisa na metade seguinte;
6. Repete os passos até encontrar o nome ou até não existirem posições para
   continuar a pesquisa.

Esta pesquisa reduz a procura numa lista ordenada de $n$ para, no máximo,
$\log_2(n)$ comparações. A verificação é feita tanto no carregamento inicial
como no momento da inserção manual de uma pessoa.

| n | │ | Linear (pior caso) | Binária (pior caso) |
|---:|:---:|---:|---:|
| 1 000 | │ | 1 000 | 10 |
| 1 000 000 | │ | 1 000 000 | 20 |
| 1 000 000 000 | │ | 1 000 000 000 | 30 |

Quando um nome aparece nas listas masculina e feminina, a correspondência
masculina tem prioridade (por exemplo "Zoé"). Se o nome não existir em nenhuma lista nem terminar
em `a` ou `o`, a pessoa é inserida na lista de não classificadas e o programa
informa imediatamente o utilizador durante a inserção.

## 3. Estrutura do Ficheiro

### Ficheiro de entrada em texto

O ficheiro `input/dados.txt` é um ficheiro UTF-8 auxiliar, usado como origem. Cada pessoa ocupa três linhas consecutivas:

1. nome completo;
2. data de nascimento no formato `DD/MM/AAAA`;
3. número identificador.


### Ficheiro binário utilizado

Após a conversão, `input/dados.bin` é o formato lido por `Main`. Cada registo
é escrito na seguinte ordem:

| Campo | Tipo binário | Memória | Descrição |
|---|---:|---:|---|
| Nome | `writeUTF` | variável | Nome completo em UTF-8, com o tamanho do texto no próprio formato |
| Dia | `byte` | 1 byte | Valor entre 1 e 31 |
| Mês | `byte` | 1 byte | Valor entre 1 e 12 |
| Ano | `short` | 2 bytes | Ano entre 1 e 65535 |
| Número | `int` | 4 bytes | Número identificador inteiro |

O tamanho dos componentes da data é adequado aos seus intervalos: um dia e
um mês cabem num byte, enquanto o ano precisa de um `short` para representar
um intervalo maior. O número é guardado num `int`, que permite representar valores maiores. O nome é guardado como
texto de tamanho variável porque os nomes têm comprimentos diferentes.

Os ficheiros binários de saída usam exatamente o mesmo formato de registo:
`output/mulheres.bin` contém as mulheres classificadas e
`output/homens.bin` contém os homens classificados.

Pessoas não classificadas são apresentadas no programa, mas não são gravadas nesses dois ficheiros.

## 4. Execução

A partir da raiz do projeto, depois de compiladas as classes, pode ser usado:

```text
java Main input\dados.bin
```

Para converter o ficheiro de texto para binário:

```text
java -cp input ConversorBinario input\dados.txt input\dados.bin
```

Os ficheiros gerados pela opção **Imprimir** ficam na pasta `output`.

## 5. output

Da mesma forma que os dados sao lidos também sao impessos nos ficheiros de saida. Ou seja a estrutura dos dados irá ser dois bytes a definir o tamanho do nome da pessoa e de seguida os repetivos numero de bytes a contar o ASCII de cada caracter do nome. A seguir o dia e mês guardados num byte com o ano guardado em dois (short) e por fim o numero de telefone guardado em 4 bytes (int).

## 6. Observações Finais

As listas de nomes femininos e masculinos foram obtidas a partir de listas
externas de nomes no IRN (Instituro Nacional de Registos) e preparadas com `LimparListas`. A aplicação
combina essas listas com as terminações mais comuns dos primeiros nomes, mas
mantém uma lista de não classificadas para evitar atribuições arbitrárias
quando não existe informação suficiente.

O uso de `ArrayList` permite inserir e percorrer os registos de forma simples,
mantendo a ordem original. A validação centralizada em `DataNascimento` evita
que datas inválidas sejam aceites tanto na leitura dos ficheiros como na
inserção manual.

Foi utilizado um repositório git na platagorma github para controlo de versão e para a criação dos ficheiros ConversorBinario e a estrutura base markdown do README atravez do copilot e das IA integradas no vscode.
