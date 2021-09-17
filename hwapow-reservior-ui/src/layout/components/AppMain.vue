<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <keep-alive :include="cachedViews">
        <router-view :key="key" />
      </keep-alive>
    </transition>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
    cachedViews() {
      return this.$store.state.tagsView.cachedViews
    },
    key() {
      return this.$route.path
    }
  }
}
</script>

<style lang="scss" scoped>
$mainTopHeight: 50px; /*顶部栏高度*/
$mainTagHeight: 34px; /*页签高度*/

.app-main {
  /* 50= navbar  50  */
  min-height: calc(100vh - #{$mainTopHeight});
  width: 100%;
  height: calc(100vh - #{$mainTopHeight});
  position: relative;
  overflow: hidden;
}

.fixed-header+.app-main {
  padding-top: $mainTopHeight;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    min-height: calc(100vh - #{$mainTopHeight} - #{$mainTagHeight});
    height: calc(100vh - #{$mainTopHeight} - #{$mainTagHeight});
  }

  .fixed-header+.app-main {
    padding-top: calc(#{$mainTopHeight} + #{$mainTagHeight});
  }
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
</style>
