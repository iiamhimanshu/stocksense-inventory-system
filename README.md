# stocksense-inventory-system
A full stack inventory management application built with Java Spring Boot and React. Track products, categories, suppliers and stock levels through a secure REST API.

**Status** -> Under active development (Phase 4 complete: DTOs, validation and error handling ✅)

## Tech Stack
- **Backend:** Java 17, Spring Boot, Spring Data JPA, Spring Security
- **Database:** MySQL
- **Frontend:** React
- **Tools:** Git, GitHub, Maven, Postman

## Planned Features

- [x] Product CRUD APIs
- [x] MySQL persistence with Spring Data JPA
- [x] Layered architecture (controller, service, repository)
- [x] DTOs and request validation
- [x] Global exception handling with consistent JSON errors
- [x] Duplicate product name protection (`409 Conflict`)
- [ ] Categories and suppliers
- [ ] Pagination and sorting
- [ ] JWT authentication with roles (Admin / Staff)
- [ ] React dashboard
- [ ] Stock history and low-stock alerts
- [ ] Docker and deployment


## API Endpoints

| Method | URL | Description |
|---|---|---|
| GET | /api/products | List all products |
| GET | /api/products/{id} | Get one product |
| POST | /api/products | Create a product |
| PUT | /api/products/{id} | Update a product |
| DELETE | /api/products/{id} | Delete a product |
| GET | /api/products/low-stock | Products with quantity under 10 |
| GET | /api/products/search?name= | Search by name (case-insensitive) 
| GET | /api/categories/{id}/products | Products in a category |

| Status | When |
|---|---|
| `400 Bad Request` | Validation fails, malformed JSON, or an invalid parameter type |
| `404 Not Found` | Product id does not exist |
| `409 Conflict` | A product with the same name already exists |
| `500 Internal Server Error` | Unexpected server error (details are logged, never returned) |



## How to Run

Setup instructions will be added as the project develops.


## Author
Himanshu Tiwari
****
[GitHub](https://github.com/iiamhimanshu) | [LinkedIn](https://www.linkedin.com/in/himanshut1/)
