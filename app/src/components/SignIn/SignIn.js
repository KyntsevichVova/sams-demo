import React from 'react';
import { useTranslation } from 'react-i18next';

function SignIn() {
    const [user, setUser] = React.useState({login: "", password: ""});
    const { t } = useTranslation('auth');

    const changeHandler = (event) => {
        setUser({...user, [event.target.name]: event.target.value})
    };
    const okCallback = React.useCallback((user) => {

    }, []);

    return (
        <div className="container">
            <div className="d-flex flex-column">
                <div className="form-group">
                    <label htmlFor="login">
                        { t('label.login') }
                    </label>

                    <input 
                        type="text" 
                        className="form-control" 
                        id="login" 
                        placeholder={ t('placeholder.login') }
                        value={user.login}
                        name="login"
                        onChange={changeHandler}
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="password">
                        { t('label.password') }
                    </label>

                    <input 
                        type="password" 
                        className="form-control" 
                        id="password" 
                        placeholder={ t('placeholder.password') } 
                        value={user.password}
                        name="password"
                        onChange={changeHandler}
                    />
                </div>
                
                <div className="d-flex flex-row justify-content-end">
                    <button 
                        className="btn btn-primary" 
                        onClick={() => {
                            okCallback(user);
                        }}
                    >
                        { t('button.signin') }
                    </button>
                </div>
            </div>
        </div>
    );
}

export default SignIn;