import React from 'react';
import { useTranslation } from 'react-i18next';
import { Redirect } from 'react-router-dom';
import UserContext from '../../contexts/UserContext';
import { API } from '../../lib/API';
import { BASE_URL } from '../../lib/Constraints';

function SignUp() {
    const [user, setUser] = React.useState({email: "", username: "", password: ""});
    const [redirect, setRedirect] = React.useState({should: false, to: "/"});

    const { userDispatch } = React.useContext(UserContext);
    const { t } = useTranslation('auth');

    const changeHandler = (event) => {
        setUser({...user, [event.target.name]: event.target.value})
    };

    const okCallback = React.useCallback((user) => {
        sessionStorage.removeItem(STORAGE_JWT);
        let headers = new Headers();
        headers.set('Content-Type', 'application/json');
        let body = user;

        API.post({
            endpoint: BASE_URL, 
            url: 'signup', 
            headers: headers, 
            body: JSON.stringify(body)
        }).then((response) => {
            if (response.status === 201) {
                let token = response.headers.get('Authorization');
                userDispatch({type: 'signin', token: token});
                setRedirect({should: true, to: "/"});
            }
        });
    }, [userDispatch]);

    return (
        <div className="container auth-form">
            <div className="d-flex flex-column">
                <div className="form-group">
                    <label htmlFor="email">
                        { t('label.email') }
                    </label>

                    <input 
                        type="email" 
                        className="form-control" 
                        id="email" 
                        placeholder={ t('placeholder.email') }
                        value={user.email}
                        name="email"
                        onChange={changeHandler}
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="username">
                        { t('label.username') }
                    </label>

                    <input 
                        type="text" 
                        className="form-control" 
                        id="username" 
                        placeholder={ t('placeholder.username') }
                        value={user.username}
                        name="username"
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
                        { t('button.signup') }
                    </button>
                </div>
            </div>
            {redirect.should && <Redirect to={redirect.to}/>}
        </div>
    );
}

export default SignUp;