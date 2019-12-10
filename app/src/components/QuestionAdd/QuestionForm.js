import React from 'react';
import { useTranslation } from 'react-i18next';
import { LEVELS } from '../../lib/Constraints';

function QuestionForm({ initState, okTitle = "OK", cancelTitle = "Cancel", okCallback, cancelCallback }) {
    const [question, setQuestion] = React.useState(initState || {title: "", link: "", level: LEVELS[0].filter});
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
            <div className="input-group my-3">
                <div className="btn-group btn-group-toggle" id="question-level">
                    {LEVELS.map((level) => {
                        const isActive = level.filter === question.level;
                        return (
                            <button 
                                type="button"
                                className={`btn${isActive ? " btn-primary" : ""}`}
                                name="level"
                                value={level.filter}
                                onClick={changeHandler}
                                key={level.filter}
                            >
                                { t(`level.${level.text}`) }
                            </button>   
                        );
                    })}
                </div>
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