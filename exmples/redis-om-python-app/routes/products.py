from fastapi import APIRouter, HTTPException
from typing import List
from models.product import Product

router = APIRouter(prefix="/products", tags=["Products"])

@router.post("/", response_model=Product, status_code=201)
async def create_product(product: Product):
    """Create a new product"""
    try:
        return product.save()
    except Exception as e:
        raise HTTPException(status_code=400, detail=f"Error creating product: {str(e)}")

@router.get("/{product_id}", response_model=Product)
async def get_product(product_id: str):
    """Get product by ID"""
    try:
        product = Product.get(product_id)
        return product
    except:
        raise HTTPException(status_code=404, detail="Product not found")

@router.get("/", response_model=List[Product])
async def get_all_products():
    """Get all products"""
    try:
        products = Product.find().all()
        return products
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error fetching products: {str(e)}")

@router.put("/{product_id}", response_model=Product)
async def update_product(product_id: str, product_data: Product):
    """Update product by ID"""
    try:
        # Get existing product
        product = Product.get(product_id)
        
        # Update fields
        product.title = product_data.title
        product.description = product_data.description
        product.price = product_data.price
        product.category = product_data.category
        
        return product.save()
    except Exception as e:
        if "not found" in str(e).lower():
            raise HTTPException(status_code=404, detail="Product not found")
        raise HTTPException(status_code=400, detail=f"Error updating product: {str(e)}")

@router.delete("/{product_id}")
async def delete_product(product_id: str):
    """Delete product by ID"""
    try:
        Product.delete(product_id)
        return {"message": "Product deleted successfully"}
    except:
        raise HTTPException(status_code=404, detail="Product not found")

# Search endpoints
@router.get("/search/by-category/{category}", response_model=List[Product])
async def search_products_by_category(category: str):
    """Search products by category"""
    try:
        products = Product.find(Product.category == category).all()
        return products
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error searching products: {str(e)}")

@router.get("/search/in-stock", response_model=List[Product])
async def get_in_stock_products():
    """Get all in-stock products"""
    try:
        products = Product.find(Product.in_stock == True).all()
        return products
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error searching products: {str(e)}")

@router.get("/search/price-range/{min_price}/{max_price}", response_model=List[Product])
async def search_products_by_price_range(min_price: float, max_price: float):
    """Search products by price range"""
    try:
        products = Product.find(
            (Product.price >= min_price) & (Product.price <= max_price)
        ).all()
        return products
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error searching products: {str(e)}")