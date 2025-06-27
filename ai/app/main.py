import os
from dotenv import load_dotenv
import google.generativeai as genai

# .env 로딩 (경로: ai/app/.env)
load_dotenv(dotenv_path=os.path.join(os.path.dirname(__file__), ".env"))
genai.configure(api_key=os.getenv("GOOGLE_API_KEY"))
for m in genai.list_models():
    print(m.name, m.supported_generation_methods)

from fastapi import FastAPI
from api.v1 import recommend

app = FastAPI(
    title="Packit AI API",
    root_path="/ai"  # Swagger 경로 보정용
)

# 여기서는 prefix 제거
app.include_router(recommend.router, tags=["AI Recommend"])