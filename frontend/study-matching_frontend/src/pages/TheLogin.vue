
<template>
  <div v-show="!!error">
    <p>{{ error }}</p>
  </div>
  <div class="form-signin w-100 m-auto">
    <form @submit.prevent="submitForm">
      <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

      <div class="form-floating" :class="{invalid: !email.isValid}">
        <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" v-model.trim="email.val" @blur="clearValidity('email')">
        <label for="floatingInput">Email address</label>
        <p v-if="!email.isValid">유효한 이메일을 입력해주세요.</p>
      </div>
      <div class="form-floating" :class="{invalid: !password.isValid}">
        <input type="password" class="form-control" id="floatingPassword" placeholder="Password" v-model.trim="password.val" @blur="clearValidity('password')">
        <label for="floatingPassword">Password</label>
        <p v-if="!password.isValid">유효한 비밀번호를 입력해주세요.</p>
      </div>

      <div class="form-check text-start my-3">
        <input class="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault">
        <label class="form-check-label" for="flexCheckDefault">
          Remember me
        </label>
      </div>
      <button class="btn btn-primary w-100 py-2" type="submit">Sign in</button>
      <p class="mt-5 mb-3 text-body-secondary">&copy; 2017–2024</p>
    </form>
  </div>
</template>

<script>
export default {
  name: "TheLogin",
  data() {
    return {
      email: {
        val:'',
        isValid: true
      },
      password: {
        val:'',
        isValid: true
      },
      formIsValid: true,
      error: null,
    };
  },
  methods: {
    clearValidity(input) {
      this[input].isValid = true;
    },
    validateForm() {
      this.formIsValid = true;
      if (this.email.val === '') {
        this.email.isValid = false;
        this.formIsValid = false;
      }
      if (this.password.val === '') {
        this.password.isValid = false;
        this.formIsValid = false;
      }
    },
    async submitForm() {
      this.validateForm();

      if (!this.formIsValid) {
        return;
      }

      const formData = {
        email: this.email.val,
        password: this.password.val,
      };
      console.log(formData);
      // 로그인 부분 구현
      try {
        await this.$store.dispatch('auth/login', formData)
      } catch (err) {
        this.error = err.message || "인증에 실패하였습니다."
      }
    }
  }

}
</script>

<style scoped>


.form-signin {
  max-width: 330px;
  padding: 1rem;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

.invalid label {
  color: red;
}

.invalid input,
.invalid textarea {
  border: 1px solid red;
}
</style>