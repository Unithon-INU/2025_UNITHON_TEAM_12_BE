from fastapi import APIRouter
from pydantic import BaseModel
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


# 단일 카테고리 추천
@router.post("/single-recommend")
def recommend_items(request: RecommendRequest) -> List[Dict[str, str]]:
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

@router.post("/mult-recommend")
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