package com.sams.demo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetLimitPageRequest implements Pageable {

    private int offset;
    private int limit;
    private final Sort sort;

    public OffsetLimitPageRequest(int offset, int limit, Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public OffsetLimitPageRequest(int offset, int limit) {
        this(offset, limit, Sort.unsorted());
    }

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetLimitPageRequest(offset + limit, limit, sort);
    }

    @Override
    public Pageable previousOrFirst() {
        if (hasPrevious()) {
            return new OffsetLimitPageRequest(offset - limit, limit, sort);
        } else {
            return new OffsetLimitPageRequest(0, limit, sort);
        }
    }

    @Override
    public Pageable first() {
        return new OffsetLimitPageRequest(0, limit, sort);
    }

    @Override
    public boolean hasPrevious() {
        return offset >= limit;
    }
}
