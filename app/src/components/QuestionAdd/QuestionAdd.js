import React from 'react';
import QuestionForm from './QuestionForm';
import { Redirect } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { API } from '../../lib/API';
import LocaleContext from '../../contexts/LocaleContext';

function QuestionAdd() {
    const [shouldRedirect, setShouldRedirect] = React.useState(false);
    const { t } = useTranslation('forms');
    const locale = React.useContext(LocaleContext);

    const okCallback = React.useCallback((question) => {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Accept-Language', locale.full);

        API.post({ 
            headers: headers,
            body: JSON.stringify(question)
        }).then((response) => {
            if (response.ok) {
                setShouldRedirect(true);
            }
        });

    }, [locale.full]);

    const cancelCallback = React.useCallback(() => {
        setShouldRedirect(true);
    }, []);

    return (
        <>
            <div className="container">
                <QuestionForm
                    okTitle={ t('add.ok') }
                    cancelTitle={ t('common.cancel') }
                    okCallback={okCallback}
                    cancelCallback={cancelCallback}
                />
            </div>
            
            {shouldRedirect && <Redirect to="/" />}
        </>
    );
}

export default QuestionAdd;