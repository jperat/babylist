package com.jperat.babylist.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Page;

public class PaginationTest {

    @Test
    public void testAll() {
        Pagination pagination = new Pagination(getPage());
        Assertions.assertEquals(20, pagination.getTotalElements());
        Assertions.assertTrue(pagination.hasNext());
        Assertions.assertFalse(pagination.hasPrevious());
    }

    private Page getPage() {
        Page page = Mockito.mock(Page.class);
        when(page.getTotalElements()).thenReturn((long)20);
        when(page.getTotalPages()).thenReturn(2);
        when(page.hasPrevious()).thenReturn(false);
        when(page.hasNext()).thenReturn(true);
        return page;
    }
}
