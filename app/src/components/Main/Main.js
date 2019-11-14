import React from 'react';
import './Main.css';
import QuestionTable from '../QuestionTable/QuestionTable';
import FilterAside from '../FilterAside/FilterAside';
import PaginationNav from '../PaginationNav/PaginationNav';
import Dropdown from '../Dropdown/Dropdown';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import { QUESTIONS_ENDPOINT, FILTERS, PAGE_SIZES } from '../../Constraints';
import PageDispatch from '../../contexts/PageDispatch';

function Main({ pageNumber }) {
    const [filter, setFilter] = React.useState(FILTERS[0].filter);
    const [pageData, setPageData] = React.useState({});
    const [pageSize, setPageSize] = React.useState(PAGE_SIZES[0]);
    const dispatchPageNumber = React.useContext(PageDispatch);
    
    React.useEffect(() => {
        fetch(`${QUESTIONS_ENDPOINT}?pageSize=${pageSize}&pageNum=${pageNumber}`)
            .then((data) => {
                data.json().then((pageData) => {
                    if (pageNumber * pageSize > pageData.total) {
                        dispatchPageNumber({ pageNumber: Math.floor((pageData.total - 1) / pageSize) });
                    }
                    setPageData(pageData);
                });
            });
    }, [pageNumber, pageSize, dispatchPageNumber]);

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
                dispatchPageNumber={dispatchPageNumber}
            />
        );
    }, [pageNumber, totalPages, dispatchPageNumber]);

    const nav = (
        <nav className="d-flex justify-content-between">
            <span className="font-weight-bold text-info border-top border-info">
                {`Showing ${firstOnPage} to ${lastOnPage} of ${totalElements} entries`}
            </span>

            {paginationNav}
        </nav>
    );

    const deleteCallback = React.useCallback((questionId) => {
        fetch(`${QUESTIONS_ENDPOINT}/${questionId}`, {
            method: 'DELETE'
        }).then((response) => {
            if (response.ok) {
                dispatchPageNumber({ pageNumber: pageNumber });
            }
        });
    }, [pageNumber, dispatchPageNumber]);

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
                        <QuestionTable 
                            questions={pageData.data || []} 
                            filter={filter} 
                            deleteCallback={deleteCallback}
                        />
                        
                        {nav}
                    </div>
                </div>

            </div>
        </main>
    );
}

export default Main;