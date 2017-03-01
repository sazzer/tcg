import test from 'ava';
import Immutable from 'immutable';
import { testReducer } from '../redux/reducer.testHelper';
import * as testSubject from './providers';

test('AUTHENTICATION_PROVIDERS:INITIALISE_FULFILLED reducer produces the correct state', (t) => {
    const output = testReducer({
        action: {
            type: 'AUTHENTICATION_PROVIDERS:INITIALISE_FULFILLED',
            payload: [{
                provider: 'google'
            }]
        }
    });

    t.deepEqual(output.toJSON().authentication, {
        providers: [
            {
                provider: 'google',
                url: '/api/authentication/google/start'
            }
        ]
    });
});

test('initialiseAuthenticationProviders action creator', async (t) => {
    const action = testSubject.initialiseAuthenticationProviders();
    t.true(action.type === 'AUTHENTICATION_PROVIDERS:INITIALISE');

    const payload = await action.payload.promise;
    t.deepEqual(payload, [{ provider: 'google' }]);
});

test('selectProviders selector works when providers are present', (t) => {
    const providers = testSubject.selectProviders(Immutable.fromJS({
        authentication: {
            providers: [
                {
                    provider: 'google',
                    url: '/api/authentication/google/start'
                }
            ]
        }
    }));

    t.deepEqual(providers, [
        {
            provider: 'google',
            url: '/api/authentication/google/start'
        }
    ]);
});

test('selectProviders selector works when providers are not present', (t) => {
    const providers = testSubject.selectProviders(Immutable.Map());

    t.deepEqual(providers, []);
});
