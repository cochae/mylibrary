package com.example.Mylibrary.Book;

import java.util.HashMap;
import java.util.Map;

public class CategoryMapper {

    // 이미 나누어진 카테고리 이름과 그에 맞는 ID 매핑
    private static final Map<String, Integer> categoryIds = new HashMap<>();

    static {
        // 카테고리 이름과 그에 해당하는 ID를 직접 매핑합니다.
        categoryIds.put("국내도서>건강/취미", 55889);
        categoryIds.put("국내도서>경제경영", 170);
        categoryIds.put("국내도서>공무원 수험서", 33617);
        categoryIds.put("국내도서>과학", 50912);
        categoryIds.put("국내도서>달력/기타", 55890);
        categoryIds.put("국내도서>대학교재", 8257);
        categoryIds.put("국내도서>만화", 2551);
        categoryIds.put("국내도서>사회과학", 50917);
        categoryIds.put("국내도서>소설/시/희곡", 38396);
        categoryIds.put("국내도서>수험서/자격증", 2913);
        categoryIds.put("국내도서>어린이", 1108);
        categoryIds.put("국내도서>에세이", 51376);
        categoryIds.put("국내도서>여행", 51320);
        categoryIds.put("국내도서>역사", 50926);
        categoryIds.put("국내도서>예술/대중문화", 50910);
        categoryIds.put("국내도서>외국어", 1322);
        categoryIds.put("국내도서>요리/살림", 50921);
        categoryIds.put("국내도서>유아", 13789);
        categoryIds.put("국내도서>인문학", 51377);
        categoryIds.put("국내도서>자기계발", 336);
        categoryIds.put("국내도서>잡지", 55890);
        categoryIds.put("국내도서>장르소설", 2919);
        categoryIds.put("국내도서>전집/중고전집", 1230);
        categoryIds.put("국내도서>종교/역학", 50946);
        categoryIds.put("국내도서>좋은부모", 2030);
        categoryIds.put("국내도서>청소년", 2551);
        categoryIds.put("국내도서>컴퓨터/모바일", 351);
        categoryIds.put("국내도서>초등학교참고서", 1383);
        categoryIds.put("국내도서>중학교참고서", 1384);
        categoryIds.put("국내도서>고등학교참고서", 1385);
    }

    // 카테고리 이름으로 ID를 찾는 메서드
    public static Integer getCategoryId(String categoryName) {
        return categoryIds.get(categoryName);
    }
}
