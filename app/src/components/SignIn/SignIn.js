import React from 'react';
import { useTranslation } from 'react-i18next';
import UserContext from '../../contexts/UserContext';
import { API } from '../../lib/API';
import { BASE_URL } from '../../lib/Constraints';

function SignIn() {
    const [user, setUser] = React.useState({login: "", password: ""});
    const { userDispatch } = React.useContext(UserContext);
    const { t } = useTranslation('auth');

    const changeHandler = (event) => {
        setUser({...user, [event.target.name]: event.target.value})
    };
    
    const okCallback = React.useCallback((user) => {
        let headers = new Headers();
        headers.set('Content-Type', 'application/json');
        let body = user;

        API.post({
            endpoint: BASE_URL, 
            url: 'signin', 
            headers: headers, 
            body: JSON.stringify(body)
        }).then((response) => {
            if (response.status === 200) {
                let token = response.headers.get('Authorization');
                userDispatch({type: 'signin', token: token});
            }
        });
    }, [userDispatch]);

    return (
        <div className="container">
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