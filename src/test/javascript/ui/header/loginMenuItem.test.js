/*eslint-disable no-unused-vars*/
import test from 'ava';
import React from 'react';
import { shallow } from 'enzyme';
import LoginMenuItem from './loginMenuItem';
/*eslint-enable no-unused-vars*/

test('Renders the correct structure', (t) => {
    const provider = {
        provider: 'google'
    };
    const handler = () => {
        // Hello
    };

    const wrapper = shallow(<LoginMenuItem provider={provider} onClick={handler} />);
    t.true(wrapper.hasClass('dropdown-item'));
});
