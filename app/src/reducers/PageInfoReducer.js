import React from 'react';
import { PAGE_SIZES, FILTERS } from '../lib/Constraints';

const pageInfoInitialState = {
    pageNumber: 0, 
    pageSize: PAGE_SIZES[0],
    filter: FILTERS[0].filter
};

function pageInfoReducer(state, action) {
    switch (action.type) {
        case 'pageNumber':
            return { ...state, pageNumber: action.pageNumber };
        case 'pageSize':
            return { ...state, pageSize: action.pageSize };
        case 'filter':
            return { ...state, filter: action.filter };
        default:
            throw new Error();
    }
}

export function usePageInfoReducer() {
    return React.useReducer(pageInfoReducer, pageInfoInitialState);
}