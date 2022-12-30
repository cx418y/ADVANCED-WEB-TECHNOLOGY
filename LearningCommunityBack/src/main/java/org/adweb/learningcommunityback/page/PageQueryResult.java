package org.adweb.learningcommunityback.page;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageQueryResult<T> {

    private int totalPage;
    private List<T> data;

    private PageQueryResult(int totalPage, List<T> data) {
        this.totalPage = totalPage;
        this.data = data;
    }

    public static <E> PageQueryResult<E> of(int totalPage, List<E> data) {
        return new PageQueryResult<>(totalPage, data);
    }
}
