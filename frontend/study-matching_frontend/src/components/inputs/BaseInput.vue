<template>
  <div class="form-group"
       :class="{
          'input-group': hasIcon,
          'input-group-focus': focused
       }">
    <slot name="label">
      <label v-if="label" class="control-label">
        {{ label }}
      </label>
    </slot>
    <slot name="addonLeft">
      <div v-if="addonLeftIcon" class="input-group-prepend">
        <div class="input-group-text">
          <i :class="addonLeftIcon"></i>
        </div>
      </div>
    </slot>
    <slot>
      <input
          :value="value"
          v-bind="$attrs"
          @change="onChanged"
          @focus="onFocus"
          @blur="onBlur"
          class="form-control"
          aria-describedby="addon-right addon-left"
      />
    </slot>
    <slot name="addonRight">
      <div v-if="addonRightIcon" class="input-group-append">
        <div class="input-group-text">
          <i :class="addonRightIcon"></i>
        </div>
      </div>
    </slot>
    <slot name="helperText"></slot>
  </div>
</template>

<script>
export default {
  inheritAttrs: false, // 컴포넌트의 최상위 DOM 요소가 아닌 특정 하위 DOM 요소에 속성 전달하기 위한 설정
  name: "base-input",
  props: {
    label: {
      type: String,
      description: "Input label"
    },
    value: {
      type: [String, Number],
      description: "Input value",
      default:'',
    },
    addonRightIcon: {
      type: String,
      description: "Input icon on the right"
    },
    addonLeftIcon: {
      type: String,
      description: "Input icon on the left"
    },
  },
  model: {
    prop: 'value',
    event: 'input'
  },
  data() {
    return {
      localValue: this.value,
      focused: false
    }
  },
  computed: {
    hasIcon() {
      const { addonRight, addonLeft } = this.$slots;
      return addonRight !== undefined || addonLeft !== undefined || this.addonRightIcon !== undefined || this.addonLeftIcon !== undefined;
    }
  },
  watch: {
    value(newValue) {
      console.log('BaseInput - Value changed:', newValue);
    }
  },
  created() {
    console.log('child component created');
  },
  mounted() {
    console.log('child component mounted');
    console.log('Initial prop value:', this.value);
  },
  methods: {
    onChanged(evt) {
      const value = evt.target.value;
      console.log('BaseInput - onInput:', value);
      this.$emit('input', value); // input 이벤트 발생시 새로운 값을 emit
      console.log('BaseInput - this.$emit("input", value) called');
    },
    onFocus() {
      this.focused = true;
    },
    onBlur() {
      this.focused = false;
    }
  }
}
</script>

<style scoped>
/* 스타일 추가 */
</style>
