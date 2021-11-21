<template>

  <router-view></router-view>

</template>

<script>
import {user} from "./main.js";

export default {
  components: { },
  data() { return {
      user: user
  }},
  methods: {
    pollToast(msg) {

    }
  }
};
</script>