import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

function QuestionTable({posts, filter}) {
    return (
        <table className="table table-fixed">
            <thead>
                <tr>
                    <th scope="col" style={{width: "70%"}}>
                        Title
                    </th>
                    <th scope="col" style={{width: "15%"}}>
                        Level
                    </th>
                    <th scope="col" style={{width: "15%"}}>
                        
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
                                <div className="tooltip-wrapper">
                                    <div className="text-truncate">
                                        {value.title}
                                    </div>
                                    <span className="tooltip-text text-break">
                                        {value.title}
                                    </span>
                                </div>
                            </td>
                            <td>
                                {value.level[0].toUpperCase() + value.level.substr(1)}
                            </td>
                            <td>
                                <a className="text-primary mx-2" target="_blank" rel="noopener noreferrer" href={value.link}>
                                    <FontAwesomeIcon icon={["fas", "external-link-alt"]} />
                                </a>
                                <a className="text-info mx-2" href="#">
                                    <FontAwesomeIcon icon={["far", "edit"]} />
                                </a>
                                <a className="text-danger mx-2" href="#">
                                    <FontAwesomeIcon icon={["far", "trash-alt"]} />
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