import React from 'react';
import QuestionForm from '../QuestionForm/QuestionForm';
import { QUESTIONS_ENDPOINT } from '../../lib/Constraints';
import { Redirect } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

function QuestionEdit({ match }) {
    const [shouldRedirect, setShouldRedirect] = React.useState(false);
    const [question, setQuestion] = React.useState(undefined);
    const questionId = match.params.questionId || 0;
    const { t } = useTranslation('forms');

    const okCallback = React.useCallback((question) => {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        
        fetch(`${QUESTIONS_ENDPOINT}/${questionId}`, {
            method: 'PUT',
            body: JSON.stringify(question),
            headers: headers
        }).then((response) => {
            if (response.ok) {
                setShouldRedirect(true);
            }
        });

    }, [questionId]);

    const cancelCallback = React.useCallback(() => {
        setShouldRedirect(true);
    }, []);

    React.useEffect(() => {
        
        fetch(`${QUESTIONS_ENDPOINT}/${questionId}`)
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