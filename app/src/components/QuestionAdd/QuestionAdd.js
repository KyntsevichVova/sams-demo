import React from 'react';
import { Redirect } from 'react-router-dom';
import LocaleContext from '../../contexts/LocaleContext';
import UserContext from '../../contexts/UserContext';
import { API } from '../../lib/API';
import QuestionForm from './QuestionForm';

function QuestionAdd() {
    const [shouldRedirect, setShouldRedirect] = React.useState(false);
    const locale = React.useContext(LocaleContext);
    const { userState } = React.useContext(UserContext);

    const okCallback = React.useCallback((question) => {
        let headers = new Headers();
        headers.set('Content-Type', 'application/json');
        headers.set('Accept-Language', locale.full);

        let body = {
            ...question,
            locale: locale.short.toUpperCase()
        };

        API.post({ 
            headers: headers,
            body: JSON.stringify(body)
        }).then((response) => {
            if (response.ok) {
                setShouldRedirect(true);
            }
        });

    }, [locale.full, locale.short]);

    const cancelCallback = React.useCallback(() => {
        setShouldRedirect(true);
    }, []);

    return (
        <>
            {!userState.loggedIn && <Redirect to="/"/>}

            <div className="container">
                <QuestionForm
                    okCallback={okCallback}
                    cancelCallback={cancelCallback}
                />
            </div>
            
            {shouldRedirect && <Redirect to="/" />}
        </>
    );
}

export default QuestionAdd;