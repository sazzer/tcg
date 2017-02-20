import React from 'react';
import { render } from 'react-dom';
import { I18nextProvider } from 'react-i18next';
import { Provider } from 'react-redux';
import i18n from './i18n';
import UI from './ui';
import store from './redux/store';

render(<I18nextProvider i18n={ i18n }>
    <Provider store={ store }>
        < UI />
    </Provider>
</I18nextProvider>, document.getElementById('app'));