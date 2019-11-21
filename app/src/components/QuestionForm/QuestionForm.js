import React from 'react';

function QuestionForm({ initState, okTitle = "OK", cancelTitle = "Cancel", okCallback, cancelCallback }) {
    const [question, setQuestion] = React.useState(initState || {title: "", link: "", level: ""});
    
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
                    Question
                </label>

                <input 
                    type="text" 
                    className="form-control" 
                    id="question-title" 
                    placeholder="Enter question" 
                    value={question.title}
                    name="title"
                    onChange={changeHandler}
                />
            </div>

            <div className="form-group">
                <label htmlFor="question-link">
                    Link to answer
                </label>

                <input 
                    type="text" 
                    className="form-control" 
                    id="question-link" 
                    placeholder="Enter URL"
                    value={question.link}
                    name="link"
                    onChange={changeHandler}
                />
            </div>

            <div className="form-group">
                <label htmlFor="question-level">
                    Question level
                </label>

                <input 
                    type="text" 
                    className="form-control" 
                    id="question-level" 
                    placeholder="Enter question level"
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