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