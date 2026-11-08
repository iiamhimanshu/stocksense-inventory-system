# stocksense-inventory-system
A full stack inventory management application built with Java Spring Boot and React. Track products, categories, suppliers and stock levels through a secure REST API.

**Status** -> Under active development (Phase 2: Product CRUD APIs ✅)

## Tech Stack
- **Backend:** Java 17, Spring Boot, Spring Data JPA, Spring Security
- **Database:** MySQL
- **Frontend:** React
- **Tools:** Git, GitHub, Maven, Postman

## Planned Features

- [x] Product CRUD APIs
- [ ] MySQL database integration
- [ ] Categories and suppliers
- [ ] JWT authentication with roles (Admin / Staff)
- [ ] React dashboard
- [ ] Low-stock alerts and stock history
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

## How to Run

Setup instructions will be added as the project develops.


## Author
Himanshu Tiwari
****
[GitHub](https://github.com/iiamhimanshu) | [LinkedIn](https://www.linkedin.com/in/himanshut1/)
