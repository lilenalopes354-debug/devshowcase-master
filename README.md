# DevShowcase API

Backend da plataforma DevShowcase desenvolvido com Java 25, Spring Boot, Spring Data JPA, Bean Validation e H2/PostgreSQL.

## Requisitos
- Java 25+
- Maven (ou `./mvnw`)

## Executar
```bash
./mvnw spring-boot:run
```
No Windows:
```powershell
.\mvnw.cmd spring-boot:run
```

Para executar os testes:
```powershell
.\mvnw.cmd clean test
```

Documentação interativa: `http://localhost:8080/swagger-ui/index.html`

## Atividade 2

### Projetos

`GET /api/projects?technology=Java&page=0&size=10`

`PUT /api/projects/{id}/upvote`

`POST /api/projects/{id}/feedbacks`
```json
{
  "authorName": "Maria Silva",
  "comment": "Excelente projeto",
  "rating": 5
}
```

### Deploy no Render

O arquivo `render.yaml` provisiona o serviço web e um PostgreSQL gratuito. No painel do Render, conecte o Blueprint ao repositório GitHub e configure `DATABASE_URL`, `DATABASE_USER` e `DATABASE_PASSWORD` como variáveis privadas. O valor de `DATABASE_URL` deve usar o formato JDBC, por exemplo `jdbc:postgresql://host:5432/devshowcase`.

O deploy contínuo é ativado pelo próprio Render ao conectar o serviço à branch `main`.

Banco local: H2 em memória.

## Endpoints

### Profiles
`POST /api/profiles`
```json
{
  "name": "Ana Silva",
  "email": "ana@email.com",
  "bio": "Desenvolvedora Java",
  "githubUrl": "https://github.com/anasilva",
  "linkedinUrl": "https://linkedin.com/in/anasilva"
}
```

`GET /api/profiles/{id}`

### Technologies
`POST /api/technologies`
```json
{ "name": "Java" }
```

`GET /api/technologies`

### Projects
`POST /api/projects`
```json
{
  "title": "DevShowcase",
  "description": "Portfólio de projetos",
  "projectUrl": "https://github.com/anasilva/devshowcase",
  "profileId": 1,
  "technologyIds": [1]
}
```

`GET /api/projects`

## Relacionamentos
- Profile 1:N Project
- Project N:N Technology
- Project 1:N Feedback

Feedback está modelado e persistido nesta etapa para completar o domínio; os endpoints de Feedback não fazem parte dos endpoints mínimos solicitados.
