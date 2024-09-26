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
            throw new Error(responseData.message || 'Failed to authenticate. Check your login data.');
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
            throw new Error(responseData.message || 'Failed to registeration. Check your sign-up data.');

        }

        // 사용자 정보를 Vuex 스토어에 커밋
        // 응답값은 백엔드 인증,인가를 토큰방식으로 바꿀 때 수정 예정. 일단 단순히 Long id값만 반환
        console.log(responseData);
        context.commit('setUser', {
            token: responseData,
            userId: responseData,
        });
    },

    async checkNickname(context, payload) {
        const response = await fetch('http://localhost:8080/check-nickname', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: payload.nickname,
            })
        });

        const responseData = await response.json();

        if (!response.ok) {
            console.log(responseData);
            const error = new Error(responseData.message || 'Failed to check nickname. Check your nickname.');
            throw error;
        }

        console.log(responseData);
        context.commit('setCheckNickname', {
            isValidNickname: responseData,
        });
    },

    async editProfile(context, payload) {
        const accountId = payload.accountId;
        const response = await fetch(`http://localhost:8080/profile/${accountId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                bio: payload.bio,
                url: payload.url,
                occupation: payload.occupation,
                location: payload.location
            })
        });

        const responseData = await response.json();

        if (!response.ok) {
            throw new Error(responseData.message || "프로필 변경에 실패하였습니다.")
        }

        //TODO AccountResponse 받아서 계정 정보 업데이트
    },

    async editPassword(context, payload) {
        const accountId = payload.accountId;
        const response = await fetch(`http://localhost:8080/password/${accountId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                newPassword: payload.newPassword,
                newPasswordConfirm: payload.newPasswordConfirm
            })
        });

        const responseData = await response.json();

        if (!response.ok) {
            throw new Error(responseData.message || '비밀번호 변경에 실패하였습니다.');
        }
    },

    async editTag(context, payload) {
        const accountId = payload.accountId;
        const response = await fetch(`http://localhost:8080/tag/${accountId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                tags : payload.tags
            })
        });

        const responseData = await response.json();

        if (!response.ok) {
            throw new Error(responseData.message || 'tag 등록에 실패하였습니다.')
        }
    },
}
