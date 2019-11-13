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
    const [pageData, setPageData] = React.useState({});
    const [pageNumber, setPageNumber] = React.useState(0);
    const [pageSize, setPageSize] = React.useState(PAGE_SIZES[0]);
    
    React.useEffect(() => {
        fetch(`${QUESTIONS_ENPOINT}?pageSize=${pageSize}&pageNum=${pageNumber}`)
            .then((data) => {
                data.json().then((pageData) => {
                    if (pageNumber * pageSize > pageData.total) {
                        setPageNumber(Math.floor((pageData.total - 1) / pageSize));
                    }
                    setPageData(pageData);
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
    }, [filter]);

    const totalElements = pageData.total || 0;
    const totalPages = Math.floor((totalElements + pageSize - 1) / pageSize);
    const firstOnPage = pageNumber * pageSize + 1;
    const lastOnPage = Math.min(totalElements, firstOnPage + pageSize);

    const paginationNav = React.useMemo(() => {
        return (
            <PaginationNav
                currentPage={pageNumber}
                totalPages={totalPages}
                setCurrentPageCallback={setPageNumber}
            />
        );
    }, [pageNumber, totalPages]);

    const nav = (
        <nav className="d-flex justify-content-between">
            <span className="font-weight-bold text-info border-top border-info">
                {`Showing ${firstOnPage} to ${lastOnPage} of ${totalElements} entries`}
            </span>

            {paginationNav}
        </nav>
    );

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
                                            <button 
                                                className="dropdown-item" 
                                                onClick={() => {setPageSize(value)}}
                                                key={value.toString()}
                                            >
                                                {value}
                                            </button>
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
                        <QuestionTable questions={pageData.data || []} filter={filter} />
                        
                        {nav}
                    </div>
                </div>

            </div>
        </main>
    );
}

export default Main;