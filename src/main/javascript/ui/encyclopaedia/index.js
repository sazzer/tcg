/*eslint-disable no-unused-vars*/
import React from 'react';
import { Route, Switch } from 'react-router-dom';
import NotFound from '../notFound';
import Races from './races';
/*eslint-enable no-unused-vars*/

/**
 * Component representing the encyclopaedia
 */
const Encyclopaedia = ({match}) => <div className="tcg-test-encyclopaedia">
    <Switch>
        <Route path={`${match.url}/races`} component={Races} />
        <Route component={NotFound} />
    </Switch>
</div>;

export default Encyclopaedia;
