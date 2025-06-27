from fastapi import APIRouter
from pydantic import BaseModel
from typing import List, Dict
from services.gemini_service import GeminiPromptService

router = APIRouter()
gemini_service = GeminiPromptService()

class RecommendRequest(BaseModel):
    gender: str
    age: int
    region: str
    startDate: str
    endDate: str
    categoryName: str


class TripItemRecommendResponse(BaseModel):
    name: str
    quantity: int

# 단일 카테고리 추천
@router.post("/single-recommend", response_model=List[TripItemRecommendResponse])
def recommend_items(request: RecommendRequest):
    return gemini_service.get_items_for_category(
        category=request.categoryName,
        gender=request.gender,
        age=request.age,
        region=request.region,
        start=request.startDate,
        end=request.endDate
    )

MULTI_CATEGORY_LIST = [
    "의류", "전자기기", "세면도구", "약품", "기타"
]

class CategoryRecommendResponse(BaseModel):
    category: str
    items: List[TripItemRecommendResponse]

@router.post("/mult-recommend", response_model=List[CategoryRecommendResponse])
def recommend_all_categories(request: RecommendRequest):
    all_items = []
    for category in MULTI_CATEGORY_LIST:
        items = gemini_service.get_items_for_category(
            category=category,
            gender=request.gender,
            age=request.age,
            region=request.region,
            start=request.startDate,
            end=request.endDate
        )
        all_items.append({
            "category": category,
            "items": items
        })
    return all_items