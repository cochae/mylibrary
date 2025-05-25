package com.example.Mylibrary.Book;

import java.util.HashMap;
import java.util.Map;

public class CategoryMapper {
    private static final Map<String, Integer> categoryIds = new HashMap<>();

    static {
        categoryIds.put("국내도서>건강/취미", 55890); //ok
        categoryIds.put("국내도서>경제경영", 170); //ok
        categoryIds.put("국내도서>공무원 수험서", 34582); //ok
        categoryIds.put("국내도서>과학", 987);  //ok
        categoryIds.put("국내도서>달력/기타", 4395); //ok
        categoryIds.put("국내도서>대학교재", 8257);  //ok
        categoryIds.put("국내도서>만화", 2551); //ok
        categoryIds.put("국내도서>사회과학", 798); //ok
        categoryIds.put("국내도서>소설/시/희곡", 1); //ok
        categoryIds.put("국내도서>수험서/자격증", 1383); //ok
        categoryIds.put("국내도서>어린이", 1108); //ok
        categoryIds.put("국내도서>에세이", 55889); //ok
        categoryIds.put("국내도서>여행", 1196); //ok
        categoryIds.put("국내도서>역사", 74); //ok
        categoryIds.put("국내도서>예술/대중문화", 517); //ok
        categoryIds.put("국내도서>외국어", 1322); //ok
        categoryIds.put("국내도서>요리/살림", 50921);
        categoryIds.put("국내도서>유아", 13789); //ok
        categoryIds.put("국내도서>인문학", 656); //ok
        categoryIds.put("국내도서>자기계발", 336); //ok
        categoryIds.put("국내도서>잡지", 2913); //ok
        categoryIds.put("국내도서>장르소설", 112011); //ok
        categoryIds.put("국내도서>전집/중고전집", 17195); //ok
        categoryIds.put("국내도서>종교/역학", 1237); //ok
        categoryIds.put("국내도서>좋은부모", 2030); //ok
        categoryIds.put("국내도서>청소년", 1137); //ok
        categoryIds.put("국내도서>컴퓨터/모바일", 351); //ok
        categoryIds.put("국내도서>초등학교참고서", 50246); //ok
        categoryIds.put("국내도서>중학교참고서", 76000); //ok
        categoryIds.put("국내도서>고등학교참고서", 76001); //ok
    }

    // 카테고리 이름으로 ID를 찾는 메서드
    public static Integer getCategoryId(String categoryName) {
        return categoryIds.get(categoryName);
    }
}
