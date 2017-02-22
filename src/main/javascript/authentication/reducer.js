import Immutable from 'immutable';
import { handleActions } from 'redux-actions';
import { reducers as providerReducers } from './providers';
import { reducers as accessTokenReducers } from './accessToken';

/**
 * Reducers for the Authentication Providers
 */
export const reducer = {
    authentication: handleActions({
        ...providerReducers,
        ...accessTokenReducers
    }, Immutable.Map())
};
