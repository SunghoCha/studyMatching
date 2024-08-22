import mutations from './mutations.js';
import actions from './actions.js';
import getters from './getters.js';

export default {
    namespaced: true,
    state() {
        return {
            lastFetch: null,
            items: [
                {
                    id: '1',
                    name: 'itemNameA',
                    price: 111
                },
                {
                    id: '2',
                    name: 'itemNameB',
                    price: 222
                },
            ]
        }
    },
    mutations,
    actions,
    getters
};