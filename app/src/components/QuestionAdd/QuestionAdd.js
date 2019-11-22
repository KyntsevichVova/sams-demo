import React from 'react';
import QuestionForm from '../QuestionForm/QuestionForm';
import { QUESTIONS_ENDPOINT } from '../../lib/Constraints';
import { Redirect } from 'react-router-dom';

function QuestionAdd() {
    const [shouldRedirect, setShouldRedirect] = React.useState(false);

    const okCallback = React.useCallback((question) => {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        
        fetch(`${QUESTIONS_ENDPOINT}`, {
            method: 'POST',
            body: JSON.stringify(question),
            headers: headers
        }).then((response) => {
            if (response.ok) {
                setShouldRedirect(true);
            }
        });

    }, []);

    const cancelCallback = React.useCallback(() => {
        setShouldRedirect(true);
    }, []);

    return (
        <>
            <div className="container">
                <QuestionForm
                    okTitle="Add"
                    okCallback={okCallback}
                    cancelCallback={cancelCallback}
                />
            </div>
            
            {shouldRedirect && <Redirect to="/" />}
        </>
    );
}

export default QuestionAdd;