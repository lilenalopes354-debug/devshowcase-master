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
