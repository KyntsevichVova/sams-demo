import React from 'react';
import { useTranslation } from 'react-i18next';

function QuestionForm({ initState, okTitle = "OK", cancelTitle = "Cancel", okCallback, cancelCallback }) {
    const [question, setQuestion] = React.useState(initState || {title: "", link: "", level: ""});
    const { t } = useTranslation('forms');
    
    const changeHandler = (event) => {
        setQuestion({...question, [event.target.name]: event.target.value})
    };

    React.useEffect(() => {
        if (initState) {
            setQuestion(initState);
        }
    }, [initState]);

    return (
        <div className="d-flex flex-column">
            <div className="form-group">
                <label htmlFor="question-title">
                    { t('label.question.title') }
                </label>

                <input 
                    type="text" 
                    className="form-control" 
                    id="question-title" 
                    placeholder={ t('placeholder.question.title') } 
                    value={question.title}
                    name="title"
                    onChange={changeHandler}
                />
            </div>

            <div className="form-group">
                <label htmlFor="question-link">
                    { t('label.question.answerLink') }
                </label>

                <input 
                    type="text" 
                    className="form-control" 
                    id="question-link" 
                    placeholder={ t('placeholder.question.answerLink') }
                    value={question.link}
                    name="link"
                    onChange={changeHandler}
                />
            </div>

            <div className="form-group">
                <label htmlFor="question-level">
                    { t('label.question.level') }
                </label>

                <input 
                    type="text" 
                    className="form-control" 
                    id="question-level" 
                    placeholder={ t('placeholder.question.level') }
                    value={question.level}
                    name="level"
                    onChange={changeHandler}
                />
            </div>
            
            <div className="d-flex flex-row justify-content-end">
                <button 
                    className="btn btn-primary" 
                    onClick={() => {
                        okCallback(question);
                    }}
                >
                    {okTitle}
                </button>

                <button 
                    className="btn btn-secondary mx-3" 
                    onClick={cancelCallback}
                >
                    {cancelTitle}
                </button>
            </div>
        </div>
    );
}

export default QuestionForm;