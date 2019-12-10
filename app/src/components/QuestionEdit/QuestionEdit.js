import React from 'react';
import QuestionForm from './QuestionForm';
import { Redirect } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { API } from '../../lib/API';
import LocaleContext from '../../contexts/LocaleContext';

function QuestionEdit({ match }) {
    const [shouldRedirect, setShouldRedirect] = React.useState(false);
    const [question, setQuestion] = React.useState(undefined);
    const questionId = match.params.questionId || 0;
    const { t } = useTranslation('forms');
    const locale = React.useContext(LocaleContext);

    const okCallback = React.useCallback((question) => {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Accept-Language', locale.full);

        API.put({
            url: `${questionId}`,
            headers: headers,
            body: JSON.stringify(question)
        }).then((response) => {
            if (response.ok) {
                setShouldRedirect(true);
            }
        });

    }, [questionId, locale.full]);

    const cancelCallback = React.useCallback(() => {
        setShouldRedirect(true);
    }, []);

    React.useEffect(() => {

        let headers = new Headers();
        headers.append('Accept-Language', locale.full);
        
        API.get({
            url: `${questionId}`,
            headers: headers
        }).then((response) => {
            if (response.ok) {
                response.json().then((value) => {
                    setQuestion(value.data[0]);
                });
            }
        });

    }, [questionId, locale.full]);

    return (
        <>
            <div className="container">
                <QuestionForm
                    okTitle={ t('edit.ok') }
                    cancelTitle={ t('common.cancel') }
                    okCallback={okCallback}
                    cancelCallback={cancelCallback}
                    initState={question}
                />
            </div>
            
            {shouldRedirect && <Redirect to="/" />}
        </>
    );

}

export default QuestionEdit;