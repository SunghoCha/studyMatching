export default {
    // registerItem(state, payload) {
    //     state.items.push(payload);
    // },
    setItems(state, payload) {
        state.items = payload;
        localStorage.setItem('items', JSON.stringify(state.items));
    },
    setFetchTimestamp(state) {
        state.lastFetch = new Date().getTime();
    }
}