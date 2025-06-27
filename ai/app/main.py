import os
from dotenv import load_dotenv

# .env 로딩 (경로: ai/app/.env)
load_dotenv(dotenv_path=os.path.join(os.path.dirname(__file__), ".env"))

from fastapi import FastAPI
from api.v1 import recommend
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI(
    title="Packit AI API",
    root_path="/ai"  # Swagger 경로 보정용
)

# 여기서는 prefix 제거
app.include_router(recommend.router, tags=["AI Recommend"])

# CORS 설정 추가
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 나중에 보안상 ["http://localhost:3000"] 등으로 제한 가능
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

