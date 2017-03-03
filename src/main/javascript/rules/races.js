import Immutable from 'immutable';
import { createAction } from 'redux-actions';
import { createSelector } from 'reselect';
import request from '../request';

/** Redux action to initialise the list of races that we can use */
export const initialiseRaces = createAction('RACES:INITIALISE', () => ({
    promise: request('/api/races')
        .then((response) => response.body.entries)
}));

/** The reducers relevant to the Races */
export const reducers = {
    'RACES:INITIALISE_FULFILLED': (state, action) => {
        const races = {};
        action.payload.forEach((race) => {
            races[race.id] = race;
        });

        return state.set('races', Immutable.Map(races));
    }
};

/**
 * Internal selector to extract the races as-is from the state
 * @param {Immutable.Map} state The state to get the races from
 * @return {Immutable.Seq} The races, as a sequence
 */
const extractRaces = (state) => state.getIn(['rules', 'races'], Immutable.Map()).valueSeq();

/**
 * Selector to get the races, as a list sorted by name
 */
export const selectRaces = createSelector(extractRaces,
    (races) => races.sortBy((race) => race.name)
);
