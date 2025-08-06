import { Router } from 'express'
import { EntityId } from 'redis-om'

import { bookRepository as repository } from './repository.js'

export const router = Router()

// PUT: Update or create a book by ID
router.put('/book/:id', async (req, res) => {
  const book = await repository.save(req.params.id, req.body)
  res.send({ id: book[EntityId] })
})

// GET: Fetch a book by ID
router.get('/book/:id', async (req, res) => {
  const book = await repository.fetch(req.params.id)
  res.send(book)
})

// DELETE: Remove a book by ID
router.delete('/book/:id', async (req, res) => {
  await repository.remove(req.params.id)
  res.type('application/json')
  res.send('"OK"')
})

// POST: Create a new book with auto-generated ID
router.post('/books', async (req, res) => {
  const book = await repository.save(req.body)
  res.send({ id: book[EntityId] })
})
 
// GET: Fetch all books
router.get('/books', async (req, res) => {
  const books = await repository.search().returnAll()
  res.send(books)
})

// GET: Fetch books by author
router.get('/books/by-author/:author', async (req, res) => {
  const author = req.params.author
  const books = await repository.search().where('author').eq(author).returnAll()
  res.send(books)
})

