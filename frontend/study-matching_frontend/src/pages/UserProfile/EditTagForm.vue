<template>
  <div class="container">
    <main>
      <div class="py-5 text-center">
        <img class="d-block mx-auto mb-4" src="/docs/5.3/assets/brand/bootstrap-logo.svg" alt="" width="72" height="57">
        <h2>관심있는 스터디 주제를 찾아보세요.</h2>
        <p class="lead"></p>
      </div>

      <div class="row g-5 justify-content-center">
        <div class="col-md-8 col-lg-8">
          <h4 class="mb-3">스터디 주제 찾기</h4>
          <form @submit.prevent="submitForm">
            <div class="row g-3">
              <div class="col-sm-8">
                <div class="form-floating">
                  <input
                      type="text"
                      v-model="inputValue"
                      @input="onInput"
                      ref="tagInput"
                      placeholder="Search or add a tag"
                  />
                  <hr class="my-4">

                  <div class="tags-list">
                    <span v-for="(tag, index) in tags" :key="index" class="tag">
                      {{ tag }} <button @click="removeTag(index)">x</button>
                    </span>
                  </div>

                  <div class="suggestions" v-if="suggestions.length">
                    <div
                        v-for="(suggestion, index) in suggestions"
                        :key="index"
                        class="suggestion"
                        @click="addTag(suggestion)"
                    >
                      {{ suggestion }}
                    </div>
                  </div>

                </div>
              </div>
              <div class="col-sm-4">
                <button type="button" class="btn btn-primary btn-custom" @click="submitForm">등록하기</button>
              </div>
              <hr class="my-4">
            </div>
          </form>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  data() {
    return {
      inputValue: '',
      tags: [],
      availableTags: ['JavaScript', 'Vue.js', 'React', 'Node.js', 'CSS', 'HTML', 'Spring', 'Java'],
      suggestions: [],
      error: null,
    };
  },
  methods: {
    onInput() {
      const searchTerm = this.inputValue.toLowerCase();
      this.suggestions = this.availableTags.filter(tag => tag.toLowerCase().includes(searchTerm)); // 정확한 필터링은 아니지만 이정도로만 설정..
    },
    addTag(tag) {
      if (!this.tags.includes(tag)) {
        this.tags.push(tag);
      }
      this.inputValue = ''; // 입력값 초기화
      this.suggestions = []; // 제안 초기화
    },
    removeTag(index) {
      this.tags.splice(index, 1);
    },
    async submitForm() {
      const formData = {
        tags: this.tags
      }
      console.log(formData);

      try {
        await this.$store.dispatch('auth/editTag', formData)
      } catch (err) {
        this.error = err.message || "tag 등록 실패"
      }
    }
  },
};
</script>

<style scoped>
.tags-list {
  margin-top: 10px;
}

.tag {
  background-color: #e0e0e0; /* 태그 색상 */
  border-radius: 4px; /* 모서리 둥글게 */
  padding: 5px 10px; /* 패딩 */
  margin-right: 5px; /* 간격 */
}

.suggestions {
  border: 1px solid #ccc;
  margin-top: 5px;
  border-radius: 4px;
  max-height: 150px;
  overflow-y: auto;
}

.suggestion {
  padding: 5px;
  cursor: pointer;
}

.suggestion:hover {
  background-color: #f0f0f0; /* 호버 시 배경색 */
}
</style>
