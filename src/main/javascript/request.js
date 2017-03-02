import { selectAccessToken } from './authentication';

/**
 * Make a request to the server
 * @param {String} url the URL to request
 * @param {String} method The HTTP Method to use
 * @return {Promise} A promise for the result
 */
export default function request(url, method = 'GET') {
    /* eslint-disable global-require, no-undef */
    // This horrible trick is to get access to the Access Token in the store, which might be a circular reference
    const store = require('./redux/store').default;
    const accessToken = store ? selectAccessToken(store.getState()) : {};

    const headers = {};
    if (accessToken.authenticated) {
        headers['Authorization'] = `Bearer ${accessToken.accessToken}`;
    }

    return fetch(url, {
        method,
        headers
    }).then((response) => response.json().then((body) => ({
        status: response.status,
        headers: response.headers,
        body
    })));
}
