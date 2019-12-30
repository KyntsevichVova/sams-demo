import React from 'react';
import { Redirect } from 'react-router-dom';
import LocaleContext from '../../contexts/LocaleContext';
import UserContext from '../../contexts/UserContext';
import { API } from '../../lib/API';
import { ROLE } from '../../lib/Constraints';
import QuestionForm from './QuestionForm';

function QuestionEdit({ match }) {
    const [shouldRedirect, setShouldRedirect] = React.useState(false);
    const [question, setQuestion] = React.useState(undefined);
    const questionId = match.params.questionId || 0;
    const locale = React.useContext(LocaleContext);
    const { userState } = React.useContext(UserContext);

    const okCallback = React.useCallback((question) => {
        let headers = new Headers();
        headers.set('Content-Type', 'application/json');
        headers.set('Accept-Language', locale.full);

        let body = {
            link: question.link,
            level: question.level,
            titles: [
                {
                    locale: "RU",
                    title: question.titleRu
                },
                {
                    locale: "EN",
                    title: question.titleEn
                }
            ]
        };

        API.put({
            url: `${questionId}`,
            headers: headers,
            body: JSON.stringify(body)
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
        headers.set('Accept-Language', locale.full);
        
        API.get({
            url: `${questionId}`,
            headers: headers
        }).then((response) => {
            if (response.ok) {
                response.json().then((value) => {
                    let data = value.data[0];
                    let filterRu = data.titles.filter((value) => value.locale === "RU");
                    let filterEn = data.titles.filter((value) => value.locale === "EN");
                    let titleRu = (filterRu && filterRu[0] && filterRu[0].title) || "";
                    let titleEn = (filterEn && filterEn[0] && filterEn[0].title) || "";

                    let question = {
                        link: data.link,
                        level: data.level,
                        titleRu: titleRu,
                        titleEn: titleEn,
                        isOwner: data.isOwner
                    };
                    
                    setQuestion(question);
                });
            }
        });

    }, [questionId, locale.full]);

    return (
        <>
            {
                !(  
                    userState.loggedIn
                    && (
                        (userState.roles.includes(ROLE.USER) && (!question || (question && question.isOwner)))
                        || (userState.roles.includes(ROLE.TRANSLATOR))
                        || (userState.roles.includes(ROLE.MODERATOR))
                        || (userState.roles.includes(ROLE.ADMIN))
                    )
                )
                && <Redirect to="/" />
            }

            <div className="container">
                <QuestionForm
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