-- Turmas
insert into turma (id, nome) values (1, '1ºA'), (2, '1ºB');

-- Disciplinas
insert into disciplina (id, nome) values (1, 'Matemática'), (2, 'Português');

-- Alunos (referenciam turma)
insert into aluno (id, nome, turma_id) values
  (1, 'Ana', 1),
  (2, 'Bruno', 1),
  (3, 'Carla', 1);

-- Avaliações (referenciam disciplina)
insert into avaliacao (id, titulo, peso, disciplina_id) values
  (10, 'Prova', 5, 1),
  (11, 'Trabalho', 2, 1),
  (12, 'Atividade', 1, 1);

-- Notas (referenciam aluno e avaliação)
insert into nota (id, aluno_id, avaliacao_id, valor) values
  (100, 1, 10, 8.0),
  (101, 1, 11, 6.0),
  (102, 1, 12, 10.0);
