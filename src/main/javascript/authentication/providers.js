import Immutable from 'immutable';
import { createAction } from 'redux-actions';

/** Action key for initialising the list of authentication providers */
const INITIALISE_AUTHENTICATION_PROVIDERS = 'AUTHENTICATION_PROVIDERS:INITIALISE';

/** Action key for resolving the list of authentication providers */
const INITIALISE_AUTHENTICATION_PROVIDERS_RESOLVED = 'AUTHENTICATION_PROVIDERS:INITIALISE_RESOLVED';

/** Redux action to initialise the list of authentication providers that we can use */
export const initialiseAuthenticationProviders = createAction(INITIALISE_AUTHENTICATION_PROVIDERS, () => ({
    promise: Promise.resolve([
        { provider: 'google' }
    ])
}));

/** The reducers relevant to the Authentication Providers */
export const reducers = {};
reducers[INITIALISE_AUTHENTICATION_PROVIDERS_RESOLVED] = (state, action) =>
    state.set('providers',
        Immutable.List(action.payload.map((provider) => ({
                provider: provider.provider,
                url: `/api/authentication/${provider.provider}/start`
            }))
        ));
