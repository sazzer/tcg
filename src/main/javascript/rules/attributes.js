import Immutable from 'immutable';
import { createAction } from 'redux-actions';
import { createSelector } from 'reselect';
import request from '../request';

/** Redux action to initialise the list of attributes that we can use */
export const initialiseAttributes = createAction('ATTRIBUTES:INITIALISE', () => ({
    promise: request('/api/attributes')
        .then((response) => response.body.entries)
}));

/** The reducers relevant to the Attributes */
export const reducers = {
    'ATTRIBUTES:INITIALISE_FULFILLED': (state, action) => {
        const attributes = {};
        action.payload.forEach((attribute) => {
            attributes[attribute.id] = attribute;
        });

        return state.set('attributes', Immutable.Map(attributes));
    }
};

/**
 * Internal selector to extract the attributes as-is from the state
 * @param {Immutable.Map} state The state to get the attributes from
 * @return {Immutable.Seq} The attributes, as a sequence
 */
const extractAttributes = (state) => state.getIn(['rules', 'attributes'], Immutable.Map()).valueSeq();

/**
 * Selector to get the attributes, as a list sorted by name
 */
export const selectAttributes = createSelector(extractAttributes,
    (attributes) => attributes.sortBy((attribute) => attribute.name)
);
