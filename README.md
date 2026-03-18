# parental-benefit-calculator
A web application that calculates Estonia's parental benefits based on gross salary and child's birth date. Built for the Helmes Technical Challenge for kood/Jõhvi students.

## Running the Application with Docker.
**Prerequisites**
- Docker Desktop installed
- Verify Docker works: `docker --version`

**Build and start the application**
Run this during first build or when there is a code change at frontend or backend
- `docker compose up --build`
Run this when there has not been a code change
- `docker compose up`

**Access the application**
- Frontend: `http://localhost:3000`
- Backend: `http://localhost:8080`

**Stop the application**
- `Ctrl + C` or `docker compose down`

The containers will stop but the database file `backend/benefits.db` will persist