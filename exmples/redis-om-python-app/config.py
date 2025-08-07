import os
from dotenv import load_dotenv

load_dotenv()

class Settings:
    # Redis settings
    REDIS_HOST = os.getenv("REDIS_HOST", "localhost")
    REDIS_PORT = int(os.getenv("REDIS_PORT", 6379))
    REDIS_PASSWORD = os.getenv("REDIS_PASSWORD", None)
    REDIS_DB = int(os.getenv("REDIS_DB", 0))
    
    # App settings
    APP_HOST = os.getenv("APP_HOST", "0.0.0.0")
    APP_PORT = int(os.getenv("APP_PORT", 8000))
    APP_DEBUG = os.getenv("APP_DEBUG", "True").lower() == "true"
    
    @property
    def redis_url(self):
        if self.REDIS_PASSWORD:
            return f"redis://:{self.REDIS_PASSWORD}@{self.REDIS_HOST}:{self.REDIS_PORT}/{self.REDIS_DB}"
        return f"redis://{self.REDIS_HOST}:{self.REDIS_PORT}/{self.REDIS_DB}"

settings = Settings()