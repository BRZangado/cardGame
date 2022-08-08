# API DE CARD GAME
*Ao subir a aplicação, será feito uma busca na omdbapi pra popular o banco com alguns filmes para teste. Aguarde a mensagem de "jogo liberado" nos logs da aplicação para começar a jogar.*<br><br>
como usar: <br>
Dois usuários foram criados inicialmente para poder rodar todo o fluxo da api <br>

### usuário 1: username: bruno, senha: 123
### usuário 2: username: bruna, senha 123

essa api usa o método basic de autenticação<br>
para testar via postman, selecione a aba *authorization*, selecione
o type *basic auth* e será apresentados os inputs de user e password.<br>
caso queria testar via curl, será necessário gerar o token do basic pelo site blitter.se
e enviar o token pelo header, por exemplo<br>
*--header 'Authorization: Basic c2lsYXM6MTIz'* <br>
TODOS OS ENDPOINT UTILIZADOS PRECISAM DE ATUTENTICAÇÃO<br>
Ao iniciar uma partida, o usuario logado será vinculado à partida iniciada.
Ao jogar uma partida, será verificado se o usuário logado é o usuário que iniciou a partida.
Caso contrário retornará erro de não autorização.

### *Começando uma partida* ###
Para iniciar uma partida<br>
POST /api/v1/round/start<br>
Ao iniciar a partida, o id da rodada (round) e os dois primeiros filmes serão informados

### *Jogando uma rodada* ###
para jogar uma rodada<br>
POST /api/v1/round/play?roundId={X}&movieId={Y}<br>
deverá ser informado o id da rodada e o id do filme escolhido<br><br>
o jogador tem 3 chances de acertar qual filme que possui maior pontuação<br><br>
caso acerte qual filme tem maior pontuação, receberá um mensagem de acerto e somará masi um ponto na partida<br><br>
Caso não exista novos filmes a serem jogados, a partida se encerrará automaticamente

### *Encerrando uma partida* ###
Você pode encerrar uma partida a qualquer momento pelo endpoint<br>
POST /api/v1/round/finish?roundId={x}<br>
Utilizando o id da partida que deseja encerrar


### Visualizando RANKING ###
Para visualizar o ranking de partidas com mais pontos<br>
GET /api/v1/round/ranking

### DOCUMENTAÇÃO ###
Toda a documentação desta api está disponivel no link do swagger<br>
{url}/swagger-ui/

