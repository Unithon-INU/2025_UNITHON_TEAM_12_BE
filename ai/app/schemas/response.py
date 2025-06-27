from typing import List, Union
from pydantic import BaseModel

class ItemResponse(BaseModel):
    name: str
    quantity: Union[int, str]  # "적당량", "필요량" 같은 값도 있으므로 str 포함

class CategoryResponse(BaseModel):
    category: str
    items: List[ItemResponse]