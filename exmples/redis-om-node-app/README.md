# Book API

A RESTful API for managing books built with Express.js and Redis OM.

## Features

- Create, read, update, and delete books
- Search books by author
- Full-text search capabilities with Redis OM
- JSON-based API responses

## Prerequisites

- Node.js (v14 or higher)
- Redis server running locally or remotely

## Installation

```bash
npm install
```

## Running the Application

```bash
npm start
```

The server will start on port 3000.

## API Endpoints

### Get API Information
```bash
curl http://localhost:3000/
```

### Create a New Book
```bash
curl -X POST http://localhost:3000/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "summary": "A classic American novel set in the Jazz Age",
    "publisher": "Scribner",
    "year": 1925,
    "pages": 180
  }'
```

### Get All Books
```bash
curl http://localhost:3000/books
```

### Get a Specific Book by ID
```bash
curl http://localhost:3000/book/01234567890123456789012345
```

### Update or Create a Book with Specific ID
```bash
curl -X PUT http://localhost:3000/book/01234567890123456789012345 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "1984",
    "author": "George Orwell",
    "summary": "A dystopian social science fiction novel",
    "publisher": "Secker & Warburg",
    "year": 1949,
    "pages": 328
  }'
```

### Search Books by Author
```bash
curl "http://localhost:3000/books/by-author/George%20Orwell"
```

### Delete a Book
```bash
curl -X DELETE http://localhost:3000/book/01234567890123456789012345
```

## Sample Responses

### Creating a Book
```json
{
  "id": "01HK8QZ7XVQZ2YW5J1M3N4P5Q6"
}
```

### Getting a Book
```json
{
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "summary": "A classic American novel set in the Jazz Age",
  "publisher": "Scribner",
  "year": 1925,
  "pages": 180
}
```

### Getting All Books
```json
[
  {
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "summary": "A classic American novel set in the Jazz Age",
    "publisher": "Scribner",
    "year": 1925,
    "pages": 180
  },
  {
    "title": "1984",
    "author": "George Orwell",
    "summary": "A dystopian social science fiction novel",
    "publisher": "Secker & Warburg",
    "year": 1949,
    "pages": 328
  }
]
```

## Book Schema

| Field | Type | Description |
|-------|------|-------------|
| title | string | Book title |
| author | string | Book author |
| summary | text | Book summary (searchable) |
| publisher | string | Publisher name |
| year | number | Publication year |
| pages | number | Number of pages |

## Error Handling

The API returns appropriate HTTP status codes:
- `200` - Success
- `404` - Book not found
- `500` - Server error

## Development

The application uses nodemon for development, which automatically restarts the server when files change.

```bash
npm start
```

## Dependencies

- **express**: Web framework for Node.js
- **redis**: Redis client for Node.js
- **redis-om**: Object mapping for Redis

## Environment Variables

The application uses the following environment variables:
- `npm_package_name` - Application name from package.json
- `npm_package_version` - Application version from package.json
