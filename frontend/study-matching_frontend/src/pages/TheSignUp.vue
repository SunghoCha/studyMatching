<template>
  <div class="container">
    <main>
      <div class="py-5 text-center">
        <img class="d-block mx-auto mb-4" src="/docs/5.3/assets/brand/bootstrap-logo.svg" alt="" width="72" height="57">
        <h2>스터디카페 회원이 되어주세요.</h2>
        <p class="lead"></p>
      </div>

      <div class="row g-5 justify-content-center">
        <div class="col-md-8 col-lg-8">
          <h4 class="mb-3">회원가입</h4>
          <form @submit.prevent="submitForm">
            <div class="row g-3">
              <div class="col-sm-8">
                <div class="form-floating">
                  <input type="text" class="form-control form-control-sm" id="nickname" placeholder="Nickname" v-model.trim="nickname.val" @blur="clearValidity('nickname')">
                  <label for="nickname">Nickname</label>
                  <p v-if="!nickname.isValid">유효한 닉네임을 입력해주세요.</p>
                </div>
              </div>
              <div class="col-sm-4">
                <button type="button" class="btn btn-primary btn-custom" @click="checkNickname">중복확인</button>
              </div>

              <div class="col-sm-8">
                <div class="form-floating">
                  <input type="email" class="form-control form-control-sm" id="email" placeholder="name@example.com" v-model.trim="email.val" @blur="clearValidity('email')">
                  <label for="email">Email address</label>
                  <p v-if="!email.isValid">유효한 이메일을 입력해주세요.</p>
                </div>
              </div>
              <div class="col-sm-4">
                <button type="button" class="btn btn-primary btn-custom">인증하기</button>
              </div>

              <div class="col-sm-8">
                <div class="form-floating">
                  <input type="password" class="form-control form-control-sm" id="password" placeholder="Password" v-model.trim="password.val" @blur="clearValidity('password')">
                  <label for="password">Password</label>
                  <p v-if="!password.isValid">유효한 비밀번호를 입력해주세요.</p>
                </div>
              </div>
              <div class="col-sm-4"></div>
            </div>

            <hr class="my-4">
            <button class="w-100 btn btn-primary btn-lg" type="submit">가입하기</button>
          </form>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: "TheSignUp",
  data() {
    return {
      nickname: {
        val:'',
        isValid: true
      },
      email: {
        val:'',
        isValid: true
      },
      password: {
        val:'',
        isValid: true
      },
      formIsValid: true,
      nicknameIsValid: false,
      error: null,
    };
  },
  methods: {
    clearValidity(input) {
      this[input].isValid = true;
    },
    validateForm() {
      this.formIsValid = true;
      this.validateNickname();
      if (this.email.val === '') {
        this.email.isValid = false;
        this.formIsValid = false;
      }
      if (this.password.val === '') {
        this.password.isValid = false;
        this.formIsValid = false;
      }
    },
    validateNickname() {
      this.formIsValid = true;
      if (this.nickname.val === '') {
        this.nickname.isValid = false;
        this.formIsValid = false;
      }
    },
    async submitForm() {
      this.validateForm();

      if (!this.formIsValid) {
        return;
      }

      const formData = {
        nickname: this.nickname.val,
        email: this.email.val,
        password: this.password.val,
      };
      console.log(formData);

      try {
        await this.$store.dispatch('auth/signup', formData)
        this.$router.push('/email-check');
      } catch (err) {
        this.error = err.message || "인증에 실패하였습니다."
      }
    },
    async checkNickname() {
      this.validateNickname();

      if (!this.formIsValid) {
        return;
      }
      const nicknameData = {
        nickname: this.nickname.val,

      };
      console.log(nicknameData);

      try {
        await this.$store.dispatch('auth/checkNickname', nicknameData)
        this.nicknameIsValid = this.$store.isValidNickName;
      } catch (err) {
        this.error = err.message || " 중복체크 실패"
      }

    },
  }

}
</script>


<style scoped>
.col-sm-4 {
  display: flex;
  align-items: center;
}
</style>