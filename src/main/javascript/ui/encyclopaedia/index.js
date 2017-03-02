/*eslint-disable no-unused-vars*/
import React from 'react';
import { Route, Switch } from 'react-router-dom';
import NotFound from '../notFound';
import Races from './races';
import Classes from './classes';
import Attributes from './attributes';
import Skills from './skills';
import Abilities from './abilities';
/*eslint-enable no-unused-vars*/

/**
 * Component representing the encyclopaedia
 */
const Encyclopaedia = ({match}) => <div className="tcg-test-encyclopaedia">
    <Switch>
        <Route path={`${match.url}/races`} component={Races} />
        <Route path={`${match.url}/classes`} component={Classes} />
        <Route path={`${match.url}/attributes`} component={Attributes} />
        <Route path={`${match.url}/skills`} component={Skills} />
        <Route path={`${match.url}/abilities`} component={Abilities} />
        <Route component={NotFound} />
    </Switch>
</div>;

export default Encyclopaedia;
