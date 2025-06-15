package com.packit.api.common.Init;

import com.packit.api.common.Gender;
import com.packit.api.domain.TemplateItem.entity.TemplateItem;
import com.packit.api.domain.TemplateItem.repository.TemplateItemRepository;
import com.packit.api.domain.category.entity.Category;
import com.packit.api.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final TemplateItemRepository templateItemRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) return;

        Category essentials = categoryRepository.save(Category.of("필수품"));
        Category clothes = categoryRepository.save(Category.of("의류 & 액세서리"));
        Category hygiene = categoryRepository.save(Category.of("세면도구 & 위생품"));
        Category health = categoryRepository.save(Category.of("건강 & 응급 용품"));
        Category electronics = categoryRepository.save(Category.of("전자기기"));

        templateItemRepository.saveAll(List.of(
                // 필수품
                TemplateItem.of(essentials, "여권", 1, Gender.BOTH),
                TemplateItem.of(essentials, "신분증", 1, Gender.BOTH),
                TemplateItem.of(essentials, "항공권", 1, Gender.BOTH),
                TemplateItem.of(essentials, "기차표", 1, Gender.BOTH),
                TemplateItem.of(essentials, "숙소 예약 확인서", 1, Gender.BOTH),
                TemplateItem.of(essentials, "신용카드", 1, Gender.BOTH),
                TemplateItem.of(essentials, "현금", 1, Gender.BOTH),

                // 의류 & 액세서리
                TemplateItem.of(clothes, "상의", 2, Gender.BOTH),
                TemplateItem.of(clothes, "하의", 2, Gender.BOTH),
                TemplateItem.of(clothes, "외투", 1, Gender.BOTH),
                TemplateItem.of(clothes, "속옷", 3, Gender.BOTH),
                TemplateItem.of(clothes, "양말", 3, Gender.BOTH),
                TemplateItem.of(clothes, "신발", 1, Gender.BOTH),
                TemplateItem.of(clothes, "모자", 1, Gender.BOTH),
                TemplateItem.of(clothes, "선글라스", 1, Gender.BOTH),
                TemplateItem.of(clothes, "장갑", 1, Gender.BOTH),

                // 세면도구 & 위생품
                TemplateItem.of(hygiene, "칫솔, 치약", 1, Gender.BOTH),
                TemplateItem.of(hygiene, "샴푸, 린스", 1, Gender.BOTH),
                TemplateItem.of(hygiene, "바디워시", 1, Gender.BOTH),
                TemplateItem.of(hygiene, "기초 스킨케어", 1, Gender.BOTH),
                TemplateItem.of(hygiene, "선크림", 1, Gender.BOTH),
                TemplateItem.of(hygiene, "화장품", 1, Gender.BOTH),
                TemplateItem.of(hygiene, "물티슈", 1, Gender.BOTH),
                TemplateItem.of(hygiene, "휴지", 1, Gender.BOTH),
                TemplateItem.of(hygiene, "생리용품", 1, Gender.FEMALE),

                // 건강 & 응급 용품
                TemplateItem.of(health, "개인 내복약", 1, Gender.BOTH),
                TemplateItem.of(health, "진통제", 1, Gender.BOTH),
                TemplateItem.of(health, "소화제", 1, Gender.BOTH),
                TemplateItem.of(health, "알러지약", 1, Gender.BOTH),
                TemplateItem.of(health, "반창고 & 연고", 1, Gender.BOTH),
                TemplateItem.of(health, "소독약", 1, Gender.BOTH),
                TemplateItem.of(health, "마스크", 3, Gender.BOTH),

                // 전자기기
                TemplateItem.of(electronics, "핸드폰", 1, Gender.BOTH),
                TemplateItem.of(electronics, "노트북 & 태블릿", 1, Gender.BOTH),
                TemplateItem.of(electronics, "충전기", 1, Gender.BOTH),
                TemplateItem.of(electronics, "보조배터리", 1, Gender.BOTH),
                TemplateItem.of(electronics, "이어폰, 헤드폰", 1, Gender.BOTH)
        ));
    }
}
