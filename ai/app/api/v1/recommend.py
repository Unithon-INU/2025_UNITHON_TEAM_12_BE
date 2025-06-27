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

# 전체 카테고리(카테고리명 + 아이템 리스트 포함) 추천 API
@router.post("/recommend", response_model=List[TripCategoryRecommendResponse])
def recommend_categories_with_items(request: RecommendRequest):
    result = gemini_service.get_recommendation(request)

    response = []
    for category, items in result.items():
        trip_items = [
            TripItemRecommendResponse(name=item["name"], quantity=item["quantity"])
            for item in items
        ]
        response.append(TripCategoryRecommendResponse(category=category, items=trip_items))
    return response

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




@router.post("/recommendVertex")
def recommend_vertex(request: RecommendRequest):
    result = generate_packing_items(
        gender=request.gender,
        age=request.age,
        title=request.title,
        region=request.region,
        start_date=request.startDate,
        end_date=request.endDate,
        description=request.description,
        trip_type=request.tripType
    )
    return {"result": result}
class RecommendRequest(BaseModel):
    gender: str
    age: int
    title: str
    region: str
    startDate: str
    endDate: str
    description: str
    tripType: List[str]


@router.post("/test", response_model=List[CategoryResponse])
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