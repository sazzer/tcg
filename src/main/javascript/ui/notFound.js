/*eslint-disable no-unused-vars*/
import React from 'react';
import { translate } from 'react-i18next';
/*eslint-enable no-unused-vars*/

@translate()
/**
 * Component representing the Not Found page
 */
export default class NotFound extends React.Component {
    /**
     * Actually render the body
     * @returns {React.Component} the body
     */
    render() {
        const { t } = this.props;
        return <div>{ t('page.notFound')}</div>;
    }
}
