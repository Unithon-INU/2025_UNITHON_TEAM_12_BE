# services/gemini_service.py
from typing import List, Dict
import os
import google.generativeai as genai

genai.configure(api_key=os.getenv("GOOGLE_API_KEY"))

class GeminiPromptService:
    def __init__(self):
        self.model = genai.GenerativeModel(model_name="models/gemini-1.5-flash")

    def get_items_for_category(self, category: str, gender: str, age: int, region: str, start: str, end: str) -> List[Dict[str, str]]:
        prompt = f"""
        사용자의 성별은 {gender}, 나이는 {age}세이며, 여행 지역은 {region}입니다.
        여행 기간은 {start}부터 {end}까지입니다.
        '{category}' 카테고리에 해당하는 여행 짐 목록을 추천해주세요.
        각각의 아이템은 이름과 수량만 제공해주세요. 예시: 노트북 1개, 충전기 1개
        """
        response = self.model.generate_content(prompt)
        lines = response.text.strip().split("\n")
        print("📦 Gemini 응답:\n", response.text)
        return self._parse_lines(lines)
        

    def _parse_lines(self, lines: List[str]) -> List[Dict[str, str]]:
        result = []
        for line in lines:
            clean = line.strip("-• ").strip()
            if not clean:
                continue
            if " " in clean:
                name, qty = clean.rsplit(" ", 1)
                try:
                    quantity = int(qty.replace("개", "").strip())
                except ValueError:
                    quantity = 1
                result.append({"name": name.strip(), "quantity": quantity})
            else:
                result.append({"name": clean, "quantity": 1})
        return result
    

    
    def get_trip_concepts(self, title: str, region: str, start: str, end: str, description: str) -> List[str]:
        prompt = f"""
        다음은 사용자의 여행 정보입니다:

        - 여행 제목: {title}
        - 여행 지역: {region}
        - 여행 기간: {start} ~ {end}
        - 여행 설명: {description}

        이 정보를 바탕으로, 짐 구성에 도움이 되는 **여행 컨셉 태그**를 다음 6가지 범주에서 각각 1~2개씩 생성해주세요. 각 태그는 해당 여행 정보에 최대한 맞춰서 실제 의미 있는 요소만 골라주세요.

        범주는 다음과 같습니다:

        1. 동반자 관련 (예: 가족, 아이 동반, 연인, 반려동물 등)
        2. 계절/날씨 관련 (예: 여름, 장마철, 더운 날씨, 일교차 큼 등)
        3. 활동 성격 관련 (예: 도시 관광, 해변 활동, 트레킹, 수영, 렌터카 이용, 대중교통 등)
        4. 숙박 스타일 관련 (예: 호텔 숙박, 게스트하우스, 캠핑 등)
        5. 짐에 영향을 줄 수 있는 상황 (예: 장거리 이동, 약 복용, 생리 예정, 아이용품 필요 등)
        6. 지역 특성 관련 (예: 섬 지역, 국내여행, 국외여행, 대도시, 교통 불편, 언어 장벽 등)

        아래 조건을 반드시 지켜주세요:

        - 각 범주는 반드시 포함되어야 하며, 각 범주별로 1~2개의 태그만 작성해주세요.
        - 태그는 중복되지 않아야 하며, **'국내여행'/'국외여행'**을 지역(region)을 기준으로 정확히 구분해주세요.

        예시 출력:
        동반자: 가족, 아이 동반  
        계절/날씨: 여름, 장마철  
        활동 성격: 해변 활동, 렌터카 이동  
        숙박 스타일: 호텔 숙박  
        짐 영향 요소: 아이용품 필요, 장거리 이동  
        지역 특성: 섬 지역, 국내여행  
        """

        response = self.model.generate_content(prompt)
        print("🏷️ Gemini 카테고리별 태그 응답:\n", response.text)

        # 결과 파싱
        tags = []
        for line in response.text.strip().split("\n"):
            if ":" in line:
                tag_values = line.split(":", 1)[1]
                tags += [t.strip() for t in tag_values.split(",") if t.strip()]
        return tags