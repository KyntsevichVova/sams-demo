import React from 'react';

function getFirstDisplayedPage(currentPage) {
    return Math.floor((currentPage - 2) / 3) * 3 + 2;
}

function createPage(pageTitle, isActive, isDisabled, onClick) {
    return (
        <li 
            className={`page-item${isActive ? " active" : ""}${isDisabled ? " disabled" : ""}`} 
            onClick={onClick}
        >
            <a className="page-link noselect" href="#">
                {pageTitle}
            </a>
        </li>
    );
}

function fillPages(startingIndex, totalPages, currentPage, setCurrentPageCallback) {
    return (
        Array(totalPages).fill(0).map((_, index) => {
            index += startingIndex;
            return createPage(index, index - 1 === currentPage, false, () => {setCurrentPageCallback(index - 1)});
        })
    );
}

function fillOverflownPages(totalPages, currentPage, setCurrentPageCallback) {
    let first = currentPage === 0 ? 2 : getFirstDisplayedPage(currentPage + 1);
    let last = Math.min(first + 2, totalPages);
    let pages = Array(0);
    pages.push(createPage(1, currentPage === 0, false, () => {setCurrentPageCallback(0)}));
    if (first > 2) {
        pages.push(createPage("...", false, false, () => {setCurrentPageCallback(first - 2)}));
    }
    if (last === totalPages || last + 1 === totalPages) {
        first = last - 3;
        fillPages(first, last - first + 1, currentPage, setCurrentPageCallback).forEach((value) => {pages.push(value)});
    } else {
        fillPages(first, last - first + 1, currentPage, setCurrentPageCallback).forEach((value) => {pages.push(value)});
        pages.push(createPage("...", false, false, () => {setCurrentPageCallback(last)}));
        pages.push(createPage(totalPages, totalPages - 1 === currentPage, false, () => {setCurrentPageCallback(totalPages - 1)}));
    }
    return pages;
}

function PaginationNav({
        currentPage, totalPages, setCurrentPageCallback,
        offset, numberOfElements, totalElements
}) {
    return (
        <nav className="d-flex justify-content-between">
            <span className="font-weight-bold text-info border-top border-info">
                {`Showing ${offset} to ${numberOfElements} of ${totalElements} entries`}
            </span>

            <ul className="pagination justify-content-end">
                {createPage("«", false, currentPage <= 0, currentPage > 0 ? () => {setCurrentPageCallback(currentPage - 1)} : null)}
                
                {   
                    totalPages <= 6 
                        ? fillPages(1, totalPages, currentPage, setCurrentPageCallback)
                        : fillOverflownPages(totalPages, currentPage, setCurrentPageCallback)
                }
                
                {createPage("»", false, currentPage + 1 >= totalPages, currentPage + 1 < totalPages ? () => {setCurrentPageCallback(currentPage + 1)} : null)}
            </ul>
        </nav>  
    );
}

export default PaginationNav;