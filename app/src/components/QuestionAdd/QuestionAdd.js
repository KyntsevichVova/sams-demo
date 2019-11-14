import React from 'react';
import QuestionForm from '../QuestionForm/QuestionForm';
import { QUESTIONS_ENDPOINT } from '../../Constraints';
import { Redirect, useHistory } from 'react-router-dom';

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

    const history = useHistory();
    const cancelCallback = React.useCallback(() => {
        history.goBack();
    }, [history]);

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