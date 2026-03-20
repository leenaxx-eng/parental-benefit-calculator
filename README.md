# Parental Benefit Calculator
A web application that calculates Estonia's parental benefits based on gross salary and the child's birth date.

## Project Overview
- Users can:
    - Enter gross monthly salary
    - Enter child's birth date
    - View a 12-month payment breakdown
- Calculation rules:
    - Salary capped at €4000
    - Daily rate = salary ÷ 30
    - Monthly payment (except first month) = days in month x daily rate
    - First month = days from birth date to month end x daily rate 
- Calculations are automatically saved. A `Benefit ID` is returned, stored in browser's local storage and used to retrive results.
- Project plan is in [notion](https://www.notion.so/Parental-Benefit-Calculator-30be1dbe16b7816babc4e520640446a4)

## Tech Stack
- Frontend: TypeScript
- Backend: Java 17, Spring Boot 
- Database: SQLite
- Testing: JUnit
- Documentation: Swagger(Springdoc)

## Key Decisions
- SQLite for simplicity 

## Running the Application with Docker
### Prerequisites
- Docker Desktop installed
- To verify installation: `docker --version`

### Build and Run
- First run: `docker compose up --build`
- Subsequent runs: `docker compose up`

### Access
- Frontend: `http://localhost:3000`
- Backend: `http://localhost:8080`

### Stop
- `Ctrl + C` or `docker compose down`
- The database file (`backend/benefits.db`) persists and survives containers restarts.

## How to Use the App
### Frontend
#### Create Calculation 
- Enter gross monthly salary (required, >0, max 2 decimals)
- Enter the child's birth date (required, DD/MM/YYYY)
- Click `Calculate`
Output
- Month
- Paid days
- Payment amount
Errors are shown for invalid input

#### Load Calculation 
- `Benefit ID` is returned after calculation
- Enter `Benefit ID` to retrieve results
- `Benefit ID` is saved in browser local storage. 
- Field auto-fills on return visit

## Automated Tests
- Backend tests cover:
    1. Calculation logic
    2. API endpoints
- Run tests with `mvn clean test`

## API Endpoints
- `POST` /api/benefits -> create calculations
- `GET` /api/benefits/{id} -> retrieve calculations
- Full documentation at [Swagger](http://localhost:8080/swagger-ui/index.html)

## Future Improvements

## Contributors
- Bacon Chan
- Janne Rinaldo
- Magdaleena Teinemaa