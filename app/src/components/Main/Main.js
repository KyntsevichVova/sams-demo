import React from 'react';
import FilterButton from '../FilterButton/FilterButton';

function Main() {
    const [filter, setFilter] = React.useState("all");
    const posts = [
        {title: "A", link: "linkA", level: "junior"},
        {title: "B", link: "linkB", level: "middle"},
        {title: "C", link: "linkC", level: "senior"}
    ];
    return (
        <main className="d-flex flex-row pt-3">
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