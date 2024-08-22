import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';

import itemModule from './modules/items/index.js';
import authModule from './modules/auth/index.js';

const store = createStore({
    modules: {
        items: itemModule,
        auth: authModule,
    },
    plugins: [createPersistedState({
        paths: ['items']
    })]
});

export default store;