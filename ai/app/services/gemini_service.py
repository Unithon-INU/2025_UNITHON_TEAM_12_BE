# services/gemini_service.py
from typing import List, Dict
import os
import google.generativeai as genai

genai.configure(api_key=os.getenv("GOOGLE_API_KEY"))

class GeminiPromptService:
    def __init__(self):
        self.model = genai.GenerativeModel("gemini-pro")

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