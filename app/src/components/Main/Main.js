import React from 'react';
import './Main.css';
import QuestionTable from '../QuestionTable/QuestionTable';
import FilterAside from '../FilterAside/FilterAside';
import PaginationNav from '../PaginationNav/PaginationNav';

const filters = [
    {filter: "all", text: "All"},
    {filter: "junior", text: "Junior"},
    {filter: "middle", text: "Middle"},
    {filter: "senior", text: "Senior"},
];

function Main() {
    const [filter, setFilter] = React.useState("all");
    const [page, setPage] = React.useState({});
    const [loading, setLoading] = React.useState(true);
    const [pageNumber, setPageNumber] = React.useState(0);
    
    React.useEffect(() => {
        setLoading(true);
        fetch(`http://localhost:8085/demo/api/v1/questions?limit=10&offset=${10 * pageNumber}`)
            .then((data) => {
                data.json().then((value) => {
                    setPage(value);
                    setLoading(false);
                });
            });
    }, [pageNumber]);

    return (
        <main className="content d-flex flex-row pt-3">
            {React.useMemo(() => {
                return (
                    <FilterAside 
                        filters={filters}
                        currentFilter={filter}
                        setFilterCallback={setFilter}
                    />
                );
            }, [filters, filter, setFilter])}

            <div className="container d-flex flex-column justify-content-start mx-0">
                <PaginationNav 
                    currentPage={pageNumber}
                    totalPages={page.totalPages || 1}
                    setCurrentPageCallback={setPageNumber}
                />
                {
                    loading 
                        ?   <div className="spinner-border text-primary" />
                        :   <QuestionTable posts={page.content} filter={filter}/>
                }  
            </div>
        </main>
    );
}

export default Main;