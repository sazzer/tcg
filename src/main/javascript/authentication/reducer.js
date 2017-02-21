import Immutable from 'immutable';
import { handleActions } from 'redux-actions';
import { INITIALISE_AUTHENTICATION_PROVIDERS_RESOLVED } from './actions';

/** The actual reducers */
const reducers = {};
reducers[INITIALISE_AUTHENTICATION_PROVIDERS_RESOLVED] = (state, action) => Immutable.fromJS(action.payload);

/**
 * Reducers for the Authentication Providers
 */
export const reducer = handleActions(reducers, Immutable.List());
