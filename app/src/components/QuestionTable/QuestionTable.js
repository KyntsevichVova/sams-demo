import React from 'react';

function QuestionTable({posts, filter}) {
    return (
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
                        <tr key={value.id.toString()}>
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
    );
}

export default QuestionTable;