import React from 'react';
import QuestionForm from '../QuestionForm/QuestionForm';
import { QUESTIONS_ENPOINT } from '../../Constraints';
import { Redirect } from 'react-router-dom';

function QuestionEdit({ match }) {
    const [shouldRedirect, setShouldRedirect] = React.useState(false);
    const [question, setQuestion] = React.useState(undefined);
    const questionId = match.params.questionId || 0;

    const okCallback = React.useCallback((question) => {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        
        fetch(`${QUESTIONS_ENPOINT}/${questionId}`, {
            method: 'PUT',
            body: JSON.stringify(question),
            headers: headers
        }).then((response) => {
            if (response.ok) {
                setShouldRedirect(true);
            }
        });

    }, [questionId]);

    React.useEffect(() => {
        
        fetch(`${QUESTIONS_ENPOINT}/${questionId}`)
            .then((response) => {
                if (response.ok) {
                    response.json().then((value) => {
                        setQuestion(value.data[0]);
                    });
                }
            });

    }, [questionId]);

    return (
        <>
            <div className="container">
                <QuestionForm
                    okTitle="Edit"
                    okCallback={okCallback}
                    initState={question}
                />
            </div>
            
            {shouldRedirect && <Redirect to="/" />}
        </>
    );

}

export default QuestionEdit;