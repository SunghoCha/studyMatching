export default {
    setUser(state, payload) {
        state.token = payload.token;
        state.userId = payload.userId;
    },
    setCheckNickname(state, payload) {
        if (typeof  payload.isValidNickName === 'boolean') {
            state.isValidNickName = payload.isValidNickName;
        } else {
            throw new Error("Invalid payload: isValidNickname must be a boolean")
        }
    }

};