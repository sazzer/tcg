import Immutable from 'immutable';
import { handleActions } from 'redux-actions';
import { INITIALISE_AUTHENTICATION_PROVIDERS_RESOLVED } from './actions';

/** The actual reducers */
const reducers = {};
reducers[INITIALISE_AUTHENTICATION_PROVIDERS_RESOLVED] = (state, action) => Immutable.fromJS(
    action.payload.map((provider) => ({
        provider: provider.provider,
        url: `/api/authentication/${provider.provider}/start`
    })
    )
);

/**
 * Reducers for the Authentication Providers
 */
export const reducer = handleActions(reducers, Immutable.List());
