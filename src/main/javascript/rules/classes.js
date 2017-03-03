import Immutable from 'immutable';
import { createAction } from 'redux-actions';
import { createSelector } from 'reselect';
import request from '../request';

/** Redux action to initialise the list of classes that we can use */
export const initialiseClasses = createAction('CLASSES:INITIALISE', () => ({
    promise: request('/api/classes')
        .then((response) => response.body.entries)
}));

/** The reducers relevant to the Classes */
export const reducers = {
    'CLASSES:INITIALISE_FULFILLED': (state, action) => {
        const classes = {};
        action.payload.forEach((cls) => {
            classes[cls.id] = cls;
        });

        return state.set('classes', Immutable.Map(classes));
    }
};

/**
 * Internal selector to extract the classes as-is from the state
 * @param {Immutable.Map} state The state to get the classes from
 * @return {Immutable.Seq} The classes, as a sequence
 */
const extractClasses = (state) => state.getIn(['rules', 'classes'], Immutable.Map()).valueSeq();

/**
 * Selector to get the classes, as a list sorted by name
 */
export const selectClasses = createSelector(extractClasses,
    (classes) => classes.sortBy((cls) => cls.name)
);
