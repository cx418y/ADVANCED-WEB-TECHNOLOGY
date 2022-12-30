package org.adweb.learningcommunityback;

import org.junit.jupiter.api.Test;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
class LearningCommunityBackApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testPaging() {
        List<Integer> li = Stream
                .of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14)
                .filter(n -> n % 2 == 0)
                .toList();

        PagedListHolder<Integer> page = new PagedListHolder<>(li);
        page.setPage(1);
        page.setPageSize(3);
        page.getPageList().forEach(System.out::println);
        System.out.println(page.getPageCount());


    }

}
