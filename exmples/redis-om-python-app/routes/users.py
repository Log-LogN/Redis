from fastapi import APIRouter, HTTPException
from typing import List
from models.user import User

router = APIRouter(prefix="/users", tags=["Users"])

@router.post("/", response_model=User, status_code=201)
async def create_user(user: User):
    """Create a new user"""
    try:
        return user.save()
    except Exception as e:
        raise HTTPException(status_code=400, detail=f"Error creating user: {str(e)}")

@router.get("/{user_id}", response_model=User)
async def get_user(user_id: str):
    """Get user by ID"""
    try:
        user = User.get(user_id)
        return user
    except:
        raise HTTPException(status_code=404, detail="User not found")

@router.get("/", response_model=List[User])
async def get_all_users():
    """Get all users"""
    try:
        users = User.find().all()
        return users
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error fetching users: {str(e)}")

@router.put("/{user_id}", response_model=User)
async def update_user(user_id: str, user_data: User):
    """Update user by ID"""
    try:
        # Get existing user
        user = User.get(user_id)
        
        # Update fields
        user.name = user_data.name
        user.email = user_data.email
        user.age = user_data.age
        user.city = user_data.city
        
        return user.save()
    except Exception as e:
        if "not found" in str(e).lower():
            raise HTTPException(status_code=404, detail="User not found")
        raise HTTPException(status_code=400, detail=f"Error updating user: {str(e)}")

@router.delete("/{user_id}")
async def delete_user(user_id: str):
    """Delete user by ID"""
    try:
        User.delete(user_id)
        return {"message": "User deleted successfully"}
    except:
        raise HTTPException(status_code=404, detail="User not found")

# Search endpoints
@router.get("/search/by-city/{city}", response_model=List[User])
async def search_users_by_city(city: str):
    """Search users by city"""
    try:
        users = User.find(User.city == city).all()
        return users
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error searching users: {str(e)}")

@router.get("/search/by-age/{min_age}/{max_age}", response_model=List[User])
async def search_users_by_age_range(min_age: int, max_age: int):
    """Search users by age range"""
    try:
        users = User.find(
            (User.age >= min_age) & (User.age <= max_age)
        ).all()
        return users
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error searching users: {str(e)}")