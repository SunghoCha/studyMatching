export default {
    async login(context, payload) {
        const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: payload.email,
                password: payload.password,
            })
        });

        const responseData = await response.json();

        if (!response.ok) {
            console.log(responseData);
            const error = new Error(responseData.message || 'Failed to authenticate. Check your login data.');
            throw error;
        }

        console.log(responseData);
        context.commit('setUser', {
            token: responseData.idToken,
            userId: responseData.localId,
        });
    },

    async signup(context, payload) {
        const response = await fetch('http://localhost:8080/sign-up', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                nickname: payload.nickname,
                email: payload.email,
                password: payload.password,
            })
        });

        const responseData = await response.json();

        if (!response.ok) {
            console.log(responseData);
            const error = new Error(responseData.message || 'Failed to registeration. Check your sign-up data.');
            throw error;
        }

        // 사용자 정보를 Vuex 스토어에 커밋
        // 응답값은 백엔드 인증,인가를 토큰방식으로 바꿀 때 수정 예정. 일단 단순히 Long id값만 반환
        console.log(responseData);
        context.commit('setUser', {
            token: responseData,
            userId: responseData,
        });
    },
}
