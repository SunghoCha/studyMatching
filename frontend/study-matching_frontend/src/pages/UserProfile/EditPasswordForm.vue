<template>
  <TheCard>
    <template v-slot:header>
      <h4 class="card-title">Edit Profile</h4>
    </template>
    <form>
      <div class="d-flex flex-column">
        <div class="col-md-4 mt-3">
          <base-input type="password"
                      label="새 패스워드"
                      placeholder="새 패스워드"
                      min="4"
                      max="20"
                      v-model="newPassword"
          >
          </base-input>
        </div>
        <div class="col-md-4 mt-3">
          <base-input type="password"
                      label="새 패스워드 확인"
                      placeholder="새 패스워드 확인"
                      min="4"
                      max="20"
                      v-model="confirmPassword"
          >
          </base-input>
        </div>
        <div class="col-md-4 mt-3">
          <div class="text-center mt-3">
            <button type="submit" class="btn btn-info btn-fill"
            @click.prevent="updatePassword">
            Update Profile
          </button>
          </div>
        </div>
      </div>
  </form>
</TheCard>
</template>

<script>
  import TheCard from "@/components/cards/TheCard.vue";
  import BaseInput from "@/components/inputs/BaseInput.vue";

export default {
  name: "TheEditProfileForm",
  components: {BaseInput, TheCard},
  data() {
    return {
      newPassword: '',
      newPasswordConfirm: '',
      error: null,
    }
  },
  created() {
    console.log('parent component created');
  },
  mounted() {
    console.log('parent component mounted');
  },
  methods: {
    async updatePassword() {
      if (this.newPassword !== this.newPasswordConfirm) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
      }
      const formData = {
        newPassword: this.newPassword,
        newPasswordConfirm: this.newPasswordConfirm
      };
      try {
        await this.$store.dispatch('auth/editPassword', formData)
        this.$router.replace('/profile')
      } catch (err) {
        this.error = err.message || "비밀번호 변경에 실패하였습니다."
      }
    }
  },
}
</script>

<style scoped>

</style>