# Boletim API (Spring Boot 3, Java 17)


API para lançamento de notas e cálculo de média ponderada por disciplina.


## Rodando local

```bash
./mvnw spring-boot:run
# ou
./mvnw clean package -DskipTests && java -jar target/boletim-1.0.0.jar


Acesse Swagger: http://localhost:8080/swagger-ui.html
Console H2: http://localhost:8080/h2 (JDBC URL: jdbc:h2:mem:boletimdb)

Endpoints úteis

GET /turmas

GET /disciplinas

GET /turmas/{turmaId}/alunos

GET /disciplinas/{disciplinaId}/avaliacoes

GET /lancamentos?turmaId=&disciplinaId=

POST /notas/lote (upsert de notas)

GET /notas/medias?turmaId=&disciplinaId=



Observações

spring.jpa.defer-datasource-initialization=true garante que o data.sql rode após o Hibernate criar as tabelas.

Valores de nota vão de 0.0 a 10.0, peso de 1 a 5.



## Dicas de teste rápido (curl)


```bash
curl "http://localhost:8080/lancamentos?turmaId=1&disciplinaId=1"


curl -X POST "http://localhost:8080/notas/lote" \
-H "Content-Type: application/json" \
-d '[{"alunoId":1,"avaliacaoId":10,"valor":8.0},{"alunoId":1,"avaliacaoId":11,"valor":6.0}]'


curl "http://localhost:8080/notas/medias?turmaId=1&disciplinaId=1"


## Dica de modelo de autenticação

O **OAuth 2.0** (Open Authorization) é um protocolo de **autorização** amplamente utilizado, que traz diversas vantagens para a segurança e usabilidade de uma API.

Aqui está uma breve descrição das principais vantagens:

1.  **Segurança Aprimorada:**
    * **Não Compartilha Credenciais:** O aplicativo cliente (terceiro) nunca tem acesso direto ao nome de usuário e senha do proprietário do recurso. Em vez disso, ele recebe um **Token de Acesso** temporário.
    * **Tokens com Escopo Limitado:** O acesso é concedido por meio de *scopes* (escopos), que especificam exatamente o que o aplicativo pode fazer (ex: apenas ler e-mails, mas não enviar). Isso minimiza o risco em caso de comprometimento do token.
    * **Tokens com Expiração:** Os tokens de acesso geralmente têm um tempo de vida curto, limitando a janela de tempo em que um token roubado pode ser usado.

2.  **Melhor Experiência do Usuário (UX):**
    * **Login com Terceiros (SSO):** Facilita o uso de serviços de terceiros (como Google, Facebook) para autenticação, eliminando a necessidade de criar e lembrar novas senhas para cada aplicativo.
    * **Controle do Usuário:** O usuário tem controle granular sobre quais permissões concede a cada aplicativo, visualizando e consentindo explicitamente o escopo de acesso.

3.  **Flexibilidade e Padrão da Indústria:**
    * **Diversos Tipos de Aplicação:** Suporta vários fluxos (Grant Types) adequados para diferentes cenários, como aplicações web, mobile, desktop e comunicação máquina a máquina.
    * **Padrão Adotado:** É o padrão de mercado, o que significa que há uma vasta documentação, ferramentas e bibliotecas que facilitam a sua implementação.
    * **Separação de Funções:** Separa claramente a função de autenticação (quem o usuário é) e a de autorização (o que o usuário pode fazer), tornando o sistema mais robusto e modular.