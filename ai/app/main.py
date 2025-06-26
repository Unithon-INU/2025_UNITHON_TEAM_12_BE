from fastapi import FastAPI
from app.api.v1 import recommend

app = FastAPI(title="Packit AI API")

app.include_router(recommend.router, prefix="/api/v1/recommend", tags=["AI Recommend"])