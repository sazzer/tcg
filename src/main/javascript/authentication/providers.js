import Immutable from 'immutable';
import { createAction } from 'redux-actions';

/** Redux action to initialise the list of authentication providers that we can use */
export const initialiseAuthenticationProviders = createAction('AUTHENTICATION_PROVIDERS:INITIALISE', () => ({
    promise: Promise.resolve([
        { provider: 'google' }
    ])
}));

/** The reducers relevant to the Authentication Providers */
export const reducers = {
    'AUTHENTICATION_PROVIDERS:INITIALISE_FULFILLED': (state, action) => state.set('providers',
        Immutable.List(action.payload.map((provider) => ({
                provider: provider.provider,
                url: `/api/authentication/${provider.provider}/start`
            }))
        )
    )
};

/**
 * Selector to get the Authentication Providers from the store
 * @param {Immutable.Map} state The Redux state to get the values from
 * @return {Array} The list of providers
 */
export const selectProviders = (state) => state.getIn(['authentication', 'providers'], Immutable.List()).toJS();
