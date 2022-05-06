<template>
  <div class="example" >
    <b-progress :value="value" :max="max" show-progress animated></b-progress>

    <b-table striped hover :items="donorsForTable"  ></b-table>

  </div>

</template>

<script>
export default {
  name: "main-page.vue",

  data() {
    return {
      max: 100
    }
  },
  methods: {
    retrieveAllTotalAmounts() {
      this.value = this.retrieveAllTotalAmounts()
    }
  }
}
</script>

<style scoped lang="scss">
.example {
  color: red;
  $color: red;
}
$color: red;
</style>

<script lang="ts" src="./main-page.component.ts"></script>
