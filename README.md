# Package Management – Full Stack Coding Exercise

REST API and React UI for managing **packages** (each package has a name, description, and one or more **products**). Package prices are calculated from product prices and can be shown in a chosen currency using live exchange rates.

## Features

- **Backend (Java 17, Spring Boot 3)**
  - **Packages API**: Create, retrieve, update, delete, and list packages.
  - **Products**: Sourced from external API (`product-service.herokuapp.com`) with Basic auth (user/pass).
  - **Exchange rates**: [Frankfurter API](https://www.frankfurter.app/) (configured as `api.frankfurter.dev`). Default currency is USD when none is specified.
  - **Package attributes**: ID, name, description, products (one or more), total price (with optional currency).
  - **Security**: HTTP Basic authentication (user/pass). CORS enabled for the React app.
  - **Docs**: Swagger UI at `/swagger-ui.html`, OpenAPI at `/v3/api-docs`.

- **Frontend (React, Vite)**
  - List all packages, create, edit, delete.
  - Select products from the catalog; form only submits when every product is from the list.
  - Choose currency for package price; total and per-product prices use the current exchange rate.

## Acceptance criteria (from spec)

| Requirement | Status |
|-------------|--------|
| Backend: Java / Spring Boot | Yes |
| Frontend: React | Yes |
| API: Create / Retrieve / Update / Delete / List packages | Yes |
| UI demonstrates at least “List all Packages” | Yes (plus Create, Edit, Delete) |
| User can specify currency for package prices | Yes |
| Price calculation uses current exchange rate | Yes |
| API defaults to USD if no currency specified | Yes |
| Exchange rate source: Frankfurter (suggested) | Yes (`api.frankfurter.dev`) |
| Package: ID, Name, Description, Products, Price | Yes |
| Product API: Basic auth user/pass | Yes (outgoing calls use user/pass) |

## How to run locally

### Prerequisites

- **Backend**: JDK 17+, Maven 3.6+
- **Frontend**: Node.js 18+, npm

### 1. Run the backend

From the project root (same folder as `pom.xml`):

```bash
./mvnw spring-boot:run
```

Or build and run the JAR:

```bash
./mvnw clean package -DskipTests
java -jar target/codingexercise-*.jar
```

- API base: **http://localhost:8077**
- Swagger UI: http://localhost:8077/swagger-ui.html  
- Login for Swagger / API: **user** / **pass**

### 2. Run the frontend

Clone the `frontend` from the repository:

```bash
npm install
npm run dev
```

- App: **http://localhost:5173**
- Vite proxies `/api` to `http://localhost:8077` and adds Basic auth, so the UI talks to the backend without CORS issues.

### 3. Use the app

1. Open http://localhost:5173 (with backend already running).
2. Use “Refresh” to load packages, “Create package” to add one.
3. For each package row, pick products from the dropdown (all must be from the product list to submit).
4. Choose currency; total and per-product prices update with the exchange rate.

### Direct API (no frontend)

- List packages:  
  `curl -u user:pass http://localhost:8077/packages`
- Create package (example):  
  `curl -u user:pass -X POST http://localhost:8077/packages -H "Content-Type: application/json" -d '{"packageName":"My Pack","packageDescription":"Test","priceCurrency":"USD","products":[{"productId":"<id-from-products-api>","productName":"...","usdPrice":10.0}]}'`

## Configuration

- **Backend** (`src/main/resources/application.yml`):
  - **Database**: Default is **H2** in-memory (no setup). If you prefer not to use H2, a **MySQL** profile is included: run with `--spring.profiles.active=mysql` and set the datasource URL, username, and password in the `spring.config.activate.on-profile: mysql` section (e.g. create a `codingexercise` database and update credentials).
  - `app.exchange-rate.url`: Frankfurter API URL (default: `https://api.frankfurter.dev/v1/latest?base=USD`).
  - `app.product-service.url`: Product API base URL (default: `https://product-service.herokuapp.com/api/v1/products`).
  - `app.security.username` / `app.security.password`: API Basic auth credentials (default: **user** / **pass**). If you change these, update the frontend proxy auth (e.g. in `frontend/vite.config.js`) when calling the backend in dev.

- **Frontend**: Uses `/api` as base URL in dev (proxied to backend). Optional env (see `frontend/.env.example`): `VITE_API_URL` for direct backend URL; `VITE_API_USERNAME` and `VITE_API_PASSWORD` for API Basic auth (must match backend `app.security`). Use `.env.local` for local overrides (gitignored).

## Project layout (backend)

- `controller/` – REST endpoints (packages, products, exchange-rates).
- `service/`, `service/impl/` – Package, product, and exchange-rate logic.
- `dto/` – Request/response DTOs; validation on `ProductsPackageDto` and `ProductDto`.
- `model/`, `repository/` – JPA entities and Spring Data repositories.
- `config/` – Security (Basic auth, CORS), RestTemplate.
- `exception/` – Global exception handler and validation error mapping.

Tests: `./mvnw test` (unit and integration; Checkstyle/PMD can be skipped with `-Dcheckstyle.skip=true -Dpmd.skip=true`).
