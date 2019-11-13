import React from 'react';
import './Main.css';
import QuestionTable from '../QuestionTable/QuestionTable';
import FilterAside from '../FilterAside/FilterAside';
import PaginationNav from '../PaginationNav/PaginationNav';
import Dropdown from '../Dropdown/Dropdown';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import { QUESTIONS_ENPOINT, FILTERS, PAGE_SIZES } from '../../Constraints';

function Main() {
    const [filter, setFilter] = React.useState(FILTERS[0].filter);
    const [page, setPage] = React.useState({});
    const [pageNumber, setPageNumber] = React.useState(0);
    const [pageSize, setPageSize] = React.useState(PAGE_SIZES[0]);
    
    React.useEffect(() => {
        fetch(`${QUESTIONS_ENPOINT}?pageSize=${pageSize}&pageNum=${pageNumber}`)
            .then((data) => {
                data.json().then((value) => {
                    if (value.number >= value.totalPages) {
                        setPageNumber(value.totalPages - 1);
                    }
                    setPage(value);
                });
            });
    }, [pageNumber, pageSize]);

    const filterAside = React.useMemo(() => {
        return (
            <FilterAside 
                filters={FILTERS}
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
        <main className="d-flex flex-row justify-content-start pt-3">
            <div className="container-fluid mx-5">

                <div className="row mb-3">
                    
                    <div className="col-2" />
                    
                    <div className="col-10 d-flex flex-row justify-content-between">
                        <div>
                            <span className="pb-2 font-weight-bold text-info border-bottom border-info">
                                Show
                                
                                <Dropdown title={`${pageSize}`}>
                                    {PAGE_SIZES.map((value) => {
                                        return (
                                            <a 
                                                className="dropdown-item" 
                                                href="#"
                                                onClick={() => {setPageSize(value)}}
                                            >
                                                {value}
                                            </a>
                                        );  
                                    })}
                                </Dropdown>
                                
                                entries
                            </span>

                            <Link to="/add">
                                <button className="btn btn-primary mx-5">
                                    <FontAwesomeIcon icon={["fas", "plus"]} />
                                </button>
                            </Link>
                        </div>

                        <div className="d-flex flex-row justify-content-end w-50">
                            <div className="input-group mb-0">
                                <div className="input-group-prepend">
                                    <span className="input-group-text">
                                        <FontAwesomeIcon icon={["fas", "search"]} />
                                    </span>
                                </div>
                                
                                <input type="text" className="form-control h-100" />
                            </div>
                        </div>

                    </div>  

                </div>

                <div className="row">
                    <div className="col-2">
                        {filterAside}
                    </div>

                    <div className="col-10 d-flex flex-column">
                        <QuestionTable questions={page.content || []} filter={filter}/>
                        
                        {paginationNav}
                    </div>
                </div>

            </div>
        </main>
    );
}

export default Main;