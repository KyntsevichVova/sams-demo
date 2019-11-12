import React from 'react';
import QuestionForm from '../QuestionForm/QuestionForm';
import { API_URL } from '../../Constraints';
import { Redirect } from 'react-router-dom';

function QuestionAdd() {
    const [shouldRedirect, setShouldRedirect] = React.useState(false);
    const okCallback = React.useCallback((question) => {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        fetch(`${API_URL}/questions`, {
            method: 'POST',
            body: JSON.stringify(question),
            headers: headers
        }).then((response) => {
            if (response.ok) {
                setShouldRedirect(true);
            }
        });
    }, []);
    return (
        <>
            <div className="container">
                <QuestionForm
                    okTitle="Add"
                    okCallback={okCallback}
                >
                </QuestionForm>
            </div>
            {shouldRedirect && <Redirect to="/"></Redirect>}
        </>
    );
}

export default QuestionAdd;