export default {
    items(state) {
        return state.items;
    },
    hasItems(state) {
        return state.items && state.items.length > 0;
    },
    isItem(_, getters, _2, rootGetters) {
        const items = getters.items;
        const userId = rootGetters.userId;
        return items.some(item => item.id === userId);
    },
    shouldUpdate(state) {
        const lastFetch = state.lastFetch;
        if (!lastFetch) {
            return true;
        }
        const currentTimeStamp = new Date().getTime();
        return (currentTimeStamp - lastFetch) / 1000 > 60;
    }
}