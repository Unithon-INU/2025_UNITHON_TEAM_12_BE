from fastapi import APIRouter
from pydantic import BaseModel
from typing import List, Dict
from services.gemini_service import GeminiPromptService
from services.gemini_service  import get_packing_recommendation
from services.vertex_service import generate_packing_items
from services.test import VertexPromptService
from schemas.response import CategoryResponse

router = APIRouter()
gemini_service = GeminiPromptService()
vertex_test = VertexPromptService()

class TripConceptRequest(BaseModel):
    title: str
    region: str
    startDate: str
    endDate: str
    description: str

@router.post("/generate-concepts", response_model=List[str])
def generate_concepts(request: TripConceptRequest):
    return gemini_service.get_trip_concepts(
        title=request.title,
        region=request.region,
        start=request.startDate,
        end=request.endDate,
        description=request.description
    )


class TripItemRecommendResponse(BaseModel):
    name: str
    quantity: int

class RecommendRequest(BaseModel):
    gender: str
    age: int
    title: str
    region: str
    startDate: str
    endDate: str
    description: str
    tripType: List[str]

class TripCategoryRecommendResponse(BaseModel):
    category: str
    items: List[TripItemRecommendResponse]

MULTI_CATEGORY_LIST = [
    "의류", "전자기기", "세면도구", "약품", "기타"
]

class RecommendRequest(BaseModel):
    gender: str
    age: int
    title: str
    region: str
    startDate: str
    endDate: str
    description: str
    tripType: List[str]
class CategoryRecommendResponse(BaseModel):
    category: str
    items: List[TripItemRecommendResponse]

@router.post("/recommend", response_model=List[CategoryResponse])
def testtest(request: RecommendRequest):
    result = vertex_test.get_test(
        gender=request.gender,
        age=request.age,
        title=request.title,
        region=request.region,
        start_date=request.startDate,
        end_date=request.endDate,
        description=request.description,
        trip_type=request.tripType
    )
    return result