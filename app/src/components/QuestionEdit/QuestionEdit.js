import React from 'react';
import QuestionForm from '../QuestionForm/QuestionForm';
import { QUESTIONS_ENPOINT } from '../../Constraints';
import { Redirect } from 'react-router-dom';

function QuestionEdit({ match }) {
    const [shouldRedirect, setShouldRedirect] = React.useState(false);
    const [question, setQuestion] = React.useState(undefined);

    const okCallback = React.useCallback((question) => {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        
        fetch(`${QUESTIONS_ENPOINT}`, {
            method: 'PUT',
            body: JSON.stringify({...question, id: match.params.questionId}),
            headers: headers
        }).then((response) => {
            if (response.ok) {
                setShouldRedirect(true);
            }
        });

    }, []);

    React.useEffect(() => {
        
        fetch(`${QUESTIONS_ENPOINT}/${match.params.questionId}`)
            .then((response) => {
                if (response.ok) {
                    response.json().then((value) => {
                        setQuestion(value);
                    });
                }
            });

    }, [match.params.questionId]);

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