import React from 'react';
import './Main.css';
import QuestionTable from '../QuestionTable/QuestionTable';
import FilterAside from '../FilterAside/FilterAside';
import PaginationNav from '../PaginationNav/PaginationNav';
import Dropdown from '../Dropdown/Dropdown';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import { QUESTIONS_ENDPOINT, FILTERS, PAGE_SIZES } from '../../lib/Constraints';
import PageDispatch from '../../contexts/PageDispatch';
import { Trans } from 'react-i18next';

function usePageNumber() {
    const dispatchPageParams = React.useContext(PageDispatch);

    return React.useCallback((pageNumber) => {
        dispatchPageParams({
            type: 'pageNumber',
            pageNumber: pageNumber
        });
    }, [dispatchPageParams]); 
}

function usePageSize() {
    const dispatchPageParams = React.useContext(PageDispatch);

    return React.useCallback((pageSize) => {
        dispatchPageParams({
            type: 'pageSize',
            pageSize: pageSize
        });
    }, [dispatchPageParams]);
}

function useFilter() {
    const dispatchPageParams = React.useContext(PageDispatch);

    return React.useCallback((filter) => {
        dispatchPageParams({
            type: 'filter',
            filter: filter
        });
    }, [dispatchPageParams]);
}

function buildRequest(params) {
    let paramsArray = [];

    const entries = Object.entries(params);

    for (const [key, value] of entries) {
        if (value !== null && value !== undefined) {
            paramsArray.push(`${key}=${encodeURI(value)}`);
        }
    }

    return paramsArray ? `?${paramsArray.join('&')}` : '';
}

function Main({ pageNumber, pageSize, filter }) {
    const [pageData, setPageData] = React.useState({});
    const [forceUpdate, setForceUpdate] = React.useState(false);

    const setPageNumberCallback = usePageNumber();
    const setPageSizeCallback = usePageSize();
    const setFilterCallback = useFilter();
    
    React.useEffect(() => {
        let params = {
            pageSize: pageSize,
            pageNum: pageNumber,
            level: filter === 'all' ? null : filter
        };

        fetch(`${QUESTIONS_ENDPOINT}${buildRequest(params)}`)
            .then((data) => {
                data.json().then((pageData) => {
                    if (pageNumber * pageSize > pageData.total) {
                        setPageNumberCallback(Math.floor((pageData.total - 1) / pageSize));
                    }
                    setPageData(pageData);
                });
            });
    }, [pageNumber, pageSize, filter, setPageNumberCallback, forceUpdate]);

    const filterAside = React.useMemo(() => {
        return (
            <FilterAside 
                filters={FILTERS}
                currentFilter={filter}
                setFilterCallback={setFilterCallback}
            />
        );
    }, [filter, setFilterCallback]);

    const totalElements = pageData.total || 0;
    const totalPages = Math.floor((totalElements + pageSize - 1) / pageSize);
    const firstOnPage = pageNumber * pageSize + 1;
    const lastOnPage = Math.min(totalElements, firstOnPage + pageSize);

    const paginationNav = React.useMemo(() => {
        return (
            <PaginationNav
                currentPage={pageNumber}
                totalPages={totalPages}
                setCurrentPageCallback={setPageNumberCallback}
            />
        );
    }, [pageNumber, totalPages, setPageNumberCallback]);

    const nav = (
        <nav className="d-flex justify-content-between">
            <span className="font-weight-bold text-info border-top border-info">
                <Trans i18nKey="entries.showing" ns="table">
                    Showing {{firstOnPage}} to {{lastOnPage}} of {{totalElements}} entries
                </Trans>
                {/*`Showing ${firstOnPage} to ${lastOnPage} of ${totalElements} entries`*/}
            </span>

            {paginationNav}
        </nav>
    );

    const deleteCallback = React.useCallback((questionId) => {
        fetch(`${QUESTIONS_ENDPOINT}/${questionId}`, {
            method: 'DELETE'
        }).then((response) => {
            if (response.ok) {
                setForceUpdate(!forceUpdate);
            }
        });
    }, [forceUpdate]);

    return (
        <main className="d-flex flex-row justify-content-start pt-3">
            <div className="container-fluid mx-5">

                <div className="row mb-3">
                    
                    <div className="col-2" />
                    
                    <div className="col-10 d-flex flex-row justify-content-between">
                        <div>
                            <span className="pb-2 font-weight-bold text-info border-bottom border-info">
                                <Trans i18nKey="entries.limit" ns="table">
                                    Show
                                    
                                    <Dropdown title={`${pageSize}`}>
                                        {PAGE_SIZES.map((value) => {
                                            return (
                                                <button 
                                                className="dropdown-item" 
                                                onClick={() => {setPageSizeCallback(value)}}
                                                key={value.toString()}
                                                >
                                                    {value}
                                                </button>
                                            );  
                                        })}
                                    </Dropdown>
                                    
                                    entries
                                </Trans>
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