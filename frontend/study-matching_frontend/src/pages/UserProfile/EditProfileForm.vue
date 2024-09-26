<template>
  <TheCard>
    <template v-slot:header>
      <h4 class="card-title">Edit Profile</h4>
    </template>
    <form>
      <div class="row">
        <div class="col-md-5">
          <base-input type="text"
                      label="Company"
                      :disabled="true"
                      placeholder="Light dashboard"
                      v-model="user.company">
          </base-input>
        </div>
        <div class="col-md-3">
          <base-input type="text"
                      label="Username"
                      placeholder="Username"
                      :value="user.username"
                      @input="onChangeUserName">
          </base-input>
        </div>
        <div class="col-md-4">
          <base-input type="email"
                      label="Email"
                      placeholder="Email"
                      :value="user.email"
                      @input="onChangeEmail">
          </base-input>
        </div>
      </div>

      <div class="row">


      </div>

      <div class="row">
        <div class="col-md-12">
          <base-input type="text"
                      label="Address"
                      placeholder="Home Address"
                      v-model="user.address">
          </base-input>
        </div>
      </div>

      <div class="row">
        <div class="col-md-4">
          <base-input type="text"
                      label="City"
                      placeholder="City"
                      v-model="user.city">
          </base-input>
        </div>
        <div class="col-md-4">
          <base-input type="text"
                      label="Country"
                      placeholder="Country"
                      v-model="user.country">
          </base-input>
        </div>
        <div class="col-md-4">
          <base-input type="number"
                      label="Postal Code"
                      placeholder="ZIP Code"
                      v-model="user.postalCode">
          </base-input>
        </div>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="form-group">
            <label>About Me</label>
            <textarea rows="5" class="form-control border-input"
                      placeholder="Here can be your description"
                      v-model="user.aboutMe">
              </textarea>
          </div>
        </div>
      </div>
      <div class="text-center">
        <button type="submit" class="btn btn-info btn-fill float-right" @click.prevent="updateProfile">
          Update Profile
        </button>
      </div>
    </form>
  </TheCard>
</template>

<script>
import TheCard from "@/components/cards/TheCard.vue";
import BaseInput from "@/components/inputs/BaseInput.vue";

export default {
  name: "EditProfileForm",
  components: {BaseInput, TheCard},
  data() {
    return {
      user: {
        company: 'Light dashboard',
        username: 'michael23',
        email: 'example@gmail.com',
        firstName: 'Mike',
        lastName: 'Andrew',
        address: 'Melbourne, Australia',
        city: 'melbourne',
        country: 'Australia',
        postalCode: '',
        aboutMe: `Lamborghini Mercy, Your chick she so thirsty, I'm in that two seat Lambo.`
      },
    }
  },
  watch: {
    'user.username'(newValue) {
      console.log('EditProfileForm - Username updated in parent:', newValue);
    }
  },
  created() {
    console.log('parent component created');
  },
  mounted() {
    console.log('parent component mounted');
  },
  methods: {
    onChangeUserName(val) {
      this.user.username = val;
      console.log("EditProfileForm - username changed")
    },
    onChangeEmail(val) {
      this.user.email = val;
      console.log("EditProfileForm - email changed")
    }
  },
}
</script>

<style scoped>

</style>