# Sistema de Notas - Professor Carlos

Sistema web para gerenciamento de notas e frequência de alunos, desenvolvido com Spring Boot (backend) e React (frontend).

## 📋 Descrição do Projeto

O Professor Carlos precisa organizar as notas e a frequência de seus alunos. Este sistema permite:

- Cadastrar alunos com 5 notas (0 a 10) e frequência (0 a 100%)
- Calcular automaticamente a média de cada aluno
- Calcular a média da turma em cada disciplina
- Identificar alunos com média acima da média da turma
- Identificar alunos com frequência abaixo de 75%

## 🚀 Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3.2.0
- Maven
- API REST

### Frontend
- React 18
- JavaScript ES6+
- CSS3
- Fetch API

## 🔧 Como Executar o Sistema

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Node.js 16+ e npm

### Executando o Backend

1. Clone o repositório:
```bash
git clone https://github.com/KawanyTeixeira/sistema-notas-professor-carlos.git
cd sistema-notas-professor-carlos
```

2. Execute o backend:
```bash
mvn spring-boot:run
```

O backend estará disponível em: `http://localhost:8080`

### Executando o Frontend

1. Entre na pasta do frontend:
```bash
cd sistema-notas-frontend
```

2. Instale as dependências:
```bash
npm install
```

3. Execute o frontend:
```bash
npm start
```

O frontend abrirá automaticamente em: `http://localhost:3000`

## 📡 Endpoints da API

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/alunos` | Lista todos os alunos |
| GET | `/api/alunos/{id}` | Busca um aluno específico |
| POST | `/api/alunos` | Adiciona um novo aluno |
| DELETE | `/api/alunos/{id}` | Remove um aluno |
| GET | `/api/alunos/estatisticas` | Retorna estatísticas da turma |

## 📝 Premissas Assumidas

1. **Armazenamento em Memória**: Os dados são armazenados em memória durante a execução. Ao reiniciar o servidor, os dados são perdidos.

2. **Validações**: 
   - Notas devem estar entre 0 e 10
   - Frequência deve estar entre 0 e 100%
   - Nome do aluno é obrigatório
   - São exatamente 5 disciplinas

3. **Cálculo de Médias**:
   - Média do aluno = soma das 5 notas ÷ 5
   - Média da turma por disciplina = soma das notas ÷ número de alunos
   - Média geral da turma = soma das médias dos alunos ÷ número de alunos

## 🎯 Decisões de Projeto

### Backend

1. **Arquitetura em Camadas**: Separação entre Controller, Service, Model e DTO
2. **RESTful API**: Seguindo padrões REST
3. **Armazenamento Temporário**: HashMap para simplicidade
4. **CORS Configuration**: Permitir comunicação frontend-backend

### Frontend

1. **React Hooks**: useState e useEffect para gerenciamento de estado
2. **CSS Puro**: Sem dependências de bibliotecas de UI
3. **Feedback Visual**: Mensagens de sucesso/erro e loading states
4. **Validações**: Tanto no frontend quanto no backend

## 👨‍💻 Desenvolvido por

Kawany Emilly Freitas Teixeira

---

Projeto desenvolvido como parte do processo seletivo para estágio em desenvolvimento na DTI Digital.

