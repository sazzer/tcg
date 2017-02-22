import Immutable from 'immutable';
import { handleActions } from 'redux-actions';
import { AUTHENTICATE, INITIALISE_AUTHENTICATION_PROVIDERS_RESOLVED } from './actions';

/** The actual reducers */
const reducers = {};
reducers[INITIALISE_AUTHENTICATION_PROVIDERS_RESOLVED] = (state, action) =>
    state.set('providers',
        Immutable.List(action.payload.map((provider) => ({
            provider: provider.provider,
            url: `/api/authentication/${provider.provider}/start`
        }))
    ));

reducers[AUTHENTICATE] = (state, action) =>
    state.set('accessToken', Immutable.Map({
        accessToken: action.payload.accessToken,
        expiry: action.payload.expiry
    }));

/**
 * Reducers for the Authentication Providers
 */
export const reducer = handleActions(reducers, Immutable.Map());
