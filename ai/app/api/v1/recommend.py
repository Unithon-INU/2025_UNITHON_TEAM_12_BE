from fastapi import APIRouter
from pydantic import BaseModel

router = APIRouter()

class RecommendRequest(BaseModel):
    gender: str
    age: int
    region: str
    startDate: str
    endDate: str
    categoryName: str

@router.post("/single-recommend")
def recommend_items(request: RecommendRequest):
    return [
        {"name": "양말", "quantity": 3},
        {"name": "칫솔", "quantity": 1}
    ]

@router.post("/mult-recommend")
def recommend_items(request: RecommendRequest):
    return [
        {"name": "양말", "quantity": 3},
        {"name": "칫솔", "quantity": 1}
    ]