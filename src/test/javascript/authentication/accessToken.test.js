import test from 'ava';
import Immutable from 'immutable';
import { testReducer } from '../redux/reducer.testHelper';
import * as testSubject from './accessToken';

test('AUTHENTICATED reducer produces the correct state', (t) => {
    const output = testReducer({
        action: {
            type: 'AUTHENTICATED',
            payload: {
                accessToken: 'thisIsMyAccessToken',
                expiry: '2017-02-24T13:07:00'
            }
        }
    });

    t.deepEqual(output.toJSON(), {
        authentication: {
            accessToken: {
                accessToken: 'thisIsMyAccessToken',
                expiry: '2017-02-24T13:07:00',
                authenticated: true
            }
        }
    });
});

test('LOGOUT reducer produces the correct state', (t) => {
    const output = testReducer({
        input: {
            authentication: {
                accessToken: {
                    accessToken: 'thisIsMyAccessToken',
                    expiry: '2017-02-24T13:07:00',
                    authenticated: true
                }
            }
        },
        action: { type: 'LOGOUT' }
    });

    t.deepEqual(output.toJSON(), {
        authentication: {}
    });
});

test('selectAccessToken selector works when access token is present', (t) => {
    const accessToken = testSubject.selectAccessToken(Immutable.fromJS({
        authentication: {
            accessToken: {
                accessToken: 'thisIsMyAccessToken',
                expiry: '2017-02-24T13:07:00',
                authenticated: true
            }
        }
    }));

    t.deepEqual(accessToken, {
        accessToken: 'thisIsMyAccessToken',
        expiry: '2017-02-24T13:07:00',
        authenticated: true
    });
});

test('selectAccessToken selector works when access token is not present', (t) => {
    const accessToken = testSubject.selectAccessToken(Immutable.Map());

    t.deepEqual(accessToken, { authenticated: false });
});

test('selectAuthenticated selector works when access token is present', (t) => {
    const authenticated = testSubject.selectAuthenticated(Immutable.fromJS({
        authentication: {
            accessToken: {
                accessToken: 'thisIsMyAccessToken',
                expiry: '2017-02-24T13:07:00',
                authenticated: true
            }
        }
    }));

    t.is(authenticated, true);
});

test('selectAuthenticated selector works when access token is not present', (t) => {
    const authenticated = testSubject.selectAuthenticated(Immutable.Map());

    t.is(authenticated, false);
});
