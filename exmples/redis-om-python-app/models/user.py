from redis_om import HashModel, get_redis_connection
from typing import Optional
from datetime import datetime
from pydantic import Field, EmailStr
from config import settings

# Redis connection
redis = get_redis_connection(url=settings.redis_url)

class User(HashModel):
    name: str = Field(index=True, description="User's full name")
    email: EmailStr = Field(index=True, description="User's email address")
    age: int = Field(index=True, ge=1, le=120, description="User's age")
    city: str = Field(index=True, description="User's city")
    created_at: datetime = Field(default_factory=datetime.now, description="Creation timestamp")
    
    class Meta:
        database = redis
        global_key_prefix = "user"
    
    def __str__(self):
        return f"User(name='{self.name}', email='{self.email}')"