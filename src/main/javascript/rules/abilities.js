import Immutable from 'immutable';
import { createAction } from 'redux-actions';
import { createSelector } from 'reselect';
import request from '../request';

/** Redux action to initialise the list of abilities that we can use */
export const initialiseAbilities = createAction('ABILITIES:INITIALISE', () => ({
    promise: request('/api/abilities')
        .then((response) => response.body.entries)
}));

/** The reducers relevant to the Abilities */
export const reducers = {
    'ABILITIES:INITIALISE_FULFILLED': (state, action) => {
        const abilities = {};
        action.payload.forEach((ability) => {
            abilities[ability.id] = ability;
        });

        return state.set('abilities', Immutable.Map(abilities));
    }
};

/**
 * Internal selector to extract the abilities as-is from the state
 * @param {Immutable.Map} state The state to get the abilities from
 * @return {Immutable.Seq} The abilities, as a sequence
 */
const extractAbilities = (state) => state.getIn(['rules', 'abilities'], Immutable.Map()).valueSeq();

/**
 * Selector to get the abilities, as a list sorted by name
 */
export const selectAbilities = createSelector(extractAbilities,
    (abilities) => abilities.sortBy((ability) => ability.name)
);
