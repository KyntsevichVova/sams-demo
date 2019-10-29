import React from 'react';

function PaginationNav({currentPage, totalPages, setCurrentPageCallback}) {
    return (
        <nav>
            <ul className="pagination">
                <li 
                    className={`page-item${currentPage > 0 ? "" : " disabled"}`}
                    onClick={currentPage > 0 ? () => {setCurrentPageCallback(currentPage - 1)} : null}
                >
                    <a className="page-link noselect" href="#">
                        &laquo;
                    </a>
                </li>
                {Array(totalPages).fill(1).map((_, index) => {
                    return (
                        <li 
                            className={`page-item${(index === currentPage) ? " active" : ""}`} 
                            onClick={() => {setCurrentPageCallback(index)}}
                        >
                            <a className="page-link noselect" href="#">
                                {index + 1}
                            </a>
                        </li>
                    );
                })}
                <li 
                    className={`page-item${currentPage + 1 < totalPages ? "" : " disabled"}`}
                    onClick={currentPage + 1 < totalPages ? () => {setCurrentPageCallback(currentPage + 1)} : null}
                >
                    <a className="page-link noselect" href="#">
                        &raquo;
                    </a>
                </li>
            </ul>
        </nav>  
    );
}

export default PaginationNav;