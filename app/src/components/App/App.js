import React from 'react';
import './App.css';
import FilterButton from '../FilterButton/FilterButton';

function App() {
    const [filter, setFilter] = React.useState("all");
    const posts = [
        {title: "A", link: "linkA", level: "junior"},
        {title: "B", link: "linkB", level: "middle"},
        {title: "C", link: "linkC", level: "senior"}
    ];
    return (
        <div className="App">
            <header>
                <h1>
                    Java Interview Notes
                </h1>
            </header>
            <main className="App-main">
                <div className="App-filters">
                    <FilterButton
                        filter="all"
                        onClick={setFilter}
                        currentFilter={filter}
                    >
                        All
                    </FilterButton>
                    <FilterButton
                        filter="junior"
                        onClick={setFilter}
                        currentFilter={filter}
                    >
                        Junior
                    </FilterButton>
                    <FilterButton
                        filter="middle"
                        onClick={setFilter}
                        currentFilter={filter}
                    >
                        Middle
                    </FilterButton>
                    <FilterButton
                        filter="senior"
                        onClick={setFilter}
                        currentFilter={filter}
                    >
                        Senior
                    </FilterButton>
                </div>
                <table className="App-posts">
                    <tr>
                        <th>
                            Title
                        </th>
                        <th>
                            Link
                        </th>
                    </tr>
                    {posts.filter((value) => {
                        return filter === "all" || value.level === filter;
                    }).map((value) => {
                        return (
                            <tr>
                                <td>
                                    {value.title}
                                </td>
                                <td>
                                    <a href={value.link}>
                                        {value.link}
                                    </a>
                                </td>
                            </tr>
                        );
                    })}
                </table>
            </main>
        </div>
    );
}

export default App;
