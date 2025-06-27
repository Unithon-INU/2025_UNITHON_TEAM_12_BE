from fastapi import FastAPI
from api.v1 import recommend

app = FastAPI(
    title="Packit AI API",
    root_path="/ai"  # Swagger 경로 보정용
)

# 여기서는 prefix 제거
app.include_router(recommend.router, tags=["AI Recommend"])