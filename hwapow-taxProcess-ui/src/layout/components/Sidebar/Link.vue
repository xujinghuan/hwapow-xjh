<template>
  <component :is="type" v-bind="linkProps(to)">
    <slot />
  </component>
</template>

<script>
import { isExternal } from '@/utils/validate'
import { getUserProfile } from '@/api/system/user'

export default {
  props: {
    to: {
      type: String,
      required: true
    }
  },
  data(){
    return{
      userId:null
    }
  },
  computed: {
    isExternal() {
      return isExternal(this.to)
    },
    type() {
      if (this.isExternal) {
        return 'a'
      }
      return 'router-link'
    }
  },
  methods: {
    linkProps(to) {
      if (this.isExternal) {
        /*getUserProfile().then(response => {
          this.userId = response.data.userId;
        });
        return {
          href: to+(to.indexOf("?") != -1?'&':'?')+'userId='+this.userId,
          target: '_blank',
          rel: 'noopener'
        }*/
        return {
          href: to,
          target: '_blank',
          rel: 'noopener'
        }
      }else{
        return {
          to: to
        }
      }
    }
  }
}
</script>
