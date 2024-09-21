<template>
  <TheCard class="card-user">
    <template #image>
      <img src="https://ununsplash.imgix.net/photo-1431578500526-4d9613015464?fit=crop&fm=jpg&h=300&q=75&w=400" alt="..."/>
    </template>
    <div class="author">
      <a href="#">
        <img class="avatar border-gray" src="img/faces/face-3.jpg" alt="..."/>

        <h4 class="title">Mike Andrew<br />
          <small>michael24</small>
        </h4>
      </a>
    </div>
    <p class="description text-center">
      "Lamborghini Mercy <br>
      Your chick she so thirsty <br>
      I'm in that two seat Lambo"
    </p>
    <template #footer>
      <div class="text-center d-flex justify-content-center">
        <button href="#" class="btn btn-simple"><i class="fa fa-facebook-square"></i></button>
        <button href="#" class="btn btn-simple"><i class="fa fa-twitter"></i></button>
        <button href="#" class="btn btn-simple"><i class="fa fa-google-plus-square"></i></button>
      </div>
    </template>
  </TheCard>
</template>

<script>
import TheCard from "@/components/cards/TheCard.vue";

export default {
  components: {
    TheCard,
  },
  data() {
    return {
      details: [
        {
          title: '12',
          subTitle: 'Files'
        },
        {
          title: '2GB',
          subTitle: 'Used'
        },
        {
          title: '24.6$',
          subTitle: 'Spent'
        }
      ]
    }
  },
  methods: {
    getClasses(index) {
      const remainder = index % 3;
      if (remainder === 0) {
        return 'col-md-3 col-md-offset-1';
      } else if (remainder === 2) {
        return 'col-md-4';
      } else {
        return 'col-md-3';
      }
    }
  }
}
</script>

<style>
/* 스타일 추가 */
</style>
