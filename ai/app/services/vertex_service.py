import os
from typing import List

from dotenv import load_dotenv
from fastapi import APIRouter
from pydantic import BaseModel

from google.cloud import aiplatform_v1beta1 as aiplatform
from google.protobuf import json_format
from google.protobuf.struct_pb2 import Value

load_dotenv()
router = APIRouter()

PROJECT_ID = "packit-463009"
LOCATION = "us-central1"  # Vertex AI 지원 지역 중 하나
MODEL_NAME = "gemini-1.5-flash-preview-0514"  # 최신 미리보기 모델

client = aiplatform.PredictionServiceClient()
model_path = f"projects/{PROJECT_ID}/locations/{LOCATION}/publishers/google/models/{MODEL_NAME}"


def generate_packing_items(
    gender: str,
    age: int,
    title: str,
    region: str,
    start_date: str,
    end_date: str,
    description: str,
    trip_type: list[str]
) -> str:
    client = genai.Client(
        vertexai=True,
        project="packit-463009",
        location="global",
    )

    prompt = f"""You will be provided with the following travel information:

*   Gender: {gender} (\"MALE\" or \"FEMALE\")
*   Age: {age} (e.g., 28)
*   Title: {title} (Trip title)
*   Region: {region} (Travel destination)
*   Start Date: {start_date} (e.g., \"2025-07-01\")
*   End Date: {end_date} (e.g., \"2025-07-04\")
*   Description: {description} (Trip description)
*   Trip Type: {trip_type} (e.g., \\[\"Family Trip\", \"Vacation\"])

CATEGORY\\_LIST = \\[\"필수품\", \"의류\", \"전자기기\", \"세면도구\", \"의약품\", \"기타\"]

Your recommendation must:
- Be tailored to the travel details (gender, age, region, season, duration, purpose).
- Avoid parenthesis ( ) in item names.
- Include at least 10 items per category where possible.
- Use simple Korean for item names (e.g., \"반팔 티셔츠\", not \"반팔 셔츠 (더운 날씨용)\").

**Special Considerations**:
- If trip type includes \"해외\": include 여권, 항공권, 현지 화폐, 비상연락처 등
- If includes \"여름\", \"더운 날씨\": 선크림, 모자, 선글라스, 얇은 옷 등
- If includes \"겨울\", \"추운 날씨\": 외투, 장갑, 털모자, 귀도리 등
- If includes \"비오는날씨\": 우산, 우비
- If includes \"생리기간\": 생리용품
- If includes \"아이 동반\": 아이 간식, 장난감, 유모차 등
- If includes \"호텔 숙박\": 세면도구 생략 가능, 단 생리용품/스킨케어 포함
- Always include basics: 신분증, 숙소 예약 확인서, 신용카드, 현금, 충전기, 보조배터리, 속옷, 양말 등

For each category, recommend relevant items based on the user's travel information. The response should be in JSON format, following the example below:

[
  {
    "category": "필수품",
    "items": [
      { "name": "여권", "quantity": 1 },
      { "name": "지갑", "quantity": 1 }
    ]
  },
  {
    "category": "의류",
    "items": [
      { "name": "반팔 티셔츠", "quantity": 3 },
      { "name": "속옷", "quantity": 4 }
    ]
  },
  {
    "category": "전자기기",
    "items": [
      { "name": "휴대폰 충전기", "quantity": 1 },
      { "name": "카메라", "quantity": 1 }
    ]
  },
  {
    "category": "세면도구",
    "items": [
      { "name": "칫솔", "quantity": 1 },
      { "name": "샴푸", "quantity": 1 }
    ]
  },
  {
    "category": "의약품",
    "items": [
      { "name": "진통제", "quantity": 1 },
      { "name": "상비약", "quantity": 1 }
    ]
  },
  {
    "category": "기타",
    "items": [
      { "name": "선크림", "quantity": 1 },
      { "name": "물티슈", "quantity": 1 }
    ]
  }
]

Output **only** valid JSON list in the above format with proper Korean item names and correct quantities."""
    system_instruction = """You are an expert travel assistant designed to create personalized packing lists for travelers. Analyze the user's travel information, including gender, age, destination, duration, and type of trip, to generate a packing list tailored to their specific needs. The packing list should be categorized into six fixed categories and provided in JSON format."""

    contents = [types.Content(role="user", parts=[types.Part.from_text(text=prompt)])]

    config = types.GenerateContentConfig(
        temperature=1,
        top_p=0.95,
        seed=0,
        max_output_tokens=8192,
        safety_settings=[
            types.SafetySetting(category="HARM_CATEGORY_HATE_SPEECH", threshold="OFF"),
            types.SafetySetting(category="HARM_CATEGORY_DANGEROUS_CONTENT", threshold="OFF"),
            types.SafetySetting(category="HARM_CATEGORY_SEXUALLY_EXPLICIT", threshold="OFF"),
            types.SafetySetting(category="HARM_CATEGORY_HARASSMENT", threshold="OFF"),
        ],
        response_mime_type="application/json",
        response_schema={"type": "OBJECT", "properties": {"response": {"type": "STRING"}}},
        system_instruction=[types.Part.from_text(text=system_instruction)],
        thinking_config=types.ThinkingConfig(thinking_budget=-1),
    )

    result = ""
    for chunk in client.models.generate_content_stream(
        model="gemini-2.5-flash",
        contents=contents,
        config=config,
    ):
        result += chunk.text
    return result