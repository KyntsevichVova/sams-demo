import React from 'react';
import './Main.css';
import QuestionTable from '../QuestionTable/QuestionTable';
import FilterAside from '../FilterAside/FilterAside';
import PaginationNav from '../PaginationNav/PaginationNav';
import Dropdown from '../Dropdown/Dropdown';

const filters = [
    {filter: "all", text: "All"},
    {filter: "junior", text: "Junior"},
    {filter: "middle", text: "Middle"},
    {filter: "senior", text: "Senior"},
];

const limits = [5, 10, 25, 50];

const API_URL = "http://localhost:8085/demo/api/v1";

function Main() {
    const [filter, setFilter] = React.useState("all");
    const [page, setPage] = React.useState({});
    const [pageNumber, setPageNumber] = React.useState(0);
    const [limit, setLimit] = React.useState(limits[0]);
    
    React.useEffect(() => {
        fetch(`${API_URL}/questions?pageSize=${limit}&pageNum=${pageNumber}`)
            .then((data) => {
                data.json().then((value) => {
                    if (value.number >= value.totalPages) {
                        setPageNumber(value.totalPages - 1);
                    }
                    setPage(value);
                });
            });
    }, [pageNumber, limit]);

    const filterAside = React.useMemo(() => {
        return (
            <FilterAside 
                filters={filters}
                currentFilter={filter}
                setFilterCallback={setFilter}
            />
        );
    }, [filter, setFilter]);

    const totalPages = page.totalPages || 1;
    const offset = ((page.pageable || {}).offset || 0) + 1;
    const numberOfElements = (page.numberOfElements || 0) + offset - 1;
    const totalElements = page.totalElements || 0;

    const paginationNav = React.useMemo(() => {
        return (
            <PaginationNav 
                currentPage={pageNumber}
                totalPages={totalPages}
                setCurrentPageCallback={setPageNumber}
                offset={offset}
                numberOfElements={numberOfElements}
                totalElements={totalElements}
            />
        );
    }, [pageNumber, totalPages, setPageNumber, offset, numberOfElements, totalElements]);

    return (
        <main className="content d-flex flex-row justify-content-start pt-3">
            <div className="container-fluid mx-5">
                <div className="row mb-3">
                    <div className="col-2"></div>
                    <div className="col-10">
                        <span className="pb-2 font-weight-bold text-info border-bottom border-info">
                            Show
                            <Dropdown title={`${limit}`}>
                                {limits.map((value) => {
                                    return (
                                        <a 
                                            className="dropdown-item" 
                                            href="#"
                                            onClick={() => {setLimit(value)}}
                                        >
                                            {value}
                                        </a>
                                    );  
                                })}
                            </Dropdown>
                            entries
                        </span>
                    </div>                    
                </div>
                <div className="row">
                    <div className="col-2">
                        {filterAside}
                    </div>
                    <div className="col-10 d-flex flex-column">
                        <QuestionTable posts={page.content || []} filter={filter}/>
                        {paginationNav}
                    </div>
                </div>
            </div>
        </main>
    );
}

export default Main;