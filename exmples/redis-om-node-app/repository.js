import { Schema, Repository } from 'redis-om'
import { redis } from './redis.js'

const schema = new Schema('book', {
  title: { type: 'string' }, 
  author: { type: 'string' }, 
  summary: { type: 'text' },  
  publisher: { type: 'string' },  
  year: { type: 'number' }, 
  pages: { type: 'number' }
})

export const bookRepository = new Repository(schema, redis)

await bookRepository.createIndex()
