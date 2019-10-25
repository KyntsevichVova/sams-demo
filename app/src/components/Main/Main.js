import React from 'react';
import FilterButton from '../FilterButton/FilterButton';
import './Main.css';

function Main() {
    const [filter, setFilter] = React.useState("all");
    const [posts, setPosts] = React.useState([]);
    
    React.useEffect(() => {
        fetch("http://localhost:8085/demo/api/v1/questions")
            .then((data) => {
                data.json().then((value) => {
                    setPosts(value);
                });
            });
    }, []);

    return (
        <main className="content d-flex flex-row pt-3">
            <div className="container w-25 d-flex justify-content-start flex-column mx-0">
                <FilterButton
                    filter="all"
                    currentFilter={filter}
                    onClick={setFilter}
                >
                    All
                </FilterButton>

                <FilterButton
                    filter="junior"
                    currentFilter={filter}
                    onClick={setFilter}
                >
                    Junior
                </FilterButton>

                <FilterButton
                    filter="middle"
                    currentFilter={filter}
                    onClick={setFilter}
                >
                    Middle
                </FilterButton>

                <FilterButton
                    filter="senior"
                    currentFilter={filter}
                    onClick={setFilter}
                >
                    Senior
                </FilterButton>
            </div>

            <div className="container d-flex justify-content-start mx-0">    
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">
                                Title
                            </th>
                            <th scope="col">
                                Link
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {posts.filter((value) => {
                            return filter === "all" || value.level === filter;
                        }).map((value) => {
                            return (
                                <tr>
                                    <td>
                                        {value.title}
                                    </td>
                                    <td>
                                        <a target="_blank" rel="noopener noreferrer" href={value.link}>
                                            {value.link}
                                        </a>
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
        </main>
    );
}

export default Main;