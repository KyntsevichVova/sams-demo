import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

function QuestionTable({ questions, deleteCallback }) {
    const { t } = useTranslation('table');

    return (
        <table className="table table-fixed">
            <thead>
                <tr>
                    <th scope="col" style={{width: "70%"}}>
                        { t('header.title') }
                    </th>
                    <th scope="col" style={{width: "15%"}}>
                        { t('header.level') }
                    </th>
                    <th scope="col" style={{width: "15%"}}>
                        
                    </th>
                </tr>
            </thead>
            <tbody>
                {questions.map((question) => {
                    return (
                        <tr key={question.id.toString()}>
                            <td>
                                <div className="tooltip-wrapper">
                                    <div className="text-truncate">
                                        {question.title}
                                    </div>

                                    <span className="tooltip-text text-break">
                                        {question.title}
                                    </span>
                                </div>
                            </td>

                            <td>
                                {question.level[0].toUpperCase() + question.level.substr(1)}
                            </td>

                            <td>
                                <a 
                                    className="text-primary mx-2" 
                                    target="_blank" 
                                    rel="noopener noreferrer" 
                                    href={question.link}
                                >
                                    <FontAwesomeIcon icon={["fas", "external-link-alt"]} />
                                </a>

                                <Link to={`/edit/${question.id}`}>
                                    <FontAwesomeIcon icon={["far", "edit"]} className="text-info mx-2" />
                                </Link>

                                <a 
                                    className="text-danger mx-2" 
                                    href="#"
                                    onClick={() => {deleteCallback(question.id)}}
                                >
                                    <FontAwesomeIcon icon={["far", "trash-alt"]} />
                                </a>

                                {!question.isFullyLocalized && 
                                    <FontAwesomeIcon icon={["fas", "exclamation-triangle"]} className="mx-2" color="red" />
                                }
                            </td>
                        </tr>
                    );
                })}
            </tbody>
        </table>
    );
}

export default QuestionTable;