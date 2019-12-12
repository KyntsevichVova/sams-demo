import { QUESTIONS_ENDPOINT } from './Constraints';

function buildParams(params) {
    let paramsArray = [];

    const entries = Object.entries(params);

    for (const [key, value] of entries) {
        if (value !== null && value !== undefined) {
            paramsArray.push(`${key}=${encodeURI(value)}`);
        }
    }

    return paramsArray ? `?${paramsArray.join('&')}` : '';
}

export const METHOD = {
    GET: 'GET',
    POST: 'POST',
    PUT: 'PUT',
    DELETE: 'DELETE'
}

function doRequest(
    method, 
    { 
        url = "", 
        params = {}, 
        headers = null, 
        body = null 
    }
) {
    const init = {
        method: method
    };
    if (headers) {
        init.headers = headers;
    }
    if (body) {
        init.body = body;
    }
    return fetch(
        `${QUESTIONS_ENDPOINT}${url ? `/${url}` : ""}${buildParams(params)}`,
        init
    );

}

export class API {
    
    static get(args) {
        return doRequest(METHOD.GET, args);
    }

    static post(args) {
        return doRequest(METHOD.POST, args);
    }

    static put(args) {
        return doRequest(METHOD.PUT, args);
    }

    static delete(args) {
        return doRequest(METHOD.DELETE, args);
    }

}
