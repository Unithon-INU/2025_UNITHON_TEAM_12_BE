# app/api/v1/schemas/recommend_dto.py
from pydantic import BaseModel
from typing import List

class RecommendRequest(BaseModel):
    gender: str
    age: int
    title: str
    region: str
    startDate: str
    endDate: str
    description: str
    tripType: List[str]

from pydantic import BaseModel
from typing import List

class TripRecommendationRequest(BaseModel):
    gender: str  # "MALE" or "FEMALE"
    age: int
    title: str
    region: str
    start_date: str  # "2025-07-01"
    end_date: str    # "2025-07-04"
    description: str
    trip_type: List[str]  # ["가족여행", "여름"]

class RecommendedItem(BaseModel):
    name: str
    quantity: int

class CategoryResponse(BaseModel):
    category: str
    items: List[RecommendedItem]

class TripRecommendationResponse(BaseModel):
    recommendations: List[CategoryResponse]