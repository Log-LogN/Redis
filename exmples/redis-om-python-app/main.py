from fastapi import FastAPI
from routes import users_router, products_router
from config import settings
import uvicorn

# Create FastAPI app
app = FastAPI(
    title="Redis OM CRUD API",
    description="A simple CRUD API using Redis OM Python",
    version="1.0.0",
    docs_url="/docs",
    redoc_url="/redoc"
)

# Include routers
app.include_router(users_router)
app.include_router(products_router)

@app.get("/")
async def root():
    """Root endpoint"""
    return {
        "message": "Redis OM CRUD API",
        "version": "1.0.0",
        "docs": "/docs",
        "redoc": "/redoc"
    }

@app.get("/health")
async def health_check():
    """Health check endpoint"""
    return {"status": "healthy", "redis": "connected"}

if __name__ == "__main__":
    uvicorn.run(
        "main:app",
        host=settings.APP_HOST,
        port=settings.APP_PORT,
        reload=settings.APP_DEBUG
    )