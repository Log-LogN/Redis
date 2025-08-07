# Redis OM Python CRUD Application

A simple CRUD API built with Redis OM Python and FastAPI.

## Features
- User management (CRUD operations)
- Product management (CRUD operations)
- Search functionality
- Automatic API documentation
- Environment configuration

## Models
- **User**: name, email, age, city
- **Product**: title, description, price, category, in_stock

## API Endpoints

### Users
- `POST /users/` - Create user
- `GET /users/` - Get all users
- `GET /users/{user_id}` - Get user by ID
- `PUT /users/{user_id}` - Update user
- `DELETE /users/{user_id}` - Delete user
- `GET /users/search/by-city/{city}` - Search by city
- `GET /users/search/by-age/{min_age}/{max_age}` - Search by age range

### Products
- `POST /products/` - Create product
- `GET /products/` - Get all products
- `GET /products/{product_id}` - Get product by ID
- `PUT /products/{product_id}` - Update product
- `DELETE /products/{product_id}` - Delete product
- `GET /products/search/by-category/{category}` - Search by category
- `GET /products/search/in-stock` - Get in-stock products
- `GET /products/search/price-range/{min_price}/{max_price}` - Search by price

## Setup Instructions

1. Install dependencies
2. Start Redis server
3. Configure environment
4. Run the application
5. Test the API

See below for detailed steps.

How to Start the Application
Step 1: Create Project Directory
bashmkdir redis-om-crud
cd redis-om-crud
Step 2: Create Virtual Environment (Recommended)
bash# Create virtual environment
python -m venv venv

# Activate virtual environment
# On Windows:
venv\Scripts\activate
# On macOS/Linux:
source venv/bin/activate
Step 3: Install Dependencies
bashpip install -r requirements.txt
Step 4: Start Redis Server
bash# Option 1: Using Docker (Recommended)
docker run -d -p 6379:6379 --name redis-server redis/redis-stack

# Option 2: Using local Redis installation
redis-server

# Option 3: Redis with persistence
docker run -d -p 6379:6379 -v redis-data:/data --name redis-server redis/redis-stack
Step 5: Configure Environment
Create the .env file with your Redis configuration (or use defaults).
Step 6: Create Directory Structure
Create all the directories and files as shown above.
Step 7: Run the Application
bash# Method 1: Direct execution
python main.py

# Method 2: Using uvicorn
uvicorn main:app --reload --host 0.0.0.0 --port 8000

# Method 3: Production mode
uvicorn main:app --host 0.0.0.0 --port 8000 --workers 4
Step 8: Access the Application

API Documentation: http://localhost:8000/docs
ReDoc Documentation: http://localhost:8000/redoc
Health Check: http://localhost:8000/health