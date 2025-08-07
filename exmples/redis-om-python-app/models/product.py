from redis_om import HashModel, get_redis_connection
from typing import Optional
from datetime import datetime
from pydantic import Field
from config import settings

# Redis connection
redis = get_redis_connection(url=settings.redis_url)

class Product(HashModel):
    title: str = Field(index=True, description="Product title")
    description: str = Field(description="Product description")
    price: float = Field(index=True, ge=0, description="Product price")
    category: str = Field(index=True, description="Product category")
    created_at: datetime = Field(default_factory=datetime.now, description="Creation timestamp")
    
    class Meta:
        database = redis
        global_key_prefix = "product"
    
    def __str__(self):
        return f"Product(title='{self.title}', price=${self.price})"